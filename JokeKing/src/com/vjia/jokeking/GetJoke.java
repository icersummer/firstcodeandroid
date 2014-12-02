package com.vjia.jokeking;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

public class GetJoke {

	/**
	 * if test, use local fake list data; else get data from URL connection.
	 */
//	private static boolean test = Boolean.TRUE;
	private static boolean test = Boolean.FALSE;
	public static List<Joke> lists = new ArrayList<Joke>();
	static String jsonStr = null;
	private static String error = "error";

	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static String getNumber(Context context) {
		if (!test) {
			// TODO
			// Cursor cursor=context.getContentResolver().query(uri, projection,
			// selection, selectionArgs, sortOrder)
			String githubUrl = "https://github.com/icersummer/firstcodeandroid/JokeKing/data/jokedata.json";
			queryFromGithub(githubUrl);
			for(;;){
				if(jsonStr ==null){
					continue;
				} else {
					break;
				}
			}

			if(jsonStr.equals(error)){
//				throw new Exception("error occur");
				lists.add(new Joke("error - This is Joker 1", 100));
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
				jsonStr = error;
				System.out.println("in queryFromGithub.onError...");
			}});
	}
}
