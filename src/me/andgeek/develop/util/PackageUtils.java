package me.andgeek.develop.util;

import me.andgeek.develop.BaseApplication;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

public final class PackageUtils {
    
    /**
     * 获取版本名称
     * 
     * @return 当前应用的版本名称
     */
    public static String getVersionName() {
        String version = "";
        try {
            PackageManager manager = BaseApplication.getInstance().getPackageManager();
            PackageInfo info = manager.getPackageInfo(BaseApplication.getInstance().getPackageName(), 0);
            version = info.versionName;
        }
        catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }
    
    /**
     * 获取版本号
     * 
     * @return 当前应用的版本号
     */
    public static int getVersionCode() {
        int version = -1;
        try {
            PackageManager manager = BaseApplication.getInstance().getPackageManager();
            PackageInfo info = manager.getPackageInfo(BaseApplication.getInstance().getPackageName(), 0);
            version = info.versionCode;
        }
        catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }
    
}
