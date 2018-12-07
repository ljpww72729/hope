package cc.lkme.hope.di;

import cc.lkme.hope.main.HopeActivity;
import cc.lkme.hope.main.mine.profile.ProfileActivity;
import cc.lkme.hope.main.mine.settings.SettingsActivity;
import cc.lkme.hope.main.news.detail.DetailActivity;
import cc.lkme.hope.main.news.video.detail.VideoDetailActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector(modules = HopeFragmentModule.class)
    abstract HopeActivity contributeHopeActivity();

    @ContributesAndroidInjector(modules = DetailFragmentModule.class)
    abstract DetailActivity contributeDetailActivity();

    @ContributesAndroidInjector(modules = VideoDetailFragmentModule.class)
    abstract VideoDetailActivity contributeVideoDetailActivity();

    @ContributesAndroidInjector(modules = SettingsFragmentModule.class)
    abstract SettingsActivity contributeSettingsActivity();

    @ContributesAndroidInjector(modules = ProfileFragmentModule.class)
    abstract ProfileActivity contributeProfileActivity();


}
