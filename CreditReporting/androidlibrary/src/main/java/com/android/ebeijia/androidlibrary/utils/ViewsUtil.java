package com.android.ebeijia.androidlibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;


import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ViewsUtil {



	public static int getStatusBarHeight(Context context) {
		Class<?> c = null;
		Object obj = null;
		Field field = null;
		int x = 0, sbar = 0;
		try {
			c = Class.forName("com.android.internal.R$dimen");
			obj = c.newInstance();
			field = c.getField("status_bar_height");
			x = Integer.parseInt(field.get(obj).toString());
			sbar = context.getResources().getDimensionPixelSize(x);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return sbar;
	}
	public static  int dp2px(int px,Context context){
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,px,context.getResources().getDisplayMetrics());
	}

     public static DisplayImageOptions getDefaultImageLoaderOption(int onLoadingImage,int failImage){
		 DisplayImageOptions options = new DisplayImageOptions.Builder()
				 .showImageOnLoading(onLoadingImage)
				 .showImageOnFail(failImage)
				 .cacheInMemory(true)
				 .cacheOnDisk(true)
				 .bitmapConfig(Bitmap.Config.RGB_565)
				 .build();
		 return options;
	 }
	public static DisplayImageOptions getDefaultImageLoaderOption(int onLoadingImage){
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(onLoadingImage)

				.cacheInMemory(true)
				.cacheOnDisk(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.build();
		//.displayer(new FadeInBitmapDisplayer(350))
		return options;
	}
	public static DisplayImageOptions getNoFadeImageLoaderOption(int onLoadingImage){
		DisplayImageOptions options = new DisplayImageOptions.Builder()
				.showImageOnLoading(onLoadingImage)
				.cacheInMemory(true)
				.cacheOnDisk(true)
				.bitmapConfig(Bitmap.Config.RGB_565)
				.build();
		return options;
	}
	public static void displayImage(String url,ImageView imageView, final View progress,int loadPlaceHolder,int failPlaceHolder){
		DisplayImageOptions options=null;
		if(failPlaceHolder>0){
			options=getDefaultImageLoaderOption(loadPlaceHolder,failPlaceHolder);
		}
		else{
			options=getDefaultImageLoaderOption(loadPlaceHolder);
		}
		if(progress!=null){
			ImageLoader.getInstance().displayImage(url, imageView, options, new ImageLoadingListener() {
				@Override
				public void onLoadingStarted(String s, View view) {
					progress.setVisibility(View.VISIBLE);
				}

				@Override
				public void onLoadingFailed(String s, View view, FailReason failReason) {
					progress.setVisibility(View.GONE);
				}

				@Override
				public void onLoadingComplete(String s, View view, Bitmap bitmap) {
					progress.setVisibility(View.GONE);
				}

				@Override
				public void onLoadingCancelled(String s, View view) {
					progress.setVisibility(View.GONE);
				}
			});
		}
		else{
			ImageLoader.getInstance().displayImage(url, imageView, options);
		}





	}
	public static void displayImage(String url,ImageView imageView,View progress,int onLoadingImage){
	   displayImage(url,imageView,progress,onLoadingImage,0);


	}
	public static void displayImage(String url,ImageView imageView,int loadPlaceHolder){
            displayImage(url,imageView,null,loadPlaceHolder);
	}

	public static void setLandscape(Context context) {

		if (context != null) {
			if (android.os.Build.VERSION.SDK_INT >= 9 ) {
				((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
			} else {
				((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
			}
		}
	}
	public static boolean isLandscape(Activity context) {

		return context.getRequestedOrientation()==ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE||context.getRequestedOrientation()==ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
	}
	public static boolean isPortrait(Activity context) {

		return context.getRequestedOrientation()==ActivityInfo.SCREEN_ORIENTATION_PORTRAIT||context.getRequestedOrientation()==ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;
	}

	public static void setPortrait(Activity context) {

		if (context != null) {
			if (android.os.Build.VERSION.SDK_INT >= 9 ) {
				((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
			} else {
				((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			}
		}
	}

	public static void forceRefreshView(View target,int visible){
		target.setVisibility(visible);
	    ViewGroup viewGroup= (ViewGroup) target.getParent();
		viewGroup.requestLayout();
		viewGroup.invalidate();
	}
	/*public static void setLandscape(Activity context) {

		if (context != null) {

				((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

		}
	}

	public static void setPortrait(Activity context) {

		if (context != null) {

				((Activity) context).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

		}
	}*/

}
