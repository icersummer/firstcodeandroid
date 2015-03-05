package com.vjia.bookcollector;

import java.io.IOException;

import com.vjia.bookcollector.isbn.IsbnFileUtils;
import com.vjia.bookcollector.isbn.IsbnLocator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * the activity to show the detail info of scan; will be redirected by
 * MainActivity
 */
public class BookInfoActivity extends Activity {
	private static final String CLASSNAME = BookInfoActivity.class.getName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(CLASSNAME, "running in BookInfoActivity...");
		this.setContentView(R.layout.book_info);

		Button return_to_home = (Button) findViewById(R.id.return_to_homepage);
		TextView book_isbn = (TextView) findViewById(R.id.book_isbn);
		TextView result_info = (TextView) findViewById(R.id.result_info);
		ImageView qrcode_bitmap_after_scan = (ImageView) findViewById(R.id.qrcode_bitmap_after_scan);

		// extract Intent data passed from MainActivity
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String s_book_isbn = (String) bundle.get("result");//book_isbn
		Bitmap p_bitmap_parcelable = (Bitmap) bundle.get("bitmap"); // bitmap of barcode
		
		Log.d(CLASSNAME, String.format("s_book_isbn=%s, p_bitmap_parcelable=%s", s_book_isbn, p_bitmap_parcelable));

		// show the needed info in the UI
		book_isbn.setText(s_book_isbn);
		result_info.setText(s_book_isbn);
		qrcode_bitmap_after_scan.setImageBitmap(p_bitmap_parcelable);

		// query ISBN via douban, store in local disk csv
		String generatedCsvName = queryAndStore(s_book_isbn);
		if(null != generatedCsvName){
			// TODO prompt into a Pop message, like alert
			
		}
		
		//TODO another good way to add Linstener
		// action handler for Return button
		return_to_home.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(BookInfoActivity.this, MainActivity.class);
				startActivity(intent);
			}
		});
	}

	private String queryAndStore(String isbn) {
		// TODO Auto-generated method stub
		try {
			String bookXML = IsbnLocator.fetchBookInfoByXML(isbn);
			String generatedCsvName = IsbnFileUtils.generateResultInCsvInDisk(bookXML, this);
			return generatedCsvName;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
    private View.OnClickListener listener = new OnClickListener() {  
        public void onClick(View v) {  
            Button view = (Button) v;  
            switch (view.getId()) {  
            case R.id.about_version_code:  // button 1
//                save();  
                break;  
            case R.id.action_settings:  // button 2
//                read();  
                break;  
  
            }  
  
        }  
  
    };  

}
