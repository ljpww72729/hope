package cc.lkme.hope.di;

import cc.lkme.hope.main.HopeActivity;
import cc.lkme.hope.main.news.detail.DetailActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {
    @ContributesAndroidInjector(modules = HopeFragmentModule.class)
    abstract HopeActivity contributeHopeActivity();

    @ContributesAndroidInjector(modules = DetailFragmentModule.class)
    abstract DetailActivity contributeDetailActivity();

}
