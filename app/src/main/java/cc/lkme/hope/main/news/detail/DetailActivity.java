package cc.lkme.hope.main.news.detail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

import cc.lkme.hope.BaseActivity;
import cc.lkme.hope.R;
import cc.lkme.hope.databinding.DetailActBinding;
import cc.lkme.hope.utils.ActivityUtils;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class DetailActivity extends BaseActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    private DetailActBinding binding;

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.detail_act);
        DetailFragment detailFragment = DetailFragment.newInstance(getIntent().getExtras());
        ActivityUtils.replaceFragmentInActivity(
                getSupportFragmentManager(), detailFragment, R.id.contentFrame);
    }

    public static void start(Context context, Bundle bundle) {
        Intent starter = new Intent(context, DetailActivity.class);
        if (bundle != null) {
            starter.putExtras(bundle);
        }
        context.startActivity(starter);
    }

}
