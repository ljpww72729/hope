package cc.lkme.hope.utils;

public interface SystemInfoInter {
    /**
     * 获取IMEI号
     *
     * @return String
     */
    String getUdid();

    /**
     * 获取AndroidId
     *
     * @return String
     */
    String getOpenUDID();

    /**
     * 获取操作系统
     *
     * @return String Android 或 iOS
     */
    String getOS();

    /**
     * 获取操作系统版本号
     *
     * @return String
     */
    String getOSVersion();

    /**
     * 获取设备型号
     *
     * @return String
     */
    String getDeviceModel();

    /**
     * 获取mac地址
     *
     * @return String
     */
    String getMac();
}
