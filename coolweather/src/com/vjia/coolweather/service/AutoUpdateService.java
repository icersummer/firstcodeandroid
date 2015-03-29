package com.vjia.coolweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import com.vjia.coolweather.util.HttpCallbackListener;
import com.vjia.coolweather.util.HttpUtil;
import com.vjia.coolweather.util.Utility;

public class AutoUpdateService extends Service {

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		new Thread(new Runnable(){
			@Override
			public void run() {
				// TODO Auto-generated method stub
				updateWeather();
			}

		}).start();
		
		AlarmManager manager= (AlarmManager) getSystemService(ALARM_SERVICE);
		int anHour = 8*60*60*1000;// 8小时的毫秒数
		long triggerAtTime = SystemClock.elapsedRealtime()+anHour;
		Intent i= new Intent(this, AutoUpdateService.class);//TODO keypoint of auto update
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
		manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
		return super.onStartCommand(intent, flags, startId);
	}
	
	private void updateWeather() {
		// TODO Auto-generated method stub
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String weatherCode = prefs.getString("weather_code", "");
		String address= "https://www.weather.com.cn/data/cityinfo/"+weatherCode+".html";
		HttpUtil.sendHttpRequest(address, new HttpCallbackListener(){
			@Override
			public void onFinish(String response) {
				// TODO Auto-generated method stub
				Utility.handleWeatherResponse(AutoUpdateService.this, response);
			}

			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				e.printStackTrace();
			}}
		);
	}
	

}
