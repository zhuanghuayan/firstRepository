package com.android.ebeijia.androidlibrary.application;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;

import com.android.ebeijia.androidlibrary.utils.FileUtil;
import com.android.ebeijia.androidlibrary.utils.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MyCrashHandler implements UncaughtExceptionHandler {
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final String EXP_FILE = "exception.txt";
	private static final String TAG = "MyCrashHandler";
	private Context context;
	// 单例设计模式
	private static MyCrashHandler myCrashHandler;

	private MyCrashHandler() {

	}

	public synchronized static MyCrashHandler getInstance() {
		if (myCrashHandler == null) {
			myCrashHandler = new MyCrashHandler();
		}
		return myCrashHandler;
	}

	// 对其进行初始化，后面获取应用相关信息需要使用到上下文
	public void init(Context context) {
		this.context = context;
		// 获取到当前线程，设置未捕获异常的处理
		Thread.currentThread().setUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		// 1.获取应用程序版本信息
		StringBuilder sb = new StringBuilder();
		PackageManager pm = context.getPackageManager();
		try {
			PackageInfo info = pm.getPackageInfo(context.getPackageName(), 0);
			String versionName = info.versionName;

			sb.append("程序版本号为:" + versionName);
			sb.append("\n");
			// 2.获取手机硬件信息
			Build build = new Build();// 手机硬件信息封装在Builde中，通过反射获取其字段，包括私有 暴力破解
			Field[] fields = build.getClass().getDeclaredFields();
			for (int i = 1; i < fields.length; i++) {
				// 暴力访问
				fields[i].setAccessible(true);
				String name = fields[i].getName();
				String value = fields[i].get(null).toString();
				sb.append(name + " = " + value);
				sb.append("\n");
			}
			// 3.获取异常报错信息
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			final String errorInfo = sw.toString();
			Log.e(TAG, errorInfo);
			/* sb.append(errorInfo); */

			/*
			 * Toast.makeText(context, "很抱歉,程序出现异常,即将退出.",
			 * Toast.LENGTH_LONG).show();
			 */
			/*
			 * sendSMS("18321841049", errorInfo.replaceAll("", "").substring(0,
			 * 20)) ;
			 */

			final String phoneInfo = sb.toString();
			// 4.上传到服务器

			if (Log.isDebugOther) {
				new Thread() {
					public void run() {
						writeFile(FileUtil.getAPPFilePath(context), errorInfo,
								phoneInfo);
						// 最后让应用程序自杀
						
						BaseApplication.bContext.reStartApp();
						/*
						 * android.os.Process.killProcess(android.os.Process.myPid
						 * ());
						 */
					};
				}.start();
			}
		} catch (Exception e) {
			Log.e(e);
		}
      //重新启动应用
		if(!Log.isDebugOther){
			BaseApplication.bContext.reStartApp();
		}
	}

	

	public void writeFile(File dir, String error, String phoneInfo) {

		FileOutputStream out = null;
		try {
			File file = new File(dir, EXP_FILE);
			error = "\r\n" + sdf.format(new Date()) + "\r\n" + error;
			if (file.exists()) {
				out = new FileOutputStream(file, true);
				out.write(error.getBytes());

			} else {
				dir.mkdirs();
				out = new FileOutputStream(file, true);
				// 不存在 phoneInfo 不写入文件
				out.write(phoneInfo.getBytes());
				out.write(error.getBytes());
			}
		} catch (Exception e) {
			Log.e(e);
		} finally {

			try {
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {

				Log.e(e);
			} finally {
			}

		}

	}

	public void sendSMS(String phoneNumber, String message) {
		// 获取短信管理器
		android.telephony.SmsManager smsManager = android.telephony.SmsManager
				.getDefault();
		// 拆分短信内容（手机短信长度限制）
		List<String> divideContents = smsManager.divideMessage(message);
		for (String text : divideContents) {
			smsManager.sendTextMessage(phoneNumber, null, text, null, null);
		}
	}
}
