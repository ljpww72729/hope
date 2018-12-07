package cc.lkme.hope.main.mine;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import cc.lkme.hope.BaseFragment;
import cc.lkme.hope.R;
import cc.lkme.hope.databinding.MineFragBinding;
import cc.lkme.hope.main.mine.profile.ProfileActivity;
import cc.lkme.hope.main.mine.settings.SettingsActivity;
import cc.lkme.hope.utils.AutoClearedValue;

/**
 * 个人页面
 */
public class MineFragment extends BaseFragment {
    private AutoClearedValue<MineFragBinding> binding;
    private MineViewModel viewModel;

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        MineFragBinding mineFragBinding = MineFragBinding.inflate(inflater, container, false);
        binding = new AutoClearedValue<>(this, mineFragBinding);
        initToolBar(binding.get().toolbar);
        return binding.get().getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = (MineViewModel) obtainViewModel(getActivity(), MineViewModel.class);
        binding.get().setViewmodel(viewModel);
        viewModel.getOpenProfile().observe(this, new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void aVoid) {
                openProfile();
            }
        });
    }

    private void openProfile() {
        ProfileActivity.start(getActivity(), new Bundle());
    }

    public void initToolBar(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.va_notifications);
        toolbar.inflateMenu(R.menu.mine_settings);
        toolbar.setTitle(R.string.title_mine);
        toolbar.setNavigationOnClickListener(v -> {
            Toast.makeText(getActivity(), "通知", Toast.LENGTH_SHORT).show();
        });
        toolbar.setOnMenuItemClickListener(item -> {
            switch (item.getItemId()) {
                case R.id.settings:
                    Bundle bundle = new Bundle();
                    SettingsActivity.start(getActivity(), bundle);
                    break;
            }
            return true;
        });
    }

}
