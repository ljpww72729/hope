package cc.lkme.hope.di;

import cc.lkme.hope.main.mine.settings.SettingsFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class SettingsFragmentModule {
    @ContributesAndroidInjector
    abstract SettingsFragment contributeSettingsFragment();
}
