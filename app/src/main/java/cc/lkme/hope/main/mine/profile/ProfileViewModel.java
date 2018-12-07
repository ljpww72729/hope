package cc.lkme.hope.main.mine.profile;

import android.app.Application;

import javax.inject.Inject;

import cc.lkme.hope.BaseViewModel;
import cc.lkme.hope.SingleLiveEvent;
import cc.lkme.hope.data.source.HopeRepository;
import timber.log.Timber;

public class ProfileViewModel extends BaseViewModel {

    private final HopeRepository mHopeRepository;
    private final SingleLiveEvent<Void> showAvatar = new SingleLiveEvent<>();

    @Inject
    public ProfileViewModel(Application mApplication, HopeRepository hopeRepository) {
        super(mApplication);
        this.mHopeRepository = hopeRepository;
        Timber.d("%s created!", this.getClass().getSimpleName());
    }

    public SingleLiveEvent<Void> getShowAvatar() {
        return showAvatar;
    }

    public void showAvatar() {
        showAvatar.call();
    }

}