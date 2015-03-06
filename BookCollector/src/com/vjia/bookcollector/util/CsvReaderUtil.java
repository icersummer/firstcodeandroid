package com.vjia.bookcollector.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.util.Log;

import com.vjia.bookcollector.pojo.BookEntity;

public class CsvReaderUtil {

	private static final String tag = CsvReaderUtil.class.getName();

	public static List<BookEntity> readBookCsvs(Activity activity) {
		List<BookEntity> books = new ArrayList<BookEntity>();

		// currentAppFilesFolder : /data/data/com.vjia.bookcollector/files/
		File filesDir = activity.getFilesDir();
		String[] csvFiles = filesDir.list(new FilenameFilter() {
			@Override
			public boolean accept(File file, String path) {
				// TODO Auto-generated method stub
				if (path.endsWith(".csv") || path.endsWith(".CSV")) {
					return true;
				}
				return false;
			}
		});

		for (String csv : csvFiles) {
			Log.e(tag, "csv=" + csv);
			try {
				FileInputStream inputStream = activity.openFileInput(csv);
				byte[] bytes = new byte[1024];
				ByteArrayOutputStream arrayOutputStream=new ByteArrayOutputStream();
				while(inputStream.read(bytes) != -1) {
					arrayOutputStream.write(bytes, 0, bytes.length);
				}
				inputStream.close();
				arrayOutputStream.close();
				// charset can be : utf-8, gbk
				String content=new String(arrayOutputStream.toByteArray(), Charset.forName("utf-8"));
				BookEntity book = new BookEntity();
				convertToEntity(book, content);
				Log.v(tag, "content="+content);
				books.add(book);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return books;
	}

	/**
	 * content format: (2 lines)
	 * AUTHOR,BINDING,ISBN10,ISBN13,LINK,PRICE,PUBDATE,PUBLISHER,SUMMARY,TAGS,TITLE,
	 * Ben Forta,null,null,9787115164742,http://book.douban.com/subject/2269648/,29.00元,null,null,null,null,正则表达式必知必会,
	 * @param book
	 * @param content
	 */
	private static void convertToEntity(BookEntity book, final String content) {
		// TODO Auto-generated method stub
		String msg = String.format("r.index=%d, n.index=%d, rn.index=%d" , 
				content.indexOf("\r"),
				content.indexOf("\n"),
				content.indexOf("\r\n")
				);// r.index=-1, n.index=77, rn.index=-1
		Log.v(tag, msg);
		
		// HARD CODE TO EXTRACT THE CONTENT FOR NOW
		String replacement = "AUTHOR,BINDING,ISBN10,ISBN13,LINK,PRICE,PUBDATE,PUBLISHER,SUMMARY,TAGS,TITLE,";
		if(content.indexOf(replacement) < 0) {
			Log.e(tag, "ERROR : THE CSV FILE IS NOT A VALID EXPECTED CSV !!");
		}
		
		String tmpContent = content.replace(replacement, "");
		
		// char(10):\n , char(13):\r
		int a = tmpContent.charAt(0);
		Log.e(tag, "aint="+a);
		
		tmpContent = tmpContent.replace("\n", "");
		String[] tagValues = tmpContent.split(",");
		// FOR NOW ONLY CARE ABOUT : AUTHOR, ISBN13, LINK, PRICE, TITLE
		int idx_author = 0;//replacement.indexOf("AUTHOR");//TODO , WRONG LOGIC
		int idx_isbn13 = 3;//replacement.indexOf("ISBN13");
		int idx_link   = 4;//replacement.indexOf("LINK");
		int idx_price  = 5;//replacement.indexOf("PRICE");
		int idx_title  = 10;//replacement.indexOf("TITLE");
		
		String author = tagValues[idx_author];
		String isbn13 = tagValues[idx_isbn13];
		String link   = tagValues[idx_link];
		String price  = tagValues[idx_price];
		String title  = tagValues[idx_title];
		
		book.setAuthor(author);
		book.setIsbn13(isbn13);
		book.setLink(link);
		book.setPrice(price);
		book.setTitle(title);
		
		Log.v(tag, book.toString());
	}

}
