package cc.lkme.hope.main.news.detail;

import android.app.Application;

import javax.inject.Inject;

import cc.lkme.hope.BaseViewModel;
import cc.lkme.hope.data.source.HopeRepository;

public class DetailViewModel extends BaseViewModel {

    @Inject
    public DetailViewModel(Application mApplication, HopeRepository hopeRepository) {
        super(mApplication);

    }
}
