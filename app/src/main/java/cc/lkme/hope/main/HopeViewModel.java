package cc.lkme.hope.main;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.v4.app.Fragment;
import android.support.v4.util.SparseArrayCompat;

import javax.inject.Inject;

import cc.lkme.hope.BaseViewModel;
import cc.lkme.hope.data.source.HopeRepository;

public class HopeViewModel extends BaseViewModel {

    private final MutableLiveData<SparseArrayCompat<Fragment>> navigationFragments = new MutableLiveData<>();
    private final MutableLiveData<Integer> navigationIndex = new MutableLiveData<>();

    @Inject
    public HopeViewModel(Application mApplication, HopeRepository mHopeRepository) {
        super(mApplication);
        navigationFragments.setValue(new SparseArrayCompat<>(3));
        navigationIndex.setValue(0);
    }

    public MutableLiveData<SparseArrayCompat<Fragment>> getNavigationFragments() {
        return navigationFragments;
    }

    public MutableLiveData<Integer> getNavigationIndex() {
        return navigationIndex;
    }
}
