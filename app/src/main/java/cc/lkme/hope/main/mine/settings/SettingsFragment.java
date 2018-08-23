package cc.lkme.hope.main.mine.settings;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cc.lkme.hope.BaseFragment;
import cc.lkme.hope.R;
import cc.lkme.hope.databinding.SettingsFragBinding;
import cc.lkme.hope.utils.AutoClearedValue;

/**
 * 个人页面
 */
public class SettingsFragment extends BaseFragment {
    private AutoClearedValue<SettingsFragBinding> binding;
    private SettingsViewModel viewModel;

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SettingsFragBinding settingsFragBinding = SettingsFragBinding.inflate(inflater, container, false);
        binding = new AutoClearedValue<>(this, settingsFragBinding);
        initToolBar(binding.get().toolbar);
        return binding.get().getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = (SettingsViewModel) obtainViewModel(getActivity(), SettingsViewModel.class);
        binding.get().setViewmodel(viewModel);
        viewModel.getViewDataLiveData().observe(this, settingsViewData -> binding.get().setViewdata(settingsViewData));
    }

    public void initToolBar(Toolbar toolbar) {
        toolbar.setNavigationIcon(R.drawable.va_back);
        toolbar.setTitle(R.string.mine_setting);
        toolbar.setNavigationOnClickListener(v -> getActivity().onBackPressed());
    }

}
