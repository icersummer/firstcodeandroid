package com.vjia.bookcollector.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class JSONHelper {

	/**
	 * convert a Maps to JS array, using the JSON standard; will convert both
	 * keys and values to strings using the <code>toString()</code> on objects
	 * 
	 * @param arrayName
	 * @param map
	 * @return
	 */
	public static String toJSONArray(String arrayName, Map<?, ?> maps) {
		return toJSONKeyedArray(arrayName, maps, false, false);
	}

	public static String toJSONKeyedArray(String arrayName, Map<?, ?> maps,
			boolean sortMapByValues, boolean sortMapByKeys) {
		// TODO Auto-generated method stub
		StringBuffer buffer = new StringBuffer();
		Iterator<?> keys = maps.keySet().iterator();
		String keyStr = "";
		Object key = null;
		Map map = null;

		buffer.append("var " + arrayName + "=new Array();" + "\n");
		while (keys.hasNext()) {
			key = keys.next();
			keyStr = key.toString();
			map = (Map) maps.get(key);
			buffer.append("");
			// TODO refer other API
		}
		return buffer.toString();
	}

	/**
	 * Convert a Collection of Maps to a JS array, using the JSON standard; will
	 * convert both key and values to Strings using the toString() on the
	 * objects
	 * 
	 * @param maps
	 * @return
	 */
	public static String toJSONArray(Collection<?> maps) {
		return toJSONArray(maps, false, false);
	}

	public static String toJSONArray(Collection<?> maps,
			boolean sortMapsByValues, boolean sortMapsByKeys) {
		// TODO Auto-generated method stub
		StringBuffer buffer = new StringBuffer();
		Iterator<?> iter = maps.iterator();
		Map<?, ?> map = null;
		buffer.append("[");
		while (iter.hasNext()) {
			map = (Map) iter.next();
			buffer.append(toJSONMap(map, sortMapsByValues, sortMapsByKeys));
			if (iter.hasNext()) {
				buffer.append(",");
			}
		}
		buffer.append("]");
		return buffer.toString();
	}

	public static String toJSONMap(Map<?, ?> map, boolean sortByValues,
			boolean sortByKeys) {
		// TODO Auto-generated method stub
		Collection<?> keySet = map.keySet();
		if (sortByValues) {
			// get a specific order by KEYS array
			Vector values = new Vector(map.values());
			Collections.sort(values);
			Collection sorted = new ArrayList();
			Iterator t = values.iterator();
			Iterator k = null;
			Object key = "";
			Object value = "";
			while (t.hasNext()) {
				value = t.next();
				k = map.keySet().iterator();
				while (k.hasNext()) {
					key = k.next();
					if (value.equals(map.get(key))) {
						sorted.add(key);
					}
				}
			}
			keySet = sorted;
		} else if(sortByKeys){
			Vector sorted=new Vector(keySet);
			Collections.sort(sorted);
			keySet=sorted;
		}
		
		// JSON string
		StringBuffer buffer=new StringBuffer();
		Iterator keys=keySet.iterator();
		String keyStr="";
		String valueStr="";
		Object key=null;
		buffer.append("{");
		while(keys.hasNext()){
			key=keys.next();
			keyStr=key.toString();
			valueStr=map.get(key).toString();
			
			buffer.append(""); //TODO refer to other API
			if(keys.hasNext()){
				buffer.append(",");
			}
		}
		buffer.append("}");
		
		return buffer.toString();
	}

	public static String toJSONMap(Map<?, ?> map) {
		return toJSONMap(map, false, false);
	}

}
