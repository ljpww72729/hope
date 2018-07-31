package cc.lkme.hope.di;

import cc.lkme.hope.main.news.detail.DetailFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class DetailFragmentModule {
    @ContributesAndroidInjector
    abstract DetailFragment contributeDetailFragment();
}
