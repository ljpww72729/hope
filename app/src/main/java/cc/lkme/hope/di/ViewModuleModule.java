package cc.lkme.hope.di;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import cc.lkme.hope.ViewModelFactory;
import cc.lkme.hope.main.HopeViewModel;
import cc.lkme.hope.main.news.NewsViewModel;
import cc.lkme.hope.main.news.recommend.RecommendViewModel;
import cc.lkme.hope.main.news.video.VideoViewModel;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
public abstract class ViewModuleModule {

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


}
