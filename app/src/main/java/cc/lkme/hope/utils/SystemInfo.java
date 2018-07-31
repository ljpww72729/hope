package cc.lkme.hope.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.net.NetworkInterface;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import timber.log.Timber;

@Singleton
public class SystemInfo implements SystemInfoInter {

    Application application;
    //mac地址
    private static String macSerial = "";

    @Inject
    PermissionUtils permissionUtils;

    @Inject
    public SystemInfo(Application application) {
        this.application = application;
    }


    @SuppressLint("MissingPermission")
    @Override
    public String getUdid() {
        try {
            TelephonyManager tm = (TelephonyManager) application.getSystemService(Context.TELEPHONY_SERVICE);
            if (permissionUtils.selfPermissionGranted(Manifest.permission.READ_PHONE_STATE)) {
                return tm.getDeviceId();
            }
        } catch (Exception ignore) {
            Timber.d(ignore);
        }
        return "";
    }

    @Override
    public String getOpenUDID() {
        try {
            return Settings.System.getString(application.getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception ignore) {
            Timber.d(ignore);
        }
        return "";
    }

    @Override
    public String getOS() {
        return "Android";
    }

    @Override
    public String getOSVersion() {
        return String.valueOf(Build.VERSION.SDK_INT);
    }

    @Override
    public String getDeviceModel() {
        return Build.MODEL;
    }

    @Override
    public String getMac() {
        try {
            if (!TextUtils.isEmpty(macSerial)) {
                return macSerial;
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                WifiManager wifi = (WifiManager) application.getSystemService(Context.WIFI_SERVICE);
                if (wifi != null) {
                    WifiInfo wifiInfo = wifi.getConnectionInfo();
                    if (wifiInfo != null && !TextUtils.isEmpty(wifiInfo.getMacAddress()) &&
                            !TextUtils.equals("02:00:00:00:00:00", wifiInfo.getMacAddress().trim())) {
                        macSerial = wifiInfo.getMacAddress().trim();
                        return macSerial;
                    }
                }
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                macSerial = getMacCompat23();
            } else {
                macSerial = getMacCompat24();
            }

            if (!TextUtils.isEmpty(macSerial)) {
                return macSerial;
            }
        } catch (Exception ignore) {
        }
        return "";
    }

    /**
     * Android 23及以下版本使用该方法获取mac地址
     */
    private String getMacCompat23() throws Exception {
        String mac23 = null;
        String str = "";
        String streth = "";
        //无线mac地址
        Process pp = Runtime.getRuntime().exec(
                "cat /sys/class/net/wlan0/address");
        InputStreamReader ir = new InputStreamReader(pp.getInputStream());
        LineNumberReader input = new LineNumberReader(ir);

        for (; null != str; ) {
            str = input.readLine();
            if (str != null) {
                mac23 = str.trim();// 去空格
                break;
            }
        }
        if (!TextUtils.isEmpty(mac23)) {
            return mac23;
        }
        //有线mac地址
        Process ppeth = Runtime.getRuntime().exec(
                "cat /sys/class/net/eth0/address");
        InputStreamReader ireth = new InputStreamReader(ppeth.getInputStream());
        LineNumberReader inputeth = new LineNumberReader(ireth);

        for (; null != streth; ) {
            streth = inputeth.readLine();
            if (streth != null) {
                mac23 = streth.trim();// 去空格
                break;
            }
        }
        return mac23;
    }

    /**
     * Android7.0及以上通过网络接口取mac地址
     */
    private String getMacCompat24() throws Exception {
        List<NetworkInterface> all = Collections.list(NetworkInterface.getNetworkInterfaces());
        for (NetworkInterface nif : all) {
            if (!nif.getName().equalsIgnoreCase("wlan0")) continue;

            byte[] macBytes = nif.getHardwareAddress();
            if (macBytes == null) {
                return null;
            }

            StringBuilder res1 = new StringBuilder();
            for (byte b : macBytes) {
                res1.append(String.format("%02X:", b));
            }

            if (res1.length() > 0) {
                res1.deleteCharAt(res1.length() - 1);
            }
            return res1.toString();
        }
        return null;
    }
}
