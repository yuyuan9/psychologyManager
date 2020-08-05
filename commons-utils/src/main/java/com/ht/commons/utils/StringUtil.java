package com.ht.commons.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串相关方法
 *
 */
public class StringUtil {
	
	/*public static boolean isPhone(String phone) {
	    String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
	    if (phone.length() != 11) {
	        return false;
	    } else {
	        Pattern p = Pattern.compile(regex);
	        Matcher m = p.matcher(phone);
	        boolean isMatch = m.matches();
	        return isMatch;
	    }
	}*/
	
	public static String checkPhoneValue(String phone) {
		if(ValidataUtil.isPhone(phone)) {
			return phone;
		}else {
			return StringUtils.EMPTY;
		}
	}
	
	public static String[] getStringArray(String value) {
		if(StringUtils.contains(value, ",")) {
			return StringUtils.split(value, ",");
		}else if(StringUtils.contains(value, ";")) {
			return StringUtils.split(value,";");
		}else {
			return new String[] {value};
		}
	}
	
	public static String[] stringArrayComma(String value) {
		if(StringUtils.contains(value, ",")) {
			return StringUtils.split(value, ",");
		}
		return new String[] {value};
	}
	
	public static String[] stringArraySemicolon(String value) {
		if(StringUtils.contains(value, ";")) {
			return StringUtils.split(value, ";");
		}
		return new String[] {value};
	}

	public static String[] strListSolr(String val) {
		if (StringUtils.isNotBlank(val)) {
			// String[] kws = StringUtils.split(StringUtils.upperCase(val),
			// "OR");
			String[] kws = StringUtils.split(val, "OR");
			List<String> query = new ArrayList<String>();
			for (int i = 0; i < kws.length; i++) {
				if (StringUtils.isNotBlank(kws[i])) {
					query.add(StringUtils.trim(kws[i]));
				}
			}
			return (String[]) query.toArray(new String[query.size()]);
		}
		return null;
	}

	/*
	 * 替换
	 */
	public static String replaceStr(String value, String[] kw) {
		for (int i = 0; i < kw.length; i++) {
			value = StringUtils.replace(value, kw[i], "<font color='red'>" + kw[i] + "</font>");
		}
		return value;
	}

	/**
	 * 将以逗号分隔的字符串转换成字符串数组
	 * 
	 * @param valStr
	 * @return String[]
	 */
	public static String[] StrList(String valStr) {
		int i = 0;
		String TempStr = valStr;
		String[] returnStr = new String[valStr.length() + 1 - TempStr.replace(",", "").length()];
		valStr = valStr + ",";
		while (valStr.indexOf(',') > 0) {
			returnStr[i] = valStr.substring(0, valStr.indexOf(','));
			valStr = valStr.substring(valStr.indexOf(',') + 1, valStr.length());

			i++;
		}
		return returnStr;
	}

	public static String getPhoneForUserName(String phone) {
		if (StringUtils.isBlank(phone)) {
			return StringUtils.EMPTY;
		}
		return phone.substring(0, 3) + "*****" + phone.substring(8);
	}

	public static String filterHeadImg(String headImg) {
		if (!StringUtils.isBlank(headImg)) {
			if (headImg.indexOf("/") == 0) {
				return headImg;
			} else {
				return "/" + headImg;
			}
		}
		return headImg;
	}

	public static String rename(String filePath, String fileName) {
		System.out.println("filePath->" + filePath);
		System.out.println("fileName->" + fileName);
		if (!StringUtils.isBlank(filePath)) {
			int last = filePath.lastIndexOf("/");
			int last0 = filePath.lastIndexOf(".");
			return filePath.substring(0, last + 1) + fileName + filePath.substring(last0);
		} else {
			return StringUtils.EMPTY;
		}
	}

	public static String getFileType(String filePath) {

		if (!StringUtils.isBlank(filePath)) {
			int last0 = filePath.lastIndexOf(".");
			return filePath.substring(last0);
		}

		return StringUtils.EMPTY;
	}

	/*
	 * 
	 * 从字符串中截取日期并转换
	 * 
	 */
	public static int stringToDate(String str) throws Exception {
		int i = -1;
		if (str.contains("年")) {
			i = nian(str);
		} else if (str.contains("/")) {
			i = nonian(str);
		}
		return i;
	}

	/*
	 * 
	 * 提取字符串中第一个日期(以yyyy年MM月dd日格式或者yyyy年MM月dd格式)
	 * 
	 * -1 报错 0 大于 有效 1小于
	 */
	public static int nian(String str) {
		try {
			if (!StringUtils.isBlank(str)) {
				Date date = new Date();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
				Pattern pattern1 = Pattern.compile("[0-9]{4}[年][0-9]{1,2}[月][0-9]{1,2}|[日]");
				Matcher matcher1 = pattern1.matcher(str);
				String dateStr1 = null;
				if (matcher1.find()) {
					dateStr1 = matcher1.group(0);
				}
				String[] arr1 = null;
				if (null != dateStr1) {
					arr1 = dateStr1.split("(年|月)");
				}

				String s1 = "";
				for (int i = 0; i < arr1.length; i++) {
					if (i == arr1.length - 1) {
						s1 = s1 + arr1[i];
					} else {
						s1 = s1 + arr1[i] + "-";
					}
				}
				Date date2 = sdf.parse(s1);
				if (date2.getTime() > date.getTime()) {
					return 0;
				} else {
					return 1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
		return -1;

	}

	/*
	 * 
	 * 提取字符串中第一个日期(以yyyy/MM/dd格式)
	 * 
	 * 
	 */
	public static int nonian(String str) {
		try {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Pattern pattern1 = Pattern.compile("[0-9]{4}[/][0-9]{1,2}[/][0-9]{1,2}");
			Matcher matcher1 = pattern1.matcher(str);
			String dateStr1 = null;
			if (matcher1.find()) {
				dateStr1 = matcher1.group(0);
			}
			String[] arr1 = null;
			if (null != dateStr1) {
				arr1 = dateStr1.split("(/|/)");
			}
			String s1 = "";
			for (int i = 0; i < arr1.length; i++) {
				if (i == arr1.length - 1) {
					s1 = s1 + arr1[i];
				} else {
					s1 = s1 + arr1[i] + "-";
				}
			}
			Date date2 = sdf.parse(s1);
			if (date2.getTime() > date.getTime()) {
				return 0;
			} else {
				return 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}

	}

	/*
	 * 
	 * 搜索关键字变红
	 * 
	 * 
	 */
	public static String replacearry(String strSource, String[] strFrom) {
		String tempq = "";
		String temp = strSource;
		int j;
		for (j = 0; j < strFrom.length; j++) {
			tempq = replace(strSource, strFrom[j], "<font color='red'><b>" + strFrom[j] + "</b></font>");
			temp = tempq;
			strSource = temp;
		}
		return temp;

	}

	/*
	 * 
	 * 搜索关键字的具体方法
	 * 
	 */
	public static String replace(String strSource, String strFrom, String strTo) {
		if (strSource == null) {
			return null;
		}
		int i = 0;
		if ((i = strSource.indexOf(strFrom, i)) >= 0) {
			char[] cSrc = strSource.toCharArray();
			char[] cTo = strTo.toCharArray();
			int len = strFrom.length();
			StringBuffer buf = new StringBuffer(cSrc.length);
			buf.append(cSrc, 0, i).append(cTo);
			i += len;
			int j = i;
			while ((i = strSource.indexOf(strFrom, i)) > 0) {
				buf.append(cSrc, j, i - j).append(cTo);
				i += len;
				j = i;
			}
			buf.append(cSrc, j, cSrc.length - j);
			return buf.toString();
		}
		return strSource;
	}

	public static void main(String[] args) {
		System.out.println(nian("2017年12月21日前"));
		// System.out.println(getFileType("d:/a/b/sss.txt"));
	}
	
	public static Set<?> array2Set(Object[] array) {
		Set<Object> set = new TreeSet<Object>();
		for (Object id : array) {
			if(null != id){
				set.add(id);
			}
		}
		return set;
	}
	
}
