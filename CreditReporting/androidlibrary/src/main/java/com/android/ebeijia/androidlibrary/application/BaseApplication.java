package com.android.ebeijia.androidlibrary.application;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.android.ebeijia.androidlibrary.utils.AppInfoUtil;
import com.android.ebeijia.androidlibrary.utils.BasePerferenceUtil;
import com.android.ebeijia.androidlibrary.utils.Log;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.UUID;


/**
 * 检测了所有与用户相关的操作都是单步模式，都有关闭 dialog
 */
/**
 * xxxhdpi: 192*192 640 xxhdpi: 144*144 480 xhdpi:96*96 320 hdpi:72*72 240
 * mdpi:48*48 160 ldpi:36*36 120
 */
/**
 * 120 160 240 320 480 640 0.75 :1 :1.5: 2 :3 4
 */
// 6

/**
 * Created by wuqinghai on 16/2/29.
 */
public class BaseApplication extends Application{
    public static BaseApplication bContext;
    public boolean netWorkStateIsEnable=true;
    public String appVersionName;
    private Toast singleT;
    public  static String packageName;
    public String deviceId;
    public boolean isExit=false;
    @Override
    public void onCreate() {
        super.onCreate();
        bContext=this;
        startMyCrashHandler();
        startNetService();
        appVersionName = AppInfoUtil.getVersionName(this);
        packageName=AppInfoUtil.getPackageName(this);
        if (TextUtils.isEmpty(BasePerferenceUtil.getDeviceId(this))) {
            String deviceId = ((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
            String uuidMD5 = UUID.randomUUID().toString();
            BasePerferenceUtil.setDeviceId(this,
                    (TextUtils.isEmpty(deviceId) ? uuidMD5 : deviceId));
        }
        deviceId = BasePerferenceUtil.getDeviceId(this);


      ImageLoaderConfiguration configuration = ImageLoaderConfiguration
                .createDefault(this);

        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(configuration);
        Log.i("密度",getResources().getDisplayMetrics().densityDpi+"");
    }

    private void startNetService() {
        Intent intent = new Intent();
        intent.setClass(this, NetWorkService.class);
        startService(intent);
    }

    public void show(String text) {
        if(singleT==null)
        {
            singleT = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_LONG);
        }
        singleT.setText(text);
        singleT.show();
    }
    public void reStartApp(){


       if(true){
           return;
       }
        final Intent intent = getPackageManager().getLaunchIntentForPackage(getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//这句话可以去掉
        startActivity(intent);
        System.exit(0);//这句话一定要加


    }
    private void startMyCrashHandler() {
        MyCrashHandler crashHandler = MyCrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }
    public boolean isRunningForeground(Context context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        /**
         * am.getRunningTasks(1) 获取任务栈 1表示获取的最大数量
         */

        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String currentPackageName = cn.getPackageName();
        // ComponentInfo{com.android.systemui/com.android.systemui.recent.RecentsActivity}
        // ComponentInfo{com.android.systemui/com.android.systemui.recent.RecentAppFxActivity}
        if (!TextUtils.isEmpty(currentPackageName)
                && currentPackageName.equals(getPackageName())) {
            return true;
        }
        return false;
    }
    public boolean isTopActivity(Activity context) {
        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        /**
         * am.getRunningTasks(1) 获取任务栈 1表示获取的最大数量
         */

        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        String className = cn.getClassName();

        if (!TextUtils.isEmpty(className)
                && className.equals(context.getClass().getName())) {
            return true;
        }
        return false;
    }
    public boolean isExitApp(){

        if(!isExit) {
            isExit =true;
            Toast.makeText(getApplicationContext(),"再按一次退出应用",Toast.LENGTH_SHORT).show();
            new Handler(getMainLooper()).postDelayed(new Runnable() {
                @Override
                public void run() {
                    isExit = false;
                }
            }, 2000);
        }
        else{
            isExit=false;
        }
       return !isExit;
    }
}
