package com.vjia.bookcollector.isbn;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;

import android.app.Activity;
import android.util.Log;

import com.vjia.bookcollector.pojo.BookEntity;
import com.vjia.bookcollector.util.CsvWriterUtil;
import com.vjia.bookcollector.util.FileWriterUtil;
import com.vjia.bookcollector.util.XmlExtractorUtil;


public class IsbnFileUtils {

	/**
	 * the flag : if current code has the real abailabilty to extract book info from douban API response xml.
	 */
	private static final boolean TEST_MODE = false;
	private static final String CLASSNAME = IsbnFileUtils.class.getName();
	
	/*
	 * java 7, has a java.beans.PropertyDescriptor
	 */
	

	/**
	 * refer : http://docs.oracle.com/javase/tutorial/reflect/member/fieldValues.html
	 * @param xml
	 */
	public static void testReflect(String xml) {
		String aname = "author"; // attribute name
		String newValue = "test value" ;
		
		BookEntity book = new BookEntity();
		book.setAuthor("xyz");
		try {
			Class<?> c = book.getClass();
			Field field = c.getDeclaredField(aname);
			boolean accessible = field.isAccessible();
			field.setAccessible(true);
			System.out.println("1="+field.get(book));
			field.set(book,newValue);
			System.out.println("2="+field.get(book));	
			field.setAccessible(accessible);
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void generateResultInTextInDisk(String xml) {
		// TODO Auto-generated method stub
		BookEntity book = extractBookInfo(xml);
		
		String fileName = FileWriterUtil.createAndPersisteFile(book);
		
		// return a FLAG, and to prompt user of : success
		//TODO
	}
	

	/**
	 * @param xml
	 * @param bookInfoActivity 
	 * @return generate csv name
	 */
	public static String generateResultInCsvInDisk(String xml, Activity activity) {
		// TODO Auto-generated method stub
		BookEntity book = extractBookInfo(xml);
		
		String fileName = CsvWriterUtil.createAndPersisteFile(book, activity);
		
		// return a FLAG, and to prompt user of : success
		//TODO
		return fileName;
	}
	
	private static BookEntity extractBookInfo(String xml) {
		// TODO Auto-generated method stub
		BookEntity book = new BookEntity();
		if(TEST_MODE) {
			Log.i(CLASSNAME, "!! now is TEST_MODE of extractBookInfo !!");
			book.setAuthor("Mark Twin");
			book.setIsbn13("909090909765");
			book.setLink("http://google.com/vj");
			book.setPrice("$100");
		} else {
			Map<String, String> xmlMap = XmlExtractorUtil.getMapFromXml(xml);
			transformToBookEntity(book, xmlMap);
			Log.e(CLASSNAME, "book=" + book);
		}
		
		return book;
	}
	
	/**
	 * map format : { key1 : v1, v2; key2 : v } <br/>
	 * @param book
	 * @param xmlMap
	 */
	private static void transformToBookEntity(BookEntity book, final Map<String, String> map) {
		// TODO Auto-generated method stub
		Set<String> keys = map.keySet();
		for(String key:keys){
			//TODO
		}
		//TODO now to set title, author, isbn13, link, price
		String title = map.get("title");
		String author = map.get("author");
		String isbn13AndPrice = map.get("db:attribute");// isbn, price
		String link = map.get("link");
		
		int idx = isbn13AndPrice.indexOf(",");
		String isbn13 = isbn13AndPrice.substring(0, idx);
		String price = isbn13AndPrice.substring(idx + 1);
		
		book.setTitle(title);
		book.setAuthor(author);
		book.setIsbn13(isbn13);
		book.setLink(link);
		book.setPrice(price);
	}
	public static void main(String[] args) {
		IsbnFileUtils main = new IsbnFileUtils();
		main.testReflect("");
	}

}
