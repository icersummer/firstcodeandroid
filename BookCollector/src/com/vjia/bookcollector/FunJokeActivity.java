package com.vjia.bookcollector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.vjia.bookcollector.adapter.JokeAdapter;
import com.vjia.bookcollector.pojo.Joke;

public class FunJokeActivity extends Activity {

	private static final String tag = FunJokeActivity.class.getName();

	final String id_field = "id";
	final String xhid_field = "xhid";
	final String author_field = "author";
	final String content_field = "content";
	final String picUrl_field = "picUrl";
	final String status_field = "status";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.fun_joke_api);

		List<Joke> jokes = buildJokes();
		JokeAdapter adapter = new JokeAdapter(jokes, this);
		ListView lv = (ListView)this.findViewById(R.id.listview_fun_joke);
		lv.setAdapter(adapter);
		Toast.makeText(this, "Total Jokes: " + jokes.size(), Toast.LENGTH_LONG).show();

	}

	private List<Joke> buildJokes() {
		List<Joke> jokes = new ArrayList<Joke>();
		String jsonJokes = getJsonJokeStr();
		Map<String, Map<String, String>> jokeMap = transformJsonJoke(jsonJokes);
		Log.d(tag, "jokeMap=" + jokeMap);
		
		Set<Entry<String, Map<String, String>>> entrySet = jokeMap.entrySet();
		for(Entry<String, Map<String, String>> entry : entrySet){
			String key = entry.getKey();
			Map<String, String> value = entry.getValue();
		}
		
		Collection<Map<String, String>> values = jokeMap.values();
		Iterator<Map<String, String>> iter = values.iterator();
		while(iter.hasNext()){
			Map<String, String> value = iter.next();
			Joke joke = new Joke();
			joke.setId(value.get(id_field));
			joke.setXhid(value.get(xhid_field));
			joke.setAuthor(value.get(author_field));
			joke.setContent(value.get(content_field));
			joke.setPicUrl(value.get(picUrl_field));
			joke.setStatus(value.get(status_field));
			jokes.add(joke);
		}
		
		return jokes;
	}

	private Map<String, Map<String, String>> transformJsonJoke(String jsonJokes) {
		// TODO Auto-generated method stub
		if(jsonJokes==null){
			Log.e(tag, "JSON RESULT IS NULL !!");
		}
		Map<String, Map<String, String>> jokeMap = new HashMap<String, Map<String, String>>();
		try {
			JSONObject jsonObject = new JSONObject(jsonJokes);
			JSONArray jsonArray = jsonObject.getJSONArray("detail");
			Map<String, String> innerMap;
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jo = jsonArray.getJSONObject(i);
				String id = jo.getString(id_field);
				String xhid = jo.getString(xhid_field);
				String author = jo.getString(author_field);
				String content = jo.getString(content_field);
				String picUrl = jo.getString(picUrl_field);
				String status = jo.getString(status_field);

				innerMap = new HashMap<String, String>();
				innerMap.put(id_field, id);
				innerMap.put(xhid_field, xhid);
				innerMap.put(author_field, author);
				innerMap.put(content_field, content);
				innerMap.put(picUrl_field, picUrl);
				innerMap.put(status_field, status);

				jokeMap.put(id, innerMap);
			}
		} catch (JSONException e) {
			Log.e("Getjoke", e.toString());
			e.printStackTrace();
		}
		return jokeMap;
	}

	private String getJsonJokeStr() {
		// TODO Auto-generated method stub
		String jokeAPI = "http://114.215.158.105/biz/bizserver/xiaohua/list.do";
		Log.d(tag, " jokeAPI = " + jokeAPI);
		URL url;
		URLConnection conn;
		try {
			url = new URL(jokeAPI);
			conn = url.openConnection();
			InputStream is = conn.getInputStream();
			InputStreamReader isr = new InputStreamReader(is, "utf-8");
			BufferedReader br = new BufferedReader(isr);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line.trim());
			}
			br.close();
			Log.d(tag, "the return JSON string : " + sb.toString());
			return sb.toString();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
