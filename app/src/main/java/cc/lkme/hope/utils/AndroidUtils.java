package cc.lkme.hope.utils;

import android.app.Application;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AndroidUtils {
    private Application application;

    @Inject
    public AndroidUtils(Application application) {
        this.application = application;
    }

    public String getString(int stringId) {
        return application.getString(stringId);
    }

}
