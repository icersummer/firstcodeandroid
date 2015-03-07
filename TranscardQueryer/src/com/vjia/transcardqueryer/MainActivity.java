package com.vjia.transcardqueryer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private final static String tag = MainActivity.class.getName();
	private final String SPTCC_API_URL = "http://220.248.75.36/handapp/PGcardAmtServlet?arg1=";
//	private final String CARD_NOT_FOUND = "The card is not found.";
	private final float CARD_NOT_FOUND = -100.0f;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// CARD_NO INPUT
		final EditText edittext_cardno = (EditText)findViewById(R.id.edittext_cardno);
		
		// SHOW LEFT MONEY
		final TextView textview_card_money = (TextView)this.findViewById(R.id.textview_card_money);
		
		// START QUERY BUTTON
		Button button_query= (Button)this.findViewById(R.id.button_query);
		button_query.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View view) {
				String cardNo = edittext_cardno.getText().toString();
				float money = queryMoneyByCardNo(cardNo);
				textview_card_money.setText(money + "");
				popupMoney(money);
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	

	private float queryMoneyByCardNo(String cardNo) {
		// TODO Auto-generated method stub
		String requestUrl = SPTCC_API_URL + cardNo;
		try {
			URL url = new URL(requestUrl);
			URLConnection conn =url.openConnection();
			InputStream is= conn.getInputStream();
			InputStreamReader isr=new InputStreamReader(is);
			BufferedReader br=new BufferedReader(isr);
			//THE RESPONSE ONLY 1 LINE : null('47.0');
			// if no find, return : null('null');
			String response = br.readLine();
			int idx1=response.indexOf('\'');
			int idx2=response.lastIndexOf('\'');
			String money = response.substring(idx1+1, idx2);
			Log.d(tag, String.format("response=%s,money=%s", response, money));
			if(!money.equals("null")){
				return Float.valueOf(money);
			}else{
				return CARD_NOT_FOUND ;
			}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0.0f;
	}

	private void popupMoney(float money) {
		// TODO Auto-generated method stub
		Toast.makeText(this, "Money queried : " + money, Toast.LENGTH_LONG).show();
	}
}
