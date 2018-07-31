package cc.lkme.hope.main;

import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.MenuItem;

import javax.inject.Inject;

import cc.lkme.hope.BaseActivity;
import cc.lkme.hope.R;
import cc.lkme.hope.databinding.HopeActBinding;
import cc.lkme.hope.main.explore.ExploreFragment;
import cc.lkme.hope.main.news.NewsFragment;
import cc.lkme.hope.utils.ActivityUtils;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import timber.log.Timber;

public class HopeActivity extends BaseActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    private HopeActBinding hopeActBinding;
    private HopeViewModel viewModel;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    viewModel.getNavigationIndex().setValue(0);
                    return true;
                case R.id.navigation_find:
                    viewModel.getNavigationIndex().setValue(1);
                    return true;
                case R.id.navigation_mine:
                    viewModel.getNavigationIndex().setValue(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hopeActBinding = DataBindingUtil.setContentView(this, R.layout.hope_act);
        viewModel = (HopeViewModel) obtainViewModel(this, HopeViewModel.class);
        hopeActBinding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        viewModel.getNavigationIndex().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                Timber.d("当前页签为：" + integer);
                switch (integer) {
                    case 0:
                        setupViewFragment();
                        break;
                    case 1:
                        ExploreFragment exploreFragment = (ExploreFragment) viewModel.getNavigationFragments().getValue().get(1);
                        if (exploreFragment == null) {
                            exploreFragment = ExploreFragment.newInstance();
                            viewModel.getNavigationFragments().getValue().put(1, exploreFragment);
                        }
                        ActivityUtils.showFragmentInActivity(
                                getSupportFragmentManager(), exploreFragment, R.id.contentFrame, "hope_explore");
                        break;
                    case 2:
                        break;
                }
            }
        });
    }

    private void setupViewFragment() {
        NewsFragment newsFragment = (NewsFragment) viewModel.getNavigationFragments().getValue().get(0);
        if (newsFragment == null) {
            newsFragment = NewsFragment.newInstance();
            viewModel.getNavigationFragments().getValue().put(0, newsFragment);
        }
        ActivityUtils.showFragmentInActivity(
                getSupportFragmentManager(), newsFragment, R.id.contentFrame, "hope_home");
    }


}
