package cc.lkme.hope.main.mine.settings;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import cc.lkme.hope.BR;

public class SettingsViewData extends BaseObservable {
    private String cached;

    @Bindable
    public String getCached() {
        return cached;
    }

    public void setCached(String cached) {
        this.cached = cached;
        notifyPropertyChanged(BR.cached);
    }

}
