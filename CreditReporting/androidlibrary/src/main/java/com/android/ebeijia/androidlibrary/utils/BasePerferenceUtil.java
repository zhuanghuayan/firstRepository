package com.android.ebeijia.androidlibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;


import java.util.List;
import java.util.Map;

public class BasePerferenceUtil {

  
    
    
   
    

    
    public static String A="BASE_APPLICATION";// 数据库名称
	public static String DEVICE_ID="DEVICE_ID";


	public static String APP="App";//数据库名

	public static String VERSION_NAME="version_name";

	/**
	 *
	 * @param context  判断第一次是否使用过  true 使用过了 false 没有
	 * @return
	 */
	public  static boolean isUsed(Context context){
		return getLastUseVersionName(context).equals(AppInfoUtil.getVersionName(context));
	}

	/**
	 *
	 * @param context   本地数据库存储的
	 * @return
	 */
	private static String getLastUseVersionName(Context context){
		SharedPreferences sp = context.getSharedPreferences(APP,Context.MODE_PRIVATE);
		return sp.getString(VERSION_NAME, "");
	}

	/**
	 *
	 * @param context  设置已经使用过本app的 状态
	 * @return
	 */
	public static boolean setHasUsed(Context context){
		SharedPreferences sp = context.getSharedPreferences(APP, Context.MODE_PRIVATE);
		return sp.edit().putString(VERSION_NAME, AppInfoUtil.getVersionName(context)).commit();
	}

    public static boolean isOpenedActivity(Activity activity){
		SharedPreferences sp = activity.getSharedPreferences(APP, Context.MODE_PRIVATE);
		String simpleName=activity.getClass().getSimpleName();
		String name=activity.getClass().getName();
		//return name.equals(sp.getString(name,""));
		return true;
	}
	public static boolean setHasOpenedActivity(Activity activity){
		SharedPreferences sp = activity.getSharedPreferences(APP, Context.MODE_PRIVATE);
		String simpleName=activity.getClass().getSimpleName();
		String name=activity.getClass().getName();
		return sp.edit().putString(name, name).commit();

	}


public  static String getDeviceId(Context context){
	
	SharedPreferences sp = context.getSharedPreferences(A,
			Context.MODE_PRIVATE);
	
	return sp.getString(DEVICE_ID, null);
	
}
public  static boolean  setDeviceId(Context context,String deviceId){
	
	SharedPreferences sp = context.getSharedPreferences(A,
			Context.MODE_PRIVATE);
	return sp.edit().putString(DEVICE_ID, deviceId).commit();
	
	
}






}
