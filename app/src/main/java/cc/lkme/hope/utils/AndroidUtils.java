package cc.lkme.hope.utils;

import android.app.Application;
import android.support.annotation.StringRes;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AndroidUtils {
    private Application application;

    @Inject
    public AndroidUtils(Application application) {
        this.application = application;
    }

    public String getString(@StringRes int stringId) {
        return application.getString(stringId);
    }

    public String getString(@StringRes int resId, Object... formatArgs) {
        return application.getString(resId, formatArgs);
    }

}
