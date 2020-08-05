/*
 *  Copyright (c) 2014 The CCP project authors. All Rights Reserved.
 *
 *  Use of this source code is governed by a Beijing Speedtong Information Technology Co.,Ltd license
 *  that can be found in the LICENSE file in the root of the web site.
 *
 *   http://www.yuntongxun.com
 *
 *  An additional intellectual property rights grant can be found
 *  in the file PATENTS.  All contributing project authors may
 *  be found in the AUTHORS file in the root of the source tree.
 */
package com.ht.commons.support.sms.sdk.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.StringUtils;

public class DateUtil
{
  public static final int DEFAULT = 0;
  public static final int YM = 1;
  public static final int YMR_SLASH = 11;
  public static final int NO_SLASH = 2;
  public static final int YM_NO_SLASH = 3;
  public static final int DATE_TIME = 4;
  public static final int DATE_TIME_NO_SLASH = 5;
  public static final int DATE_HM = 6;
  public static final int TIME = 7;
  public static final int HM = 8;
  public static final int LONG_TIME = 9;
  public static final int SHORT_TIME = 10;
  public static final int DATE_TIME_LINE = 12;

  public static String dateToStr(Date date, String pattern)
  {
    if ((date == null) || (date.equals("")))
      return null;
    SimpleDateFormat formatter = new SimpleDateFormat(pattern);
    return formatter.format(date);
  }

  public static String dateToStr(Date date) {
    return dateToStr(date, "yyyy/MM/dd");
  }

  public static String dateToStr(Date date, int type) {
    switch (type)
    {
    case 0:
      return dateToStr(date);
    case 1:
      return dateToStr(date, "yyyy/MM");
    case 2:
      return dateToStr(date, "yyyyMMdd");
    case 11:
      return dateToStr(date, "yyyy-MM-dd");
    case 3:
      return dateToStr(date, "yyyyMM");
    case 4:
      return dateToStr(date, "yyyy/MM/dd HH:mm:ss");
    case 5:
      return dateToStr(date, "yyyyMMddHHmmss");
    case 6:
      return dateToStr(date, "yyyy/MM/dd HH:mm");
    case 7:
      return dateToStr(date, "HH:mm:ss");
    case 8:
      return dateToStr(date, "HH:mm");
    case 9:
      return dateToStr(date, "HHmmss");
    case 10:
      return dateToStr(date, "HHmm");
    case 12:
      return dateToStr(date, "yyyy-MM-dd HH:mm:ss");
    case 13:
        return dateToStr(date, "yyyy年MM月dd日");
    case 14:
        return dateToStr(date, "EEE MMM dd HH:mm:ss z yyyy");
    case 15:
        return dateToStr(date, "yyyy");
    }
    throw new IllegalArgumentException("Type undefined : " + type);
  }
  public static Date StrTodate(String date, String pattern) throws Exception
  {
	  SimpleDateFormat formatter =null;
    if (!StringUtils.isBlank(date)){
    	if(StringUtils.equals(pattern, "EEE MMM dd HH:mm:ss z yyyy")){
    		formatter = new SimpleDateFormat(pattern,Locale.US);
    	}else{
    		formatter = new SimpleDateFormat(pattern);
    	}
        return formatter.parse(date);
    }else{
    	return null;
    }
  }
  //给日期加一天
  public static Date addDay(Date date){
	  Calendar c = Calendar.getInstance();  
      c.setTime(date);  
      c.add(Calendar.DAY_OF_MONTH, 1);
      return c.getTime();
  }
//给日期加减a天
  public static Date addDay(Date date,int a){
	  Calendar c = Calendar.getInstance();  
      c.setTime(date);  
      c.add(Calendar.DAY_OF_MONTH, a);
      return c.getTime();
  }
//给日期加减a月
  public static Date addMonth(Date date,int a){
	  Calendar c = Calendar.getInstance();  
      c.setTime(date);  
      c.add(Calendar.MONTH, a);
      return c.getTime();
  }
//给日期加减a年
  public static Date addYear(Date date,int a){
	  Calendar c = Calendar.getInstance();  
      c.setTime(date);  
      c.add(Calendar.YEAR, a);
      return c.getTime();
  }
  public static String getYear() {
		return dateToStr(new Date(),15);
  }
  public static Integer year() {
		return Integer.valueOf(getYear());
  }
  public static String getBeforeDay(int day, String suffixhour) {
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR, -day);
		return formatDate(c.getTime(), "yyyy-MM-dd") + " " + suffixhour;
	}
  /**
	 * 格式化日期
	 */
	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
		String dateStr = simpleDateFormat.format(date);
		return dateStr;
	}
	
	public static int getDiffYearByDate(Date startTime,Date endTime){
		return getDiffYear(formatDate(startTime,"yyyy-MM-dd"),formatDate(endTime,"yyyy-MM-dd"));
	}
	
	public static int getDiffYear(String startTime, String endTime) {
		DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
		try {
			long aa = 0;
			int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(startTime).getTime()) / (1000 * 60 * 60 * 24))
					/ 365);
			return years;
		} catch (Exception e) {
			// 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
			return 0;
		}
	}
	/**
	 * <li>功能描述：时间相减得到天数
	 * 
	 * @param beginDateStr
	 * @param endDateStr
	 * @return long
	 * @author Administrator
	 */
	public static long getDaySub(String beginDateStr, String endDateStr) {
		long day = 0;
		java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");
		java.util.Date beginDate = null;
		java.util.Date endDate = null;

		try {
			beginDate = format.parse(beginDateStr);
			endDate = format.parse(endDateStr);
			day = (beginDate.getTime() - endDate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (ParseException e) {
			e.printStackTrace();
			
			day=-1;
		}
		// System.out.println("相隔的天数="+day);

		return day;
	}
	/**
	 * @Title: compareDate
	 * @Description: TODO(日期比较，如果s>=e 返回true 否则返回false)
	 * @param s
	 * @param e
	 * @return boolean
	 * @throws Exception 
	 * @throws @author
	 *             luguosui
	 */
	public static boolean compareDate(String s, String e) throws Exception {
		if (StrTodate(s,"yyyy-MM-dd") == null || StrTodate(e,"yyyy-MM-dd") == null) {
			return false;
		}
		return StrTodate(s,"yyyy-MM-dd").getTime() >= StrTodate(e,"yyyy-MM-dd").getTime();
	}
}