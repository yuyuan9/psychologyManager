package com.ht.table;

import org.apache.commons.lang3.StringUtils;

public class CreateUtils {
	
	public static String prefix="t";
	
	public static String getTableName(String prefix,Class clazz) {
		String name = clazz.getSimpleName();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < name.length(); i++) {
			if (Character.isUpperCase(name.charAt(i))) {
				sb.append("_" + StringUtils.lowerCase(name.charAt(i) + ""));
			} else {
				sb.append(name.charAt(i));
			}
		}
		return prefix + sb.toString();
	}

}
