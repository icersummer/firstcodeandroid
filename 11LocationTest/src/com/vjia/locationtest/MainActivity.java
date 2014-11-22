package com.vjia.locationtest;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private TextView positionTextView;
	private LocationManager locationManager;
	private String provider;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		positionTextView = (TextView)this.findViewById(R.id.position_text_view);
		locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		// get all the available Position Provider
		List<String> providerList=locationManager.getProviders(true);
		if(providerList.contains(LocationManager.GPS_PROVIDER)){
			provider=LocationManager.GPS_PROVIDER;
		}else if(providerList.contains(LocationManager.NETWORK_PROVIDER)){
			provider=LocationManager.NETWORK_PROVIDER;
		}else{
			// no available Postion Provider, show Toast to user
			Toast.makeText(this, "No location provider to use", Toast.LENGTH_SHORT);
			return;
		}
		
		Location location = locationManager.getLastKnownLocation(provider);
		if(location==null){
			// show current device's location info
			showLocation(location);
		}
		//(String provider, long minTime, float minDistance, LocationListener listener)
		locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);
	}
	
	protected void onDestroy(){
		super.onDestroy();
		if(locationManager!=null){
			// review the Listener when shutdown the APP
			locationManager.removeUpdates(locationListener);
		}
	}
	
	LocationListener locationListener = new LocationListener(){

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub
			showLocation(location);
		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onProviderEnabled(String arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub
			
		}
		
	};

	private void showLocation(Location location) {
		// TODO Auto-generated method stub
		String currentPosition= String.format("latitude is %s %n logitude is %s", 
				location.getLatitude(), location.getLongitude());
		positionTextView.setText(currentPosition);
	}
}
