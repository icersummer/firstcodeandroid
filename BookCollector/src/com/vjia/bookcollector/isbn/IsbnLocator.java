package com.vjia.bookcollector.isbn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class IsbnLocator {
	// http://api.douban.com/people/1345767?apikey={apikey}
	// http://www.douban.com/service/apikey/
	
	String apikey = "00531b774a81caa000f95265df855fe0";  
	String isbnUrl = "http://api.douban.com/book/subject/isbn/";  
	  
	public static void main(String[] args) throws Exception {  
	    //requestUrl = isbnUrl + isbnNo + "?apikey=" + apikey;  
	    //eg:http://api.douban.com/book/subject/isbn/9787111298854?apikey=111111111111111111114  
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
	public String fetchBookInfoByXML(String isbnNo) throws IOException  {  
	    String requestUrl = isbnUrl + isbnNo + "?apikey=" + apikey;  
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
	    return sb.toString();  
	}  


}