package cc.lkme.hope.main.news.video.detail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import cc.lkme.hope.R;
import cc.lkme.hope.databinding.CommonActBinding;
import cc.lkme.hope.utils.ActivityUtils;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class VideoDetailActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    private CommonActBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.common_act);
        VideoDetailFragment detailFragment = VideoDetailFragment.newInstance();//getIntent().getExtras()
        ActivityUtils.replaceFragmentInActivity(
                getSupportFragmentManager(), detailFragment, R.id.contentFrame);
    }

    @Override
    public DispatchingAndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    public static void start(Context context, Bundle bundle) {
        Intent starter = new Intent(context, VideoDetailActivity.class);
        if (bundle != null) {
            starter.putExtras(bundle);
        }
        context.startActivity(starter);
    }
}
