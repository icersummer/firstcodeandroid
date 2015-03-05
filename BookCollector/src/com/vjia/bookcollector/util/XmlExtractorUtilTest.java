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

	String xmlString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><entry xmlns=\"http://www.w3.org/2005/Atom\" xmlns:db=\"http://www.douban.com/xmlns/\" xmlns:gd=\"http://schemas.google.com/g/2005\" xmlns:openSearch=\"http://a9.com/-/spec/opensearchrss/1.0/\" xmlns:opensearch=\"http://a9.com/-/spec/opensearchrss/1.0/\">	<id>http://api.douban.com/book/subject/2269648</id>	<title>������ʽ��֪�ػ�</title>	<category scheme=\"http://www.douban.com/2007#kind\" term=\"http://www.douban.com/2007#book\"/>	<author>		<name>Ben Forta</name>	</author>	<link href=\"http://api.douban.com/book/subject/2269648\" rel=\"self\"/>	<link href=\"http://book.douban.com/subject/2269648/\" rel=\"alternate\"/>	<link href=\"http://img3.douban.com/spic/s2794811.jpg\" rel=\"image\"/>	<link href=\"http://m.douban.com/book/subject/2269648/\" rel=\"mobile\"/>	<summary>������ʽ��һ�������ޱ�ǿ������������������еĳ������������ͼ����ƽ̨�϶�������������ɸ��ָ��ӵ��ı�������������Ӽ򵥵��ı�ƥ�俪ʼ��ѭ�򽥽��ؽ����˺ܶิ�����ݣ����а����������á���������ֵ��ǰ����ң��ȵȡ�ÿ�¶�Ϊ����׼������������ʵ�õ�ʾ����������ȫ�桢ϵͳ����������������ʽ������������ȥ���ʵ�����⡣�����ʺϸ������Ժ�ƽ̨�Ŀ�����Ա��</summary>	<db:attribute name=\"isbn10\">7115164746</db:attribute>	<db:attribute name=\"isbn13\">9787115164742</db:attribute>	<db:attribute name=\"title\">������ʽ��֪�ػ�</db:attribute>	<db:attribute name=\"pages\">139</db:attribute>	<db:attribute name=\"translator\">����</db:attribute>	<db:attribute name=\"translator\">������</db:attribute>	<db:attribute name=\"translator\">������</db:attribute>	<db:attribute name=\"author\">Ben Forta</db:attribute>	<db:attribute name=\"price\">29.00Ԫ</db:attribute>	<db:attribute name=\"publisher\">�����ʵ������</db:attribute>	<db:attribute name=\"binding\">ƽװ</db:attribute>	<db:attribute name=\"pubdate\">2007</db:attribute>	<db:tag count=\"240\" name=\"������ʽ\"/>	<db:tag count=\"81\" name=\"�����\"/>	<db:tag count=\"73\" name=\"���\"/>	<db:tag count=\"34\" name=\"����\"/>	<db:tag count=\"32\" name=\"Regex\"/>	<db:tag count=\"24\" name=\"regexp\"/>	<db:tag count=\"24\" name=\"programming\"/>	<db:tag count=\"19\" name=\"�������\"/>	<gd:rating average=\"8.6\" max=\"10\" min=\"0\" numRaters=\"273\"/></entry>";
}
