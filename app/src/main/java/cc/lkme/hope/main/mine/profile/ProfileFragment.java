package cc.lkme.hope.main.mine.profile;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;

import cc.lkme.hope.BaseFragment;
import cc.lkme.hope.R;
import cc.lkme.hope.databinding.ProfileFragBinding;
import cc.lkme.hope.utils.AutoClearedValue;

public class ProfileFragment extends BaseFragment {

    private AutoClearedValue<ProfileFragBinding> binding;
    private ProfileViewModel viewModel;
    private int mShortAnimationDuration;
    private AnimatorSet mCurrentAnimator;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShortAnimationDuration = getResources().getInteger(
                android.R.integer.config_shortAnimTime);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ProfileFragBinding profileFragBinding = ProfileFragBinding.inflate(inflater, container, false);
        binding = new AutoClearedValue<>(this, profileFragBinding);
        initToolBar(binding.get().toolbar);
        return binding.get().getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = (ProfileViewModel) obtainViewModel(getActivity(), ProfileViewModel.class);
        binding.get().setViewmodel(viewModel);
        viewModel.getShowAvatar().observe(this, aVoid -> {
            showAvatar();
        });
    }

    private void showAvatar() {
// If there's an animation in progress, cancel it
        // immediately and proceed with this one.
        if (mCurrentAnimator != null) {
            mCurrentAnimator.cancel();
        }

        // Calculate the starting and ending bounds for the zoomed-in image.
        // This step involves lots of math. Yay, math.
        final Rect startBounds = new Rect();
        final Rect finalBounds = new Rect();
        final Point globalOffset = new Point();

        // The start bounds are the global visible rectangle of the thumbnail,
        // and the final bounds are the global visible rectangle of the container
        // view. Also set the container view's offset as the origin for the
        // bounds, since that's the origin for the positioning animation
        // properties (X, Y).
        binding.get().avatar.getGlobalVisibleRect(startBounds);
        binding.get().contentContainer
                .getGlobalVisibleRect(finalBounds, globalOffset);
        startBounds.offset(-globalOffset.x, -globalOffset.y);
        finalBounds.offset(-globalOffset.x, -globalOffset.y);

        // Adjust the start bounds to be the same aspect ratio as the final
        // bounds using the "center crop" technique. This prevents undesirable
        // stretching during the animation. Also calculate the start scaling
        // factor (the end scaling factor is always 1.0).
        float startScale;
        if ((float) finalBounds.width() / finalBounds.height()
                > (float) startBounds.width() / startBounds.height()) {
            // Extend start bounds horizontally
            startScale = (float) startBounds.height() / finalBounds.height();
            float startWidth = startScale * finalBounds.width();
            float deltaWidth = (startWidth - startBounds.width()) / 2;
            startBounds.left -= deltaWidth;
            startBounds.right += deltaWidth;
        } else {
            // Extend start bounds vertically
            startScale = (float) startBounds.width() / finalBounds.width();
            float startHeight = startScale * finalBounds.height();
            float deltaHeight = (startHeight - startBounds.height()) / 2;
            startBounds.top -= deltaHeight;
            startBounds.bottom += deltaHeight;
        }

        // Hide the thumbnail and show the zoomed-in view. When the animation
        // begins, it will position the zoomed-in view in the place of the
        // thumbnail.
        binding.get().avatar.setAlpha(0f);
        binding.get().avatarLarge.setVisibility(View.VISIBLE);

        // Set the pivot point for SCALE_X and SCALE_Y transformations
        // to the top-left corner of the zoomed-in view (the default
        // is the center of the view).
         binding.get().avatarLarge.setPivotX(0f);
         binding.get().avatarLarge.setPivotY(0f);

        // Construct and run the parallel animation of the four translation and
        // scale properties (X, Y, SCALE_X, and SCALE_Y).
        AnimatorSet set = new AnimatorSet();
        set
                .play(ObjectAnimator.ofFloat( binding.get().avatarLarge, View.X,
                        startBounds.left, finalBounds.left))
                .with(ObjectAnimator.ofFloat( binding.get().avatarLarge, View.Y,
                        startBounds.top, finalBounds.top))
                .with(ObjectAnimator.ofFloat( binding.get().avatarLarge, View.SCALE_X,
                        startScale, 1f))
                .with(ObjectAnimator.ofFloat( binding.get().avatarLarge,
                        View.SCALE_Y, startScale, 1f));
        set.setDuration(mShortAnimationDuration);
        set.setInterpolator(new DecelerateInterpolator());
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mCurrentAnimator = null;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                mCurrentAnimator = null;
            }
        });
        set.start();
        mCurrentAnimator = set;

        // Upon clicking the zoomed-in image, it should zoom back down
        // to the original bounds and show the thumbnail instead of
        // the expanded image.
        final float startScaleFinal = startScale;
         binding.get().avatarLarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCurrentAnimator != null) {
                    mCurrentAnimator.cancel();
                }

                // Animate the four positioning/sizing properties in parallel,
                // back to their original values.
                AnimatorSet set = new AnimatorSet();
                set.play(ObjectAnimator
                        .ofFloat( binding.get().avatarLarge, View.X, startBounds.left))
                        .with(ObjectAnimator
                                .ofFloat( binding.get().avatarLarge,
                                        View.Y, startBounds.top))
                        .with(ObjectAnimator
                                .ofFloat( binding.get().avatarLarge,
                                        View.SCALE_X, startScaleFinal))
                        .with(ObjectAnimator
                                .ofFloat( binding.get().avatarLarge,
                                        View.SCALE_Y, startScaleFinal));
                set.setDuration(mShortAnimationDuration);
                set.setInterpolator(new DecelerateInterpolator());
                set.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                         binding.get().avatar.setAlpha(1f);
                         binding.get().avatarLarge.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {
                         binding.get().avatar.setAlpha(1f);
                         binding.get().avatarLarge.setVisibility(View.GONE);
                        mCurrentAnimator = null;
                    }
                });
                set.start();
                mCurrentAnimator = set;
            }
        });
    }

    public void initToolBar(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.va_back);
        toolbar.setTitle(R.string.mine_setting);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
    }

}
