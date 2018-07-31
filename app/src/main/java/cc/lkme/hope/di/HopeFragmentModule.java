package cc.lkme.hope.di;

import cc.lkme.hope.main.explore.ExploreFragment;
import cc.lkme.hope.main.mine.MineFragment;
import cc.lkme.hope.main.news.NewsFragment;
import cc.lkme.hope.main.news.local.LocalFragment;
import cc.lkme.hope.main.news.recommend.RecommendFragment;
import cc.lkme.hope.main.news.video.VideoFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class HopeFragmentModule {
    @ContributesAndroidInjector
    abstract NewsFragment contributeNewsFragment();

    @ContributesAndroidInjector
    abstract RecommendFragment contributeRecommendFragment();

    @ContributesAndroidInjector
    abstract VideoFragment contributeVideoFragment();

    @ContributesAndroidInjector
    abstract LocalFragment localFragment();

    @ContributesAndroidInjector
    abstract ExploreFragment contributeExploreFragment();

    @ContributesAndroidInjector
    abstract MineFragment contributeMineFragment();


}
