package com.vjia.helloandroid;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class HelloAndroidActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hello_android_layout);
		Log.v("HelloWorldActivity", "onCreate execute");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.hello_android, menu);
		return true;
	}

}
