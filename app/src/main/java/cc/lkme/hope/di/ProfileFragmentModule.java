package cc.lkme.hope.di;

import cc.lkme.hope.main.mine.profile.ProfileFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ProfileFragmentModule {
    @ContributesAndroidInjector
    abstract ProfileFragment contributeProfileFragment();
}
