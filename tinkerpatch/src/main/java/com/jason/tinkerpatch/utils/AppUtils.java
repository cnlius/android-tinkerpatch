package com.jason.tinkerpatch.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * application 相关
 * Created by liusong on 2017/9/6.
 */

public class AppUtils {

    /**
     * 版本名称
     *
     * @param context
     * @return
     */
    public static String getAppVersionName(Context context) {
        try {
            PackageInfo pkgInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (pkgInfo != null) {
                return pkgInfo.versionName;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }
}
