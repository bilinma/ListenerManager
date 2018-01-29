package com.linkage.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * 日期处理工具类
 * 
 * @author zhaoxin
 * 
 */
public class DateUtil {
	private static Map<String, SimpleDateFormat> formats;
	private static final String DATE_FORMATE_STRING_DEFAULT = "yyyyMMddHHmmss";
	private static final String DATE_FORMATE_STRING_A = "yyyy-MM-dd HH:mm:ss";
	private static final String DATE_FORMATE_STRING_B = "yyyy-MM-dd";
	private static final String DATE_FORMATE_STRING_C = "MM/dd/yyyy HH:mm:ss a";
	private static final String DATE_FORMATE_STRING_D = "yyyy-MM-dd HH:mm:ss a";
	private static final String DATE_FORMATE_STRING_E = "yyyy-MM-dd'T'HH:mm:ss'Z'";
	private static final String DATE_FORMATE_STRING_F = "yyyy-MM-dd'T'HH:mm:ssZ";
	private static final String DATE_FORMATE_STRING_G = "yyyy-MM-dd'T'HH:mm:ssz";
	private static final String DATE_FORMATE_STRING_H = "yyyyMMdd";
	private static final String DATE_FORMATE_STRING_I = "yyyy-MM-dd HH:mm:ss.SSS";
	private static final String DATE_FORMATE_STRING_J = "yyyyMMddHHmmss.SSS";
	
	
	static {
		formats = new HashMap<String, SimpleDateFormat>();

		formats.put(DATE_FORMATE_STRING_DEFAULT, new SimpleDateFormat(DATE_FORMATE_STRING_DEFAULT));
		formats.put(DATE_FORMATE_STRING_A, new SimpleDateFormat(DATE_FORMATE_STRING_A));
		formats.put(DATE_FORMATE_STRING_B, new SimpleDateFormat(DATE_FORMATE_STRING_B));
		formats.put(DATE_FORMATE_STRING_C, new SimpleDateFormat(DATE_FORMATE_STRING_C));
		formats.put(DATE_FORMATE_STRING_D, new SimpleDateFormat(DATE_FORMATE_STRING_D));
		formats.put(DATE_FORMATE_STRING_E, new SimpleDateFormat(DATE_FORMATE_STRING_E));
		formats.put(DATE_FORMATE_STRING_F, new SimpleDateFormat(DATE_FORMATE_STRING_F));
		formats.put(DATE_FORMATE_STRING_G, new SimpleDateFormat(DATE_FORMATE_STRING_G));
		formats.put(DATE_FORMATE_STRING_H, new SimpleDateFormat(DATE_FORMATE_STRING_H));
		formats.put(DATE_FORMATE_STRING_I, new SimpleDateFormat(DATE_FORMATE_STRING_I));
		formats.put(DATE_FORMATE_STRING_J, new SimpleDateFormat(DATE_FORMATE_STRING_J));
	}

	/**
	 * 将Date转换为 pattern 格式的字符串，格式为：
	 *  yyyyMMddHHmmss
	 *	yyyy-MM-dd HH:mm:ss
	 *	yyyy-MM-dd
	 *	MM/dd/yyyy HH:mm:ss a
	 *	yyyy-MM-dd HH:mm:ss a
	 *	yyyy-MM-dd'T'HH:mm:ss'Z'
	 *	yyyy-MM-dd'T'HH:mm:ssZ
	 *	yyyy-MM-dd'T'HH:mm:ssz
	 * @param date 日期
	 * @param pattern 日期格式
	 * @return String --格式化的日期字符串
	 * @see java.util.Date
	 */
	public static String getFormatTimeString(Date date, String pattern) {
		SimpleDateFormat sDateFormat = getDateFormat(pattern);
		return sDateFormat.format(date);
	}

	/**
	 * 将Date转换为默认的YYYYMMDDHHMMSS 格式的字符串
	 * @param date
	 * @return
	 */
	public static String getDefaultFormateTimeString(Date date) {
		return getFormatTimeString(date, DATE_FORMATE_STRING_DEFAULT);
	}
	/**
	 * 根据pattern取得的date formate
	 * @param pattern
	 * @return
	 */
	public static SimpleDateFormat getDateFormat(String pattern) {
		SimpleDateFormat sDateFormat = formats.get(pattern);
		if (sDateFormat == null) {
			sDateFormat = new SimpleDateFormat(pattern);
			formats.put(pattern, sDateFormat);
		}
		return sDateFormat;
	}

	/**
	 * 将格式将日期字符串转换为Date对象
	 * 
	 * @param date 字符串
	 * @param pattern 格式如下：
	 * 	yyyyMMddHHmmss
	 *	yyyy-MM-dd HH:mm:ss
	 *	yyyy-MM-dd
	 *	MM/dd/yyyy HH:mm:ss a
	 *	yyyy-MM-dd HH:mm:ss a
	 *	yyyy-MM-dd'T'HH:mm:ss'Z'
	 *	yyyy-MM-dd'T'HH:mm:ssZ
	 *	yyyy-MM-dd'T'HH:mm:ssz
	 * @return 日期Date对象
	 * @throws ParseException
	 * @see java.util.Date
	 */
	public static Date getDateFromString(String date, String pattern) throws ParseException {
		SimpleDateFormat sDateFormat = getDateFormat(pattern);
		return sDateFormat.parse(date);
	}
	/**
	 * 将日期字符串转化成默认格式YYYYMMDDHHMMSS的Date对象
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getDefaultDateFromString(String date) throws ParseException {
		return getDateFromString(date, DATE_FORMATE_STRING_DEFAULT);
	}
	/**
	 * 取当前时间,格式为YYYYMMDDHHMMSS的日期字符串
	 * 
	 * @return 当前日期字符串
	 * @throws ParseException
	 * @see java.util.Date
	 */
	public static String getNowDefault() {
		return getNow(DATE_FORMATE_STRING_DEFAULT);
	}
	/**
	 * 按照pattern格式取当前日期字符串
	 * @param pattern	日期字符串格式
	 * @return			格式化后的当前日期字符串
	 */
	public static String getNow(String pattern) {
		return getFormatTimeString(new Date(), pattern);
	}

	/**
	 * 取当前时间,格式为YYYYMMDD
	 * 
	 * @return 当前日期字符串
	 * @throws ParseException
	 * @see java.util.Date
	 */
	public static String getNowII() {
		return getFormatTimeString(new Date(), DATE_FORMATE_STRING_H);
	}

	/**
	 * 将输入pattern格式的日期字符串转换为取时间的毫秒数 since 1976
	 * 
	 * @return 时间毫秒数
	 * @throws ParseException
	 * @see java.util.Date
	 */
	public static long dateString2Long(String str, String pattern) throws ParseException {
		return getDateFromString(str, pattern).getTime();
	}

	/**
	 * 把since1976的毫秒数转成默认格式yyyyMMddHHmmss的String日期字符串
	 * @param time
	 * @return
	 */
	public static String longToDateStringDefault(long time) {
		return getFormatTimeString(new Date(time), DATE_FORMATE_STRING_DEFAULT);
	}
	/**
	 * 将时间的毫秒数 since 1976转换为按照pattern格式的日期字符串
	 * 
	 * @return 日期字符串
	 * @see java.util.Date
	 */
	public static String longToDateString(long time, String pattern) {
		return getFormatTimeString(new Date(time), pattern);
	}
	/**
	 * 将Date对象转成since 1976的毫秒数
	 * @param date
	 * @return	since1976的毫秒数
	 */
	public static long date2Long(Date date) {
		return date.getTime();
	}
	/**
	 * 将since1976毫秒数转成Date对象
	 * @param time
	 * @return
	 */
	public static Date longToDate(long time){
		return new Date(time);
	}
	/**
	 * 自动适配两种格式的日期字符串转换为date对象
	 * A格式	:	yyyy-MM-dd HH:mm:ss
	 * B格式	:	yyyy-MM-dd
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getDateFromStringAdaptTwoPattern(String date) throws ParseException {
		try{
			return getDateFromString(date, DATE_FORMATE_STRING_A);
		}catch(ParseException e){
			return getDateFromString(date, DATE_FORMATE_STRING_B);
		}
	}
	
	   /**
	 * 获取当前时间的前后几个月的时间,格式为：yyyy-MM-dd HH24:MI:SS字符串
	 * 例：取前6个月的时间传入-6,后6个月的时间传入6
	 * @param count
	 * @return
	 */
	public static String getDIYDate(int count){
		StringBuffer dateStr = new StringBuffer();
		int year, month, day, hour, mi, ss;
		GregorianCalendar date = new GregorianCalendar();
		date.add(Calendar.MONTH, count+1);
		year = date.get(Calendar.YEAR);
		month = date.get(Calendar.MONTH);
		day = date.get(Calendar.DATE);
		hour = date.get(Calendar.HOUR_OF_DAY);
		mi = date.get(Calendar.MINUTE);
		ss = date.get(Calendar.SECOND);
		if(month == 0){
			month = 12;
			year --;
		}
		dateStr.append(year);//年
		dateStr.append("-");
		dateStr.append(month);//月
		dateStr.append("-");
		dateStr.append(day);//日
		dateStr.append(" ");
		dateStr.append(hour);//小时
		dateStr.append(":");
		dateStr.append(mi);//分
		dateStr.append(":");
		dateStr.append(ss);//秒
		return dateStr.toString();
	}

}
