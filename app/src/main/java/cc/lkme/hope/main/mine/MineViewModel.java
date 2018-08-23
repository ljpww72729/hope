package cc.lkme.hope.main.mine;

import android.app.Application;

import javax.inject.Inject;

import cc.lkme.hope.BaseViewModel;
import cc.lkme.hope.R;
import cc.lkme.hope.data.source.HopeRepository;
import timber.log.Timber;

public class MineViewModel extends BaseViewModel {

    private final HopeRepository mHopeRepository;

    @Inject
    public MineViewModel(Application mApplication, HopeRepository hopeRepository) {
        super(mApplication);
        this.mHopeRepository = hopeRepository;
        Timber.d("%s created!", this.getClass().getSimpleName());
    }

    public void userEdit() {
        snackbarMessage.setValue(R.string.app_name);
    }
}
