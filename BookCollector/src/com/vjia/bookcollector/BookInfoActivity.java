package com.vjia.bookcollector;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		this.setContentView(R.layout.book_info);

		Button return_to_home = (Button) findViewById(R.id.return_to_homepage);
		TextView book_isbn = (TextView) findViewById(R.id.book_isbn);
		TextView result_info = (TextView) findViewById(R.id.result_info);
		ImageView qrcode_bitmap_after_scan = (ImageView) findViewById(R.id.qrcode_bitmap_after_scan);

		// extract Intent data passed from MainActivity
		Intent intent = getIntent();
		Bundle bundle = intent.getExtras();
		String s_book_isbn = (String) bundle.get("book_isbn");
		Parcelable p_bitmap_parcelable = (Parcelable) bundle
				.get("bitmap_parcelable");

		book_isbn.setText(s_book_isbn);
		result_info.setText(s_book_isbn);
		qrcode_bitmap_after_scan.setImageBitmap((Bitmap) p_bitmap_parcelable);

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

}
