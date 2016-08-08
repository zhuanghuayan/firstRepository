package com.android.ebeijia.androidlibrary.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class FileUtil {

	
	public static File getAPPFilePath(Context context) {
		
		File cacheDir = null;
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			 cacheDir = new File(
					Environment.getExternalStorageDirectory(),AppInfoUtil.getPackageName(context));

		} else {
			cacheDir = context.getCacheDir();
		}
		return cacheDir;
	}
}
