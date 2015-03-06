package com.vjia.bookcollector;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.vjia.bookcollector.adapter.BookAdapter;
import com.vjia.bookcollector.pojo.BookEntity;
import com.vjia.bookcollector.util.CsvReaderUtil;

public class BookListActivity extends Activity {
	
	// whether we're doing a testing or run as real
	private boolean testing = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.book_list);
		
		// prepare the BookList data
		List<BookEntity> lists = buildBookList();
		// build ListView & Adapter
		BookAdapter adapter = new BookAdapter(lists, this);
		ListView lv = (ListView) findViewById(R.id.lv);
		lv.setAdapter(adapter);
		Toast.makeText(this, "Total Files : " + lists.size(), Toast.LENGTH_LONG).show();
	}

	private List<BookEntity> buildBookList() {
		// TODO Auto-generated method stub
		if(testing) {
			return buildFakeBooks();
		}
		
		List<BookEntity> books = CsvReaderUtil.readBookCsvs(this);
		
		return books;
	}

	@Deprecated
	private List<BookEntity> buildFakeBooks() {
		// TODO Auto-generated method stub
		List<BookEntity> list = new ArrayList<BookEntity>();
		BookEntity book = new BookEntity("english", "max");
		book.setIsbn13("9999999999999");
		book.setPrice("$909");
		book.setLink("http://google.com?hongkong");
		list.add(book);
		for(int i =0; i<20; i++){
			book = new BookEntity("title"+i,"author"+i);
			list.add(book);
		}
		
		return list;
	}

}
