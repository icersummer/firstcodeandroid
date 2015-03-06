package com.vjia.bookcollector;


import com.vjia.bookcollector.isbn.IsbnFileUtils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private final static String CLASSNAME = MainActivity.class.getName();
	
	// int[] array to store IMAGEs
	private int[] photo = {R.drawable.nav_menu_home_ph, R.drawable.nav_menu_hot_ph,
			R.drawable.nav_menu_category_ph,R.drawable.nav_menu_like_ph};
	private int[] photoSelected = {R.drawable.nav_menu_home_selected_ph,
			R.drawable.nav_menu_hot_selected_ph,
			R.drawable.nav_menu_category_active_ph,
			R.drawable.nav_menu_like_active_ph};
		

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//点击按钮跳转到二维码扫描界面
		Button mButton = (Button) findViewById(R.id.main2_start_scan);
		mButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, MipcaActivityCapture.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
			}
		});
		
		//the button to show all local scanned books
		Button main2_show_booklist = (Button)findViewById(R.id.main2_show_booklist);
		main2_show_booklist.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, BookListActivity.class);
				startActivity(intent);
			}
		});
		
		// THE BUTTON TO SHOW README
		Button static_readme_btn = (Button) findViewById(R.id.static_readme_btn);
		static_readme_btn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				String newline = "\n";
				String msg = "3 functions :" + newline + newline
						+ "1. Scan a book, store its info into CSV file;"+newline
						+ "2. View all stored book CSV info;"+newline
						+ "3. Export the stored CSV."
						+ newline
						+ newline;
				alterDialog(msg);
			}
		});
		
		/*
		// test button to go to UI test layout
		Button ui_test_button = (Button) findViewById(R.id.ui_test_button);
		ui_test_button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, UITestActivity.class);
				startActivity(intent);
			}
		});
		

		// test button to go to UI Relative layout
		Button ui_relative_layout_test_button = (Button) findViewById(R.id.ui_relative_layout_test_button);
		ui_relative_layout_test_button.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent();
				intent.setClass(MainActivity.this, UiRelativeLayoutTestActivity.class);
				startActivity(intent);
			}
		});
		*/
	}
	
	private void alterDialog(String msg) {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this)
				.setTitle("BOOK COLLECTOR README")
				.setMessage(msg)
				.setPositiveButton("确定", null)
				.show();
	}
}


