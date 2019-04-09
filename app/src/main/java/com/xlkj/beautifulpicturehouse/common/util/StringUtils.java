package com.xlkj.beautifulpicturehouse.common.util;


import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class StringUtils {
	/** 判断字符串是否有值，如果为null或者是空字符串或者只有空格或者为"null"字符串，则返回true，否则则返回false */
	public static boolean isEmpty(String value) {
		if (value != null && !"".equalsIgnoreCase(value.trim())
				&& !"null".equalsIgnoreCase(value.trim())) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 生成32位编码
	 *
	 * @return string
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString().trim().replaceAll("-", "");
		return uuid;
	}

	/**
	 * 方法用途: 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序），并且生成url参数串<br>
	 * 实现步骤: <br>
	 *
	 * @param paraMap   要排序的Map对象
	 * @param urlEncode 是否需要URLENCODE
	 * @return
	 */
	public static String formatUrlMap(Map<String, String> paraMap, boolean urlEncode) {
		String buff = "";
		Map<String, String> tmpMap = paraMap;
		try {
			List<Map.Entry<String, String>> infoIds = new ArrayList<Map.Entry<String, String>>(tmpMap.entrySet());
			// 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
			Collections.sort(infoIds, new Comparator<Map.Entry<String, String>>() {

				@Override
				public int compare(Map.Entry<String, String> o1, Map.Entry<String, String> o2) {
					return (o1.getKey()).toString().compareTo(o2.getKey());
				}
			});
			// 构造URL 键值对的格式
			StringBuilder buf = new StringBuilder();
			for (Map.Entry<String, String> item : infoIds) {
				if (StringUtils.isNotNullStr(item.getKey())&&StringUtils.isNotNullStr(item.getValue())) {
					String key = item.getKey();
					String val = item.getValue();
					if (urlEncode) {
						val = URLEncoder.encode(val, "utf-8");
					}
					buf.append(key + "=" + val);
					buf.append("&");
				}

			}
			buff = buf.toString();
			if (buff.isEmpty() == false) {
				buff = buff.substring(0, buff.length() - 1);
			}
		} catch (Exception e) {
			return null;
		}
		return buff;
	}

	/**
	 * @return 字符串是否不为空
	 */
	public static Boolean isNotNullStr(String str) {
		try {
			Boolean result = true;

			if (str == null || str.length() <= 0 || str.equals(null)
					|| str.equals("null")) {
				result = false;
			}

			return result;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @return 字符串是否为空
	 */
	public static Boolean isNullStr(String str) {
		try {
			Boolean result = false;

			if (str == null || str.length() <= 0 || str.equals(null)
					|| str.equals("null")) {
				result = true;
			}

			return result;
		} catch (Exception e) {
			return false;
		}
	}



}
