package cc.lkme.hope.main.news;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import javax.inject.Inject;

import cc.lkme.hope.BaseFragment;
import cc.lkme.hope.databinding.NewsFragBinding;
import cc.lkme.hope.main.news.data.NewsTabsFactory;
import cc.lkme.hope.utils.AutoClearedValue;
import timber.log.Timber;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewsFragment extends BaseFragment {

    private AutoClearedValue<NewsFragBinding> binding;
    private AutoClearedValue<NewsPagerAdapter> adapter;
    private NewsViewModel viewModel;
    @Inject
    NewsTabsFactory newsTabsFactory;

    public static NewsFragment newInstance() {
        return new NewsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Timber.d("onCreate被调用");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.d("onCreateView被调用");
        NewsFragBinding newsFragBinding = NewsFragBinding.inflate(inflater, container, false);
        viewModel = (NewsViewModel) obtainViewModel(getActivity(), NewsViewModel.class);
        binding = new AutoClearedValue<>(this, newsFragBinding);
        NewsPagerAdapter newsPagerAdapter = new NewsPagerAdapter(getChildFragmentManager(), newsTabsFactory.getNewsTabsArray());
        adapter = new AutoClearedValue<>(this, newsPagerAdapter);
        binding.get().viewpager.setAdapter(adapter.get());
        binding.get().tabLayout.setupWithViewPager(binding.get().viewpager, true);
        binding.get().viewpager.setOffscreenPageLimit(2);
        // 加载第一个页面
        viewModel.getViewpagerSelected().setValue(0);
        binding.get().viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Timber.d("ViewPager切换到的页面位置为：" + position);
                viewModel.getViewpagerSelected().setValue(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        return binding.get().getRoot();
    }

    @Override
    public void onDestroyView() {
        Timber.d("onDestroyView被调用");
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Timber.d("onDestroy被调用");
        super.onDestroy();
    }
}
