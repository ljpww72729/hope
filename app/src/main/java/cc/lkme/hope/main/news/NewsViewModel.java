package cc.lkme.hope.main.news;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import javax.inject.Inject;

import cc.lkme.hope.BaseViewModel;

public class NewsViewModel extends BaseViewModel {

    // ViewPager选中的项
    private MutableLiveData<Integer> viewpagerSelected = new MutableLiveData<>();

    @Inject
    public NewsViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Integer> getViewpagerSelected() {
        return viewpagerSelected;
    }
}
