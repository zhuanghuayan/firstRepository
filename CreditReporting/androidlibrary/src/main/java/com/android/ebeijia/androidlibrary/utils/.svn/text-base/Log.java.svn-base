package com.android.ebeijia.androidlibrary.utils;

public class Log {
	/**
	 * 上线把debug变成false
	 */
	private  static final boolean isDebug=true;//日志
	public  static final boolean isDebugOther=true;//其他(false会验证服务器的证书，如果服务器上面的证书不是授权的，请改为true(true 会在本地SD卡上面写入一些异常的数据，正式环境不会写（如果改为false）))
	public static void i(String tag,String msg){
	   
		if(isDebug){
		 android.util.Log.i(tag, msg);
		}
	}
	public static void e(String tag,String msg){
		if(isDebug){
		 android.util.Log.e(tag, msg);
		}
	}
	public static void d(String tag,String msg){
		if(isDebug){
		 android.util.Log.d(tag, msg);
		}
	}
	public static void e(Exception e){
		if(isDebug){
		  e.printStackTrace();
		}
	}
	public static void System_out_printLn(Object o){
		if(isDebug){
		 System.out.print(o);
		}
	}

}
