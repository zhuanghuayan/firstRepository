package com.android.ebeijia.androidlibrary.application;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.widget.Toast;


public class NetWorkService extends Service {
	private FZEGJReceiver receiver;
	private ConnectivityManager manager;
	private IntentFilter filter;

	class FZEGJReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
			if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
				checkNetWorkState();
			}
		}
	}
	
	protected void checkNetWorkState() {
		NetworkInfo info = manager.getActiveNetworkInfo();
		if (info != null && info.isAvailable()) {
			BaseApplication.bContext.netWorkStateIsEnable = true;

			
		} else {

			BaseApplication.bContext.netWorkStateIsEnable = false;
			toast();
			
		}

	}
	public static final String NET_TIP="网络未连接";
	private void toast(){

		if(BaseApplication.bContext.isRunningForeground(this)){
			BaseApplication.bContext.show(NET_TIP);
		}
	}
	@Override
	public void onCreate() {
		receiver = new FZEGJReceiver();
		filter = new IntentFilter();
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		this.registerReceiver(receiver, filter);
		manager = (ConnectivityManager) this
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		super.onCreate();
	}
	@Override
	public void onDestroy() {
		this.unregisterReceiver(receiver);
		super.onDestroy();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public IBinder onBind(Intent arg0) {
		
		return null;
	}

}
