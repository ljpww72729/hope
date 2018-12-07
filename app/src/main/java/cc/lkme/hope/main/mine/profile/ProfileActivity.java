package cc.lkme.hope.main.mine.profile;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

import cc.lkme.hope.BaseActivity;
import cc.lkme.hope.R;
import cc.lkme.hope.utils.ActivityUtils;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import cc.lkme.hope.databinding.CommonActBinding;

public class ProfileActivity extends BaseActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;
    private CommonActBinding binding;

    public static void start(Context context, Bundle bundle) {
        Intent starter = new Intent(context, ProfileActivity.class);
        if (bundle != null) {
            starter.putExtras(bundle);
        }
        context.startActivity(starter);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.common_act);
        ProfileFragment profileFragment = ProfileFragment.newInstance();
        ActivityUtils.replaceFragmentInActivity(getSupportFragmentManager(), profileFragment, R.id.contentFrame);
    }

}
