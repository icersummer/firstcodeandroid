package com.vjia.jokeking;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
	private static boolean test = Boolean.TRUE;
	// private static boolean test = Boolean.FALSE;
	public static List<Joke> lists = new ArrayList<Joke>();
	static String jsonStr = null;
	private static final String ERROR = "error";

	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static String getNumber(Context context) {
		String githubUrl = "https://github.com/icersummer/firstcodeandroid/JokeKing/data/jokedata.json";
		githubUrl = "https://raw.githubusercontent.com/icersummer/firstcodeandroid/master/JokeKing/data/jokedata.json";
		String jsonStr;

		if (!test) {
			jsonStr = queryFromGithub(githubUrl, "");
			if (jsonStr.equals(ERROR)) {
				lists.add(new Joke(ERROR + "nonono error9 - This is Joker 1",
						100));
				lists.add(new Joke(ERROR + "222 error - This is Joker 2", 10000));
				lists.add(new Joke(ERROR + "333 error - This is Joker 3", 999));
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
			System.out
					.println(" ************* NOW IN TEST MODE ****************");
			lists.add(new Joke(test + "nonono error9 - This is Joker 1", 100));
			lists.add(new Joke(test + "222 error - This is Joker 2", 10000));
			lists.add(new Joke(test + "333 error - This is Joker 3", 999));
		}

		// get Youdao translation
//		Map<String, String> translations = getYoudaoTranslations();

		return null;
	}

	/*
	 * 12-04 19:45:44.720: I/com.vjia.jokeking.MainActivity(872):
	 * line={"translation"
	 * :["好"],"basic":{"us-phonetic":"ɡʊd","phonetic":"gʊd","uk-phonetic"
	 * :"gʊd","explains"
	 * :["n. 好处；善行；慷慨的行为","adj. 好的；优良的；愉快的；虔诚的","adv. 好","n. (Good)人名；(英)古德；(瑞典)戈德"
	 * ]
	 * },"query":"good","errorCode":0,"web":[{"value":["好","良好","商品"],"key":"Good"
	 * },{"value":["公共物品","公益事业","公共财"],"key":"public good"},{"value":["好健康",
	 * "好健康牛初乳咀嚼片","健康"],"key":"Good Health"}]}
	 */
	private static Map<String, String> getYoudaoTranslations() {
		try {
			String jsonStr = getYoudaoJSONResponse();
			JSONObject jsonObject = new JSONObject(jsonStr);
			int i=1;
			Log.d(classname+i++, jsonObject.get("translation").toString());
			Log.d(classname+i++, jsonObject.get("basic").toString());
			Log.d(classname+i++, jsonObject.get("explains").toString());
			Log.d(classname+i++, jsonObject.get("query").toString());
			Log.d(classname+i++, jsonObject.get("errorCode").toString());
			Log.d(classname+i++, jsonObject.get("web").toString());
			return null;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private static String getYoudaoJSONResponse() {
		String jsonStr = "";
		try {
			String youdaoDemoURL = "http://fanyi.youdao.com/openapi.do?keyfrom=jokeking&key=1352117898&type=data&doctype=json&version=1.1&q=good";
			URL url = new URL(youdaoDemoURL);
			URLConnection conn = url.openConnection();
			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			String line;
			while ((line = br.readLine()) != null) {
				Log.i(classname, "line=" + line);
				jsonStr += line;
			}
			br.close();
			isr.close();
			is.close();
			return jsonStr;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			Log.e(classname, "connection == null ?" + (connection == null));
			InputStream in = connection.getInputStream();
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(in));
			StringBuilder response = new StringBuilder();
			String line;
			while ((line = reader.readLine()) != null) {
				if (line.startsWith("#")) {
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
	 * deprecate this method for now, to not use the async http request, but to
	 * use the direct http get request
	 * 
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
			for (;;) {
				if (jsonStr == null) {
					continue;
				} else {
					break;
				}
			}

			if (jsonStr.equals(ERROR)) {
				// throw new Exception("error occur");
				lists.add(new Joke("error9 - This is Joker 1", 100));
				lists.add(new Joke("error - This is Joker 2", 10000));
				lists.add(new Joke("error - This is Joker 3", 999));
			} else {
				try {
					// JSONObject jsonObject = new
					// JSONObject("{d:test,e:true,b:1.1,c:1,a:1}");
					/*
					 * { "employees": [ { "firstName":"John" , "lastName":"Doe"
					 * }, { "firstName":"Anna" , "lastName":"Smith" }, {
					 * "firstName":"Peter" , "lastName":"Jones" } ] }
					 */
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
		HttpUtil.sendHttpRequest(url, new HttpCallbackListener() {

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
			}
		});
	}
}
