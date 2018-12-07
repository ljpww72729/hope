package cc.lkme.hope.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import cc.lkme.hope.ViewModelFactory;
import cc.lkme.hope.main.HopeViewModel;
import cc.lkme.hope.main.explore.ExploreViewModel;
import cc.lkme.hope.main.mine.MineViewModel;
import cc.lkme.hope.main.mine.profile.ProfileViewModel;
import cc.lkme.hope.main.mine.settings.SettingsViewModel;
import cc.lkme.hope.main.news.NewsViewModel;
import cc.lkme.hope.main.news.recommend.RecommendViewModel;
import cc.lkme.hope.main.news.video.VideoViewModel;
import cc.lkme.hope.main.news.video.detail.VideoDetailViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModelModule {

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

    @Binds
    @IntoMap
    @ViewModelKey(HopeViewModel.class)
    abstract ViewModel bindHopeViewModel(HopeViewModel hopeViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(RecommendViewModel.class)
    abstract ViewModel bindRecommendViewModel(RecommendViewModel recommendViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(NewsViewModel.class)
    abstract ViewModel bindNewsViewModel(NewsViewModel newsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(VideoViewModel.class)
    abstract ViewModel bindVideoViewModel(VideoViewModel videoViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(VideoDetailViewModel.class)
    abstract ViewModel bindVideoDetailViewModel(VideoDetailViewModel videoDetailViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MineViewModel.class)
    abstract ViewModel bindMineViewModel(MineViewModel mineViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel.class)
    abstract ViewModel bindSettingsViewModel(SettingsViewModel settingsViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ExploreViewModel.class)
    abstract ViewModel bindExploreViewModel(ExploreViewModel exploreViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    abstract ViewModel bindProfileViewModel(ProfileViewModel profileViewModel);


}
