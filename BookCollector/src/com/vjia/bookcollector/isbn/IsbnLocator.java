package com.vjia.bookcollector.isbn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import android.util.Log;

public class IsbnLocator {
	private static final String CLASSNAME = IsbnLocator.class.getName();
	
	// http://api.douban.com/people/1345767?apikey={apikey}
	// http://www.douban.com/service/apikey/
	
	static String apikey = "00531b774a81caa000f95265df855fe0";  
	static String isbnUrl = "http://api.douban.com/book/subject/isbn/";  
	  
	public static void main(String[] args) throws Exception {  
	    //requestUrl = isbnUrl + isbnNo + "?apikey=" + apikey;  
	    //eg:http://api.douban.com/book/subject/isbn/9787111298854?apikey=00531b774a81caa000f95265df855fe0  
		IsbnLocator isbnTest = new IsbnLocator();  
	    String isbnNo = "9787111298854";   
	    isbnNo = "9780974514017";  
	    String xml = isbnTest.fetchBookInfoByXML(isbnNo);  
	    System.out.println(xml);  
	} 
	  
	/** 
	 * 从根据isbn号从豆瓣获取数据。已经申请apikey，每分钟最多40次请求，足够用。 
	 * @param isbnNo 
	 * @return XML string info of specific Book
	 * @throws IOException  
	 */  
	public static String fetchBookInfoByXML(String isbnNo) throws IOException  {  
		// try-catch here:
		// to catch the exception that the device(Phone) doesn't connect to internet;
		// correction of this: connect gprs; or connect wifi; or connect iphone's share wlan.
	    try {
			String requestUrl = isbnUrl + isbnNo + "?apikey=" + apikey;  
			Log.d(CLASSNAME, "requestURL to douban is : " +requestUrl);
			URL url = new URL(requestUrl);  
			URLConnection conn = url.openConnection();  
			InputStream is = conn.getInputStream();  
			InputStreamReader isr = new InputStreamReader(is, "utf-8");  
			BufferedReader br = new BufferedReader(isr);  
			StringBuilder sb = new StringBuilder();  
			  
			String line = null;  
			while ((line = br.readLine()) != null) {  
			    sb.append(line);  
			}
			  
			br.close();  
			Log.d(CLASSNAME, "the return of douban API is :\n"+sb.toString());
			return sb.toString();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			Log.e(CLASSNAME, "=== The network is not available to get internet resource !!");
			e.printStackTrace();
		}
	    
	    return null;
	}  


}