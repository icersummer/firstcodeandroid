package com.vjia.bookcollector.util;

import java.util.Iterator;
import java.util.Map;

public class JSONHelper {
	
	/**
	 * convert a Maps to JS array, using the JSON standard;
	 * will convert both keys and values to strings using the <code>toString()</code> on objects
	 * @param arrayName
	 * @param map
	 * @return
	 */
	public static String toJSONArray(String arrayName, Map<?,?> maps) {
		return toJSONKeyedArray(arrayName, maps, false, false);
	}

	private static String toJSONKeyedArray(String arrayName, Map<?, ?> maps,
			boolean sortMapByValues, boolean sortMapByKeys) {
		// TODO Auto-generated method stub
		StringBuffer buffer = new StringBuffer();
		Iterator<?> keys = maps.keySet().iterator();
		String keyStr = "";
		Object key=null;
		Map map=null;
		
		buffer.append("var " + arrayName+"=new Array();"+"\n");
		while(keys.hasNext()){
			key=keys.next();
			keyStr=key.toString();
			map=(Map)maps.get(key);
			buffer.append("");
		}
		
		return null;
	}

}
