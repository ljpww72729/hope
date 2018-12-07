package cc.lkme.hope.main.explore;

import android.app.Application;

import javax.inject.Inject;

import cc.lkme.hope.BaseViewModel;
import cc.lkme.hope.data.source.HopeRepository;
import timber.log.Timber;

public class ExploreViewModel extends BaseViewModel {

    private final HopeRepository mHopeRepository;

    @Inject
    public ExploreViewModel(Application mApplication, HopeRepository hopeRepository) {
        super(mApplication);
        this.mHopeRepository = hopeRepository;
        Timber.d("%s created!", this.getClass().getSimpleName());
    }

}