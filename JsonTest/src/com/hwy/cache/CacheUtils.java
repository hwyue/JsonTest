package com.hwy.cache;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

/**
 * 缓存cacheMap
 * 
 * @author hwyue
 * 
 */
public class CacheUtils {
	private static Map<String, Object> cacheMap = new HashMap<String, Object>();
	//private static final ResourceBundle resource = ResourceBundle.getBundle("resource", new Locale("zh"));
	private static final ResourceBundle resource = ResourceBundle.getBundle("resource", new Locale("en"));

	public static void loadCache() {
		Map<String, Object> map = new HashMap<String, Object>();
		Set<String> set = resource.keySet();
		Iterator<String> iter = set.iterator();
		while (iter.hasNext()) {
			String key = iter.next();
			String val = (String) resource.getObject(key);
			//System.out.println(key + ":" + val);
			map.put(key, val);
		}
		cacheMap = map;
	}

	public static Map<String, Object> getCacheMap() {
		return cacheMap;
	}

	public static void setCacheMap(Map<String, Object> cacheMap) {
		CacheUtils.cacheMap = cacheMap;
	}

	public static String getValueByKey(String key) {
		if (key != null && !"".equals(key)) {
			return (String) cacheMap.get(key);
		}
		return null;
	}
}
