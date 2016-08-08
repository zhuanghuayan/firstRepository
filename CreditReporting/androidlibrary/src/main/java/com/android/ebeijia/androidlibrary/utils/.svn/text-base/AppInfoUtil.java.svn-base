package com.android.ebeijia.androidlibrary.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by wuqinghai on 16/2/28.
 */
public class AppInfoUtil {
    public static String getVersionName(Context context){
        PackageManager pm=	context.getPackageManager();
        PackageInfo info=null;
        try {
            info = pm.getPackageInfo(context.getPackageName(), 0);

        } catch (PackageManager.NameNotFoundException e) {
            Log.e(e);
        }
        if(info!=null){
            return info.versionName;
        }
        return "";
    }
    public static String getPackageName(Context context){

        return context.getPackageName();
    }


}
