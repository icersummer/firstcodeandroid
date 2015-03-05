package com.vjia.bookcollector.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import com.vjia.bookcollector.pojo.BookEntity;

/**
 * write out the specific information to disk ( Android storage ) .<br/>
 * 1) for a Book's info, given a ISBN number, then should generate a file named
 * ISBN_number.properties in folder %root%\books_info\ISBN_number.prop; content
 * like : title: xxx, isbn: xxx
 * 
 * 2) also generate a file named books_list.properties in folder
 * %root%\books_info\, it contains which books are already in current folder,
 * with format: ISBN_number1=book title ISBN_number2=book title ...
 * 
 */
public class FileWriterUtil {

	private static final String CLASSNAME = FileWriterUtil.class.getName();
	
	/**
	* Enumeration for permissions.
	* <p>
	*/
	private static enum ReadPermission {
		VIEW,
		CREATE,
		EDIT,
		DELETE
	}

	
	/**
	 * return the generated file_name
	 * 
	 * @param book
	 * @return
	 */
	public static String createAndPersisteFile(BookEntity book) {
		if (book == null)
			return null;

		String fileName = book.getIsbn13();
		String fileContent = getFileContentInPropertiesFormat(book);

		boolean created = createFile(fileName, fileContent);

		if (created) {
			Log.i(CLASSNAME, "book file created : " + fileName);
		} else {
			Log.e(CLASSNAME, "!! error occur, check logs : " + fileName);
		}

		return fileName;
	}

	private static boolean createFile(String fileName, String fileContent) {
		// TODO Auto-generated method stub
		String bashFilePath = "";
		String currentFilePath = bashFilePath + "\\books_info\\" + fileName;
		File file = new File(currentFilePath);
		if (file.exists()) {
			// report error, and return false
			Log.e(CLASSNAME, "erro : file exist already : " + currentFilePath);
			return false;
		}

		try {
			boolean created = file.createNewFile();
			// append content
			FileWriter fwriter = new FileWriter(currentFilePath);
			fwriter.append(fileContent);
			fwriter.flush();
			fwriter.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return true;
	}

	private static String getFileContentInPropertiesFormat(BookEntity book) {
		// TODO Auto-generated method stub
		StringBuffer bookInfo = new StringBuffer();
		Field[] fileds = BookEntity.class.getFields();
		for (Field field : fileds) {
			boolean initAccessible = field.isAccessible();
			String fieldName = field.getName();
			try {
				field.setAccessible(true);
				Object value = field.get(book);
				// clazz can be : string, date, int, double, float
				// TODO to cast them
				Class clazz = field.getDeclaringClass();

				bookInfo.append(fieldName).append("=")
						.append(String.valueOf(value)).append("\n");
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				field.setAccessible(initAccessible);
			}
		}

		return bookInfo.toString();
	}

}
