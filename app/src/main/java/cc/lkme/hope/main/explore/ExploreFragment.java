package cc.lkme.hope.main.explore;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cc.lkme.hope.BaseFragment;
import cc.lkme.hope.R;
import cc.lkme.hope.databinding.ExploreFragBinding;
import cc.lkme.hope.utils.AutoClearedValue;
import cc.lkme.hope.utils.GlobalLayoutUtils;

public class ExploreFragment extends BaseFragment implements GlobalLayoutUtils.IOnViewReady {

    private AutoClearedValue<ExploreFragBinding> binding;
    private ExploreViewModel viewModel;

    public static ExploreFragment newInstance() {
        return new ExploreFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ExploreFragBinding exploreFragBinding = ExploreFragBinding.inflate(inflater, container, false);
        binding = new AutoClearedValue<>(this, exploreFragBinding);
        initToolBar(binding.get().toolbar);
        return binding.get().getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = (ExploreViewModel) obtainViewModel(getActivity(), ExploreViewModel.class);
        binding.get().setViewmodel(viewModel);
        GlobalLayoutUtils.setListener(this);
        GlobalLayoutUtils.setGlobalLayoutListener(binding.get().giftBag);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (binding.get().signinOne.getDrawable() instanceof Animatable) {
            ((Animatable) binding.get().signinOne.getDrawable()).start();
            ((Animatable) binding.get().signinTwo.getDrawable()).start();
            ((Animatable) binding.get().signinThree.getDrawable()).start();
            ((Animatable) binding.get().signinFour.getDrawable()).start();
            ((Animatable) binding.get().signinFive.getDrawable()).start();
            ((Animatable) binding.get().signinSix.getDrawable()).start();
//        ((Animatable)binding.get().signinSeven.getDrawable()).start();
        }
        AnimatorSet animatorSet = (AnimatorSet) AnimatorInflater.loadAnimator(getActivity().getApplicationContext(), R.animator.gift_bag_rotate);
        animatorSet.setTarget(binding.get().giftBag);
        animatorSet.start();

    }

    public void initToolBar(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.va_back);
        toolbar.setTitle(R.string.mine_setting);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
    }

    @Override
    public void onViewReady(View view) {
        if (view == binding.get().giftBag) {
            binding.get().giftBag.setPivotX(binding.get().giftBag.getMeasuredWidth() / 2);
            binding.get().giftBag.setPivotY(binding.get().giftBag.getMeasuredHeight());
        }
    }
}
