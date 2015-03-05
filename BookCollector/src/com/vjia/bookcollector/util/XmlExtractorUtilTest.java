package com.vjia.bookcollector.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Test;

public class XmlExtractorUtilTest {

	@Test
	public void getMapFromXmlTest() throws FileNotFoundException {
		String filepath = "F:/000_tmp/abc/src/com/vjia/bookcollector/pojo/Isbn_response.sample.xml";
		InputStream is = new FileInputStream(new File(filepath));
		Map map = XmlExtractorUtil.getMapFromXml(is);
		Assert.assertTrue(!map.isEmpty());
	}

	@Test
	public void getMapFromXmlTestPlainXML(){
		Map map = XmlExtractorUtil.getMapFromXml(xmlString);
		Assert.assertTrue(!map.isEmpty());
	}

	String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><entry xmlns=\"http://www.w3.org/2005/Atom\" xmlns:db=\"http://www.douban.com/xmlns/\" xmlns:gd=\"http://schemas.google.com/g/2005\" xmlns:openSearch=\"http://a9.com/-/spec/opensearchrss/1.0/\" xmlns:opensearch=\"http://a9.com/-/spec/opensearchrss/1.0/\">	<id>http://api.douban.com/book/subject/2269648</id>	<title>正则表达式必知必会</title>	<category scheme=\"http://www.douban.com/2007#kind\" term=\"http://www.douban.com/2007#book\"/>	<author>		<name>Ben Forta</name>	</author>	<link href=\"http://api.douban.com/book/subject/2269648\" rel=\"self\"/>	<link href=\"http://book.douban.com/subject/2269648/\" rel=\"alternate\"/>	<link href=\"http://img3.douban.com/spic/s2794811.jpg\" rel=\"image\"/>	<link href=\"http://m.douban.com/book/subject/2269648/\" rel=\"mobile\"/>	<summary>正则表达式是一种威力无比强大的武器，几乎在所有的程序设计语言里和计算机平台上都可以用它来完成各种复杂的文本处理工作。本书从简单的文本匹配开始，循序渐进地介绍了很多复杂内容，其中包括回溯引用、条件性求值和前后查找，等等。每章都为读者准备了许多简明又实用的示例，有助于全面、系统、快速掌握正则表达式，并运用它们去解决实际问题。本书适合各种语言和平台的开发人员。</summary>	<db:attribute name=\"isbn10\">7115164746</db:attribute>	<db:attribute name=\"isbn13\">9787115164742</db:attribute>	<db:attribute name=\"title\">正则表达式必知必会</db:attribute>	<db:attribute name=\"pages\">139</db:attribute>	<db:attribute name=\"translator\">杨涛</db:attribute>	<db:attribute name=\"translator\">王建桥</db:attribute>	<db:attribute name=\"translator\">杨晓云</db:attribute>	<db:attribute name=\"author\">Ben Forta</db:attribute>	<db:attribute name=\"price\">29.00元</db:attribute>	<db:attribute name=\"publisher\">人民邮电出版社</db:attribute>	<db:attribute name=\"binding\">平装</db:attribute>	<db:attribute name=\"pubdate\">2007</db:attribute>	<db:tag count=\"240\" name=\"正则表达式\"/>	<db:tag count=\"81\" name=\"计算机\"/>	<db:tag count=\"73\" name=\"编程\"/>	<db:tag count=\"34\" name=\"正则\"/>	<db:tag count=\"32\" name=\"Regex\"/>	<db:tag count=\"24\" name=\"regexp\"/>	<db:tag count=\"24\" name=\"programming\"/>	<db:tag count=\"19\" name=\"软件开发\"/>	<gd:rating average=\"8.6\" max=\"10\" min=\"0\" numRaters=\"273\"/></entry>";
}
