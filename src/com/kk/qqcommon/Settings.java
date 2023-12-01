package com.kk.qqcommon;

/**
 * 客户端自定义设置类
 *
 * @author KK
 * @version 1.0
 */
public class Settings {
    private static String downloadPath = "D:/";

    public static String getDownloadPath() {
        return downloadPath;
    }

    public static void setDownloadPath(String downloadPath) {
        Settings.downloadPath = downloadPath;
    }


}
