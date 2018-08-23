package cc.lkme.hope.main.mine.settings;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;

import javax.inject.Inject;

import cc.lkme.hope.BaseViewModel;
import cc.lkme.hope.R;
import cc.lkme.hope.data.source.HopeRepository;
import cc.lkme.hope.utils.AndroidUtils;
import timber.log.Timber;

public class SettingsViewModel extends BaseViewModel {

    private final HopeRepository mHopeRepository;
    private MutableLiveData<SettingsViewData> viewDataLiveData = new MutableLiveData<>();

    @Inject
    public SettingsViewModel(Application mApplication, HopeRepository hopeRepository, AndroidUtils androidUtils) {
        super(mApplication);
        Timber.d("%s created!", this.getClass().getSimpleName());
        this.mHopeRepository = hopeRepository;
        SettingsViewData settingsViewData = new SettingsViewData();
        settingsViewData.setCached(androidUtils.getString(R.string.settings_cached_data, 100));
        viewDataLiveData.setValue(settingsViewData);
    }

    public MutableLiveData<SettingsViewData> getViewDataLiveData() {
        return viewDataLiveData;
    }
}
