package com.vjia.bookcollector.util;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XmlExtractorUtil {
	
	/**
	 * map format : { key1 : v1, v2; key2 : v } <br/>
	 * <p>
	 * TAG descripiton: <br/>
	 * category-tag seems no use, its format: <category scheme="http://www.douban.com/2007#kind" term="http://www.douban.com/2007#book" /> <br/>
	 * link-tag need special process, we only care its rel=altername hyperlink : its format : <link href="http://book.douban.com/subject/2629136/" rel="alternate" /> <br/>
	 * db-attribute, for now we can only care its isbn13 & price :its format: 	<db:attribute name="isbn13">9780974514017</db:attribute> <br/>
	 * db:tag, get all its tags, its format: <db:tag count="1" name="java" /> <br/>
	 * gd:rating, store it in map as {4,10,2}, meaning average/max/min values, its format: <gd:rating average="4" max="10" min="2" numRaters="3" /> <br/>
	 * 
	 * @param input
	 * @return
	 */
	public static Map<String, String> getMapFromXml(InputStream input) {
		Map<String, String> map = new HashMap();
		List<String> tagNames = Arrays.asList("id", "title", "category",
				"author", "link", "summary", "db:attribute", "db:tag",
				"gd:rating");
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();
			Document doc = builder.parse(input);
			for(String tag : tagNames){
				System.out.println(String.format("tag=%s", tag));
				NodeList list = doc.getElementsByTagName(tag);
//				System.out.println(String.format("list=%s", list));
				int nodeSize = list.getLength();
//				if(nodeSize==1){
//					Element element = (Element)list.item(0);
//					// only retrieve its content , as <title>Pragmatic Unit Testing in Java with JUnit</title>
//					// for author is special, 	<author>					<name>Andy Hunt</name>					</author>
//					String tc = element.getTextContent();
//					if("author".equals(element.getNodeName())){
//						// just get its FirstChild's content
//						tc = element.getFirstChild().getTextContent();
//					}
//					System.out.println(String.format("==============tag=%s, value=%s", tag, tc));
//				} else {
					// e.g. <author> has two tags in sample xml
					for(int i=0;i<nodeSize;i++){
						Element element = (Element)list.item(i);
						// only retrieve its content , as <title>Pragmatic Unit Testing in Java with JUnit</title>
						// for author is special, 	<author>					<name>Andy Hunt</name>					</author>
						String tc = element.getTextContent();
						final String elementNodeName = element.getNodeName();
						if("author".equals(elementNodeName)){
							// get value of : author - name - content
							Element authorElement = element;
							NodeList atts = authorElement.getElementsByTagName("name");
							Node nameNode = atts.item(0);//TODO may has other attribute
							tc = nameNode.getTextContent();
							System.out.println(String.format("==============tag=%s, value=%s", tag, tc));
							addIntoMap(map, tag, tc);
						} else if("link".equals(elementNodeName)) {
							Element linkElement = element;
							String relValue = linkElement.getAttribute("rel");
							if(relValue.equals("alternate")){
								tc = linkElement.getAttribute("href");
								System.out.println(String.format("==============tag=%s, value=%s", tag, tc));
								addIntoMap(map, tag, tc);
							}
						} else if("db:attribute".equals(elementNodeName)){
							Element dbAttElement = element;
							String nameAttValue = dbAttElement.getAttribute("name");
							if(nameAttValue.equals("isbn13") || nameAttValue.equals("price")){ //TODO other attributes TBD
								String nameAttContent = dbAttElement.getTextContent();
								tc = nameAttContent;
								addIntoMap(map, tag, tc);
								System.out.println(String.format("==============tag=%s, value=%s", tag, tc));
							}
						} else if("db:tag".equals(elementNodeName)){
							Element dbTagElement = element;
							String nameAttValue = dbTagElement.getAttribute("name");
							tc = nameAttValue;
							addIntoMap(map, tag, tc);
							System.out.println(String.format("==============tag=%s, value=%s", tag, tc));
						} else if("gd:rating".equals(elementNodeName)){
							Element ratingElement = element;
							String averageVal = ratingElement.getAttribute("average");
							String maxVal = ratingElement.getAttribute("max");
							String minVal = ratingElement.getAttribute("min");
							tc = averageVal + ","+maxVal+","+minVal;
							addIntoMap(map, tag, tc);
							System.out.println(String.format("==============tag=%s, value=%s", tag, tc));
						} else {
							System.out.println(String.format("==============tag=%s, value=%s", tag, tc));
							addIntoMap(map, tag, tc);
						}
					}
//				}
			}
			System.out.println("-------> MAP = " + map);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}

	/**
	 * 	map format : { key1 : v1, v2; key2 : v }
	 * @param map
	 * @param tag
	 * @param tc
	 */
	private static void addIntoMap(Map<String, String> map, String tag, String content) {
		// TODO Auto-generated method stub
		if(map.containsKey(tag)) {
			String existContent = map.get(tag);
			String newContent = existContent + "," + content;
			map.put(tag, newContent);
		} else {
			map.put(tag, content);
		}
	}

	/**
	 * map format : { key1 : v1, v2; key2 : v } <br/>
	 * <p>
	 * TAG descripiton: <br/>
	 * category-tag seems no use, its format: <category scheme="http://www.douban.com/2007#kind" term="http://www.douban.com/2007#book" /> <br/>
	 * link-tag need special process, we only care its rel=altername hyperlink : its format : <link href="http://book.douban.com/subject/2629136/" rel="alternate" /> <br/>
	 * db-attribute, for now we can only care its isbn13 & price :its format: 	<db:attribute name="isbn13">9780974514017</db:attribute> <br/>
	 * db:tag, get all its tags, its format: <db:tag count="1" name="java" /> <br/>
	 * gd:rating, store it in map as {4,10,2}, meaning average/max/min values, its format: <gd:rating average="4" max="10" min="2" numRaters="3" /> <br/>
	 * 
	 * @param input
	 * @return
	 */
	public static Map<String, String> getMapFromXml(final String xml) {
		Map<String, String> map = new HashMap();
		List<String> tagNames = Arrays.asList("id", "title", "category",
				"author", "link", "summary", "db:attribute", "db:tag",
				"gd:rating");
		try {
			DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			// !! note: 这里如果不转码，会报错：java.net.MalformedURLException: no protocol
			// 可能原因是请求的编码问题，直接使用 builder.parse(xml)报错， 转码后问题解决
			InputSource inputSource = new InputSource(new ByteArrayInputStream(xml.getBytes("utf-8")));
			Document doc = builder.parse(inputSource);
			javax.xml.parsers.SAXParser a=null;
			
			for(String tag : tagNames){
				System.out.println(String.format("tag=%s", tag));
				NodeList list = doc.getElementsByTagName(tag);
				int nodeSize = list.getLength();
				// e.g. <author> has two tags in sample xml
				for(int i=0;i<nodeSize;i++){
					Element element = (Element)list.item(i);
					// only retrieve its content , as <title>Pragmatic Unit Testing in Java with JUnit</title>
					// for author is special, 	<author>					<name>Andy Hunt</name>					</author>
					String tc = element.getTextContent();
					final String elementNodeName = element.getNodeName();
					if("author".equals(elementNodeName)){
//							tc = element.getFirstChild().getTextContent();
						// get value of : author - name - content
						Element authorElement = element;
						NodeList atts = authorElement.getElementsByTagName("name");
						Node nameNode = atts.item(0);//TODO may has other attribute
						tc = nameNode.getTextContent();
						System.out.println(String.format("==============tag=%s, value=%s", tag, tc));
						addIntoMap(map, tag, tc);
					} else if("link".equals(elementNodeName)) {
						Element linkElement = element;
						String relValue = linkElement.getAttribute("rel");
						if(relValue.equals("alternate")){
							tc = linkElement.getAttribute("href");
							System.out.println(String.format("==============tag=%s, value=%s", tag, tc));
							addIntoMap(map, tag, tc);
						}
					} else if("db:attribute".equals(elementNodeName)){
						Element dbAttElement = element;
						String nameAttValue = dbAttElement.getAttribute("name");
						if(nameAttValue.equals("isbn13") || nameAttValue.equals("price")){ //TODO other attributes TBD
							String nameAttContent = dbAttElement.getTextContent();
							tc = nameAttContent;
							addIntoMap(map, tag, tc);
							System.out.println(String.format("==============tag=%s, value=%s", tag, tc));
						}
					} else if("db:tag".equals(elementNodeName)){
						Element dbTagElement = element;
						String nameAttValue = dbTagElement.getAttribute("name");
						tc = nameAttValue;
						addIntoMap(map, tag, tc);
						System.out.println(String.format("==============tag=%s, value=%s", tag, tc));
					} else if("gd:rating".equals(elementNodeName)){
						Element ratingElement = element;
						String averageVal = ratingElement.getAttribute("average");
						String maxVal = ratingElement.getAttribute("max");
						String minVal = ratingElement.getAttribute("min");
						tc = averageVal + ","+maxVal+","+minVal;
						addIntoMap(map, tag, tc);
						System.out.println(String.format("==============tag=%s, value=%s", tag, tc));
					} else {
						System.out.println(String.format("==============tag=%s, value=%s", tag, tc));
						addIntoMap(map, tag, tc);
					}
				}
			}
			System.out.println("-------> MAP = " + map);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return map;
	}
	
	
	public static void main(String[] args) throws FileNotFoundException {
		String filepath = "F:/000_tmp/abc/src/com/vjia/bookcollector/pojo/Isbn_response.sample.xml";				
		InputStream is = new FileInputStream(new File(filepath));
		getMapFromXml(is);
	}
}

//sax、dom是两种对xml文档进行解析的方法(没有具体实现，只是接口)，所以只有它们是无法解析xml文档的；jaxp只是api，
//它进一步封装了sax、dom两种接口，并且提供了DomcumentBuilderFactory/DomcumentBuilder和SAXParserFactory/SAXParser（默认使用xerces解释器）。
//http://www.cnblogs.com/jiugehuanying/archive/2012/01/12/2320058.html
// InputStream in = TestJDom.class.getClassLoader().getResourceAsStream("test.xml");  