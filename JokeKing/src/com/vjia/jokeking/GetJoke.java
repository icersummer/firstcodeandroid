package com.vjia.jokeking;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;

public class GetJoke {

	public static List<Joke> lists = new ArrayList<Joke>();
	
	// +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
	public static String getNumber(Context context) {
		// TODO
		// Cursor cursor=context.getContentResolver().query(uri, projection,
		// selection, selectionArgs, sortOrder)
		lists.add(new Joke("This is Joker 1", 100));
		lists.add(new Joke("This is Joker 2", 10000));
		lists.add(new Joke("This is Joker 3", 999));
		return null;
	}

}
