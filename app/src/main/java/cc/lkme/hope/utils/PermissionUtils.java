package cc.lkme.hope.utils;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.PermissionChecker;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

/**
 * 权限帮助类
 *
 * Created by LinkedME06 on 29/03/2017.
 */

@Singleton
public class PermissionUtils {

    private Application application;

    @Inject
    public PermissionUtils(Application application) {
        this.application = application;
    }

    /**
     * 检查应用是否有某个权限
     *
     * @param permission {@link java.security.Permission}
     * @return true:已分配该权限 false:无该权限
     */
    public boolean selfPermissionGranted(String permission) {
        // Android 6.0 以前，全部默认授权
        boolean result = true;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                if (getTargetSDKVersion() >= Build.VERSION_CODES.M) {
                    // targetSdkVersion >= 23, 使用Context#checkSelfPermission
                    result = ContextCompat.checkSelfPermission(application.getApplicationContext(), permission)
                            == PackageManager.PERMISSION_GRANTED;
                } else {
                    // targetSdkVersion < 23, 需要使用 PermissionChecker
                    result = PermissionChecker.checkSelfPermission(application.getApplicationContext(), permission)
                            == PermissionChecker.PERMISSION_GRANTED;
                }
            } catch (Exception ignore) {
                Timber.d("请将支持库版本升级到23及以后");
            }
        }
        return result;
    }

    /**
     * 获取应用targetSDKVersion
     *
     * @return {@link Integer} success:targetSDKVersion fail:-1
     */
    public int getTargetSDKVersion() {
        int targetSdkVersion = -1;
        try {
            PackageInfo info = application.getPackageManager().getPackageInfo(
                    application.getPackageName(), 0);
            targetSdkVersion = info.applicationInfo.targetSdkVersion;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return targetSdkVersion;
    }

}
