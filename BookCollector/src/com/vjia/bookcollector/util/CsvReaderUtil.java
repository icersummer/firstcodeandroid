package com.vjia.bookcollector.util;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import android.app.Activity;

import com.vjia.bookcollector.pojo.BookEntity;

public class CsvReaderUtil {

	public static List<BookEntity> readBookCsvs(Activity activity) {
		// TODO Auto-generated method stub

		try {
			FileInputStream inputStream = activity.openFileInput("fileName");
			byte[] bytes = new byte[1024];
			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
			while (inputStream.read(bytes) != -1) {

			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

}
