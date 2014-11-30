package com.vjia.jokeking;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ListView;

public class MainActivity extends Activity {

	private ListView listView;
	MyAdapter myAdapter;
	
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		GetJoke.getNumber(this);
		listView=(ListView) this.findViewById(R.id.listView);
		myAdapter = new MyAdapter(GetJoke.lists, this);
		listView.setAdapter(myAdapter);
	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.main, menu);
//		return true;
//	}

}
