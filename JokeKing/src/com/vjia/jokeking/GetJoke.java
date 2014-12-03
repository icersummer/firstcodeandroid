package com.vjia.jokeking;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class GetJoke {
	private static String classname = GetJoke.class.getName();
	/**
	 * if test, use local fake list data; else get data from URL connection.
	 */
//	private static boolean test = Boolean.TRUE;
	private static boolean test = Boolean.FALSE;
	public static List<Joke> lists = new ArrayList<Joke>();
	static String jsonStr = null;
	private static final String ERROR = "error";


	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static String getNumber(Context context) {
		String githubUrl = "https://github.com/icersummer/firstcodeandroid/JokeKing/data/jokedata.json";
		githubUrl = "https://raw.githubusercontent.com/icersummer/firstcodeandroid/master/JokeKing/data/jokedata.json";
		String jsonStr ; 

		if(!test){
			jsonStr = queryFromGithub(githubUrl, ""); 
			if(jsonStr.equals(ERROR)){
				lists.add(new Joke(ERROR+"nonono error9 - This is Joker 1", 100));
				lists.add(new Joke(ERROR+"222 error - This is Joker 2", 10000));
				lists.add(new Joke(ERROR+"333 error - This is Joker 3", 999));
			} else {
				try {
					JSONObject jsonObject = new JSONObject(jsonStr);
					JSONArray jsonArray = jsonObject.getJSONArray("names");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jo = jsonArray.getJSONObject(i);
						String content = jo.getString("content");
						String number = jo.getString("number");
						int review = Integer.valueOf(number);
						lists.add(new Joke(content, review));
					}
				} catch (JSONException e) {
					Log.e("Getjoke", e.toString());
					e.printStackTrace();
				}
			}
		} else {
			System.out.println(" ************* NOW IN TEST MODE ****************");
			lists.add(new Joke(test+"nonono error9 - This is Joker 1", 100));
			lists.add(new Joke(test+"222 error - This is Joker 2", 10000));
			lists.add(new Joke(test+"333 error - This is Joker 3", 999));
		}
		return null;
	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	private static String queryFromGithub(String address, String string) {
		HttpURLConnection connection = null;
		try {
			URL url = new URL(address);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.setConnectTimeout(8000);
			connection.setReadTimeout(8000);
			Log.e(classname, "connection == null ?" + (connection==null));
			InputStream in = connection.getInputStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				if(line.startsWith("#")){
					// skip comments
					continue;
				}
				response.append(line);
			}
			return response.toString().trim();
		} catch (Exception e) {
			Log.e("HttpUtil", e.toString());
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
		return ERROR;
	}

	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	/**
	 * deprecate this method for now, 
	 * to not use the async http request, 
	 * but to use the direct http get request
	 * @param context
	 * @return
	 */
	public static String getNumber_(Context context) {
		if (!test) {
			// TODO
			// Cursor cursor=context.getContentResolver().query(uri, projection,
			// selection, selectionArgs, sortOrder)
			String githubUrl = "https://github.com/icersummer/firstcodeandroid/JokeKing/data/jokedata.json";
			githubUrl = "https://raw.githubusercontent.com/icersummer/firstcodeandroid/master/JokeKing/data/jokedata.json";
			queryFromGithub(githubUrl);
			for(;;){
				if(jsonStr ==null){
					continue;
				} else {
					break;
				}
			}

			if(jsonStr.equals(ERROR)){
//				throw new Exception("error occur");
				lists.add(new Joke("error9 - This is Joker 1", 100));
				lists.add(new Joke("error - This is Joker 2", 10000));
				lists.add(new Joke("error - This is Joker 3", 999));
			} else {
				try {
//					JSONObject jsonObject = new JSONObject("{d:test,e:true,b:1.1,c:1,a:1}");
					/*{
						"employees": [
						{ "firstName":"John" , "lastName":"Doe" },
						{ "firstName":"Anna" , "lastName":"Smith" },
						{ "firstName":"Peter" , "lastName":"Jones" }
						]
						}*/
					JSONObject jsonObject = new JSONObject(jsonStr);
					JSONArray jsonArray = jsonObject.getJSONArray("names");
					for (int i = 0; i < jsonArray.length(); i++) {
						JSONObject jo = jsonArray.getJSONObject(i);
						String content = jo.getString("content");
						String number = jo.getString("number");
						int review = Integer.valueOf(number);
						lists.add(new Joke(content, review));
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			
		} else {
			lists.add(new Joke("This is Joker 1", 100));
			lists.add(new Joke("This is Joker 2", 10000));
			lists.add(new Joke("This is Joker 3", 999));
		}
		return null;
	}

	private static void queryFromGithub(String url) {
		// TODO Auto-generated method stub
		HttpUtil.sendHttpRequest(url, new HttpCallbackListener(){

			@Override
			public void onFinish(String response) {
				// TODO Auto-generated method stub
				jsonStr = response;
				System.out.println("in queryFromGithub.onFinish...");
			}

			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				jsonStr = ERROR;
				Log.e("GetJoke", e.toString());
				e.printStackTrace();
				System.out.println("in queryFromGithub.onError...");
			}});
	}
}
