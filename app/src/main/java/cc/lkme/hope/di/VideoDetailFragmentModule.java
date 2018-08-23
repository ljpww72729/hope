package cc.lkme.hope.di;

import cc.lkme.hope.main.news.video.detail.VideoDetailFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class VideoDetailFragmentModule {
    @ContributesAndroidInjector
    abstract VideoDetailFragment contributeVideoDetailFragment();
}
