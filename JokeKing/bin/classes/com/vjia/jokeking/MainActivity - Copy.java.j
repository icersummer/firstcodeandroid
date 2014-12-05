package com.vjia.jokeking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends Activity {
	private static String classname = MainActivity.class.getName();

	private ListView listView;
	MyAdapter myAdapter;
	/**
	 * doctype=json ; doctype=xml
	 */
	private static String youdaoDemoURL = "http://fanyi.youdao.com/openapi.do?keyfrom=jokeking&key=1352117898&type=data&doctype=json&version=1.1&q=good";

	private Handler handler = null;
	TextView showYoudaoTV ;
	
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		GetJoke.getNumber(this);
		listView = (ListView) this.findViewById(R.id.listView);
		myAdapter = new MyAdapter(GetJoke.lists, this);
		listView.setAdapter(myAdapter);

		// test code to know how HTTP codes
		Button httpTestBtn = (Button) this.findViewById(R.id.httpTestBtn);
		showYoudaoTV = (TextView) this.findViewById(R.id.showHTTP);
		httpTestBtn.setOnClickListener(new HttpOnClickListener());
		//创建属于主线程的handler
		handler=new Handler();
	}
	
	Runnable runnableUi = new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			// update UI
			showYoudaoTV.setText("") 
		}
		
	};
	
	String httpContent;
	
	class HttpOnClickListener implements OnClickListener{
			@Override
			public void onClick(View view) {
				
				showYoudaoTV.setText("Get Loading...");
				new Thread(){
					public void run(){
						handler.post(runnableUi);
						try {
							URL url = new URL(youdaoDemoURL);
							URLConnection conn = url.openConnection();
							InputStream is = conn.getInputStream();
							InputStreamReader isr = new InputStreamReader(is,
									"utf-8");
							BufferedReader br = new BufferedReader(isr);
							String line;
							while ((line = br.readLine()) != null) {
								Log.i(classname, "line=" + line);
							}
							httpContent="done!";
							br.close();
							isr.close();
							is.close();
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}.start();
				
				// TODO Auto-generated method stub
				new AsyncTask<String, Void, Void>() {

					@Override
					protected Void doInBackground(String... params) {
						// TODO Auto-generated method stub
						try {
							URL url = new URL(params[0]);
							URLConnection conn = url.openConnection();
							InputStream is = conn.getInputStream();
							InputStreamReader isr = new InputStreamReader(is,
									"utf-8");
							BufferedReader br = new BufferedReader(isr);
							String line;
							while ((line = br.readLine()) != null) {
								Log.i(classname, "line=" + line);
							}
							/*
							 * 直接这么写是不行的，会报错：
							 * showYoudaoTV.setText("Get HTTP done.");
							 * 因为Android中相关的view和控件不是线程安全的，必须单独处理。
							 * 必须通过Handler对象更新UI，具体看http://blog.csdn.net/djx123456/article/details/6325983。
							 * 
							 */
							br.close();
							isr.close();
							is.close();
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						return null;
					}

				}.execute(youdaoDemoURL);
				/*
				 * 使用有道翻译的API 有道翻译API申请成功 API key：1352117898 keyfrom：jokeking •
				 * 创建时间：2014-12-04 • 网站名称：jokeking • 网站地址：http://jokeking.com
				 */
			}
	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	// @Override
	// public boolean onCreateOptionsMenu(Menu menu) {
	// // Inflate the menu; this adds items to the action bar if it is present.
	// getMenuInflater().inflate(R.menu.main, menu);
	// return true;
	// }

}



/*
	class HttpOnClickListener implements OnClickListener{
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				new AsyncTask<String, Void, Void>() {

					@Override
					protected Void doInBackground(String... params) {
						// TODO Auto-generated method stub
						try {
							URL url = new URL(params[0]);
							URLConnection conn = url.openConnection();
							InputStream is = conn.getInputStream();
							InputStreamReader isr = new InputStreamReader(is,
									"utf-8");
							BufferedReader br = new BufferedReader(isr);
							String line;
							while ((line = br.readLine()) != null) {
								Log.i(classname, "line=" + line);
							}
							br.close();
							isr.close();
							is.close();
						} catch (MalformedURLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return null;
					}

				}.execute(youdaoDemoURL);
			}

	}
*/