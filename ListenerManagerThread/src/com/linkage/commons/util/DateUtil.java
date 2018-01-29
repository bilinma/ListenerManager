package com.linkage.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

/**
 * ���ڴ�������
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
	 * ��Dateת��Ϊ pattern ��ʽ���ַ�������ʽΪ��
	 *  yyyyMMddHHmmss
	 *	yyyy-MM-dd HH:mm:ss
	 *	yyyy-MM-dd
	 *	MM/dd/yyyy HH:mm:ss a
	 *	yyyy-MM-dd HH:mm:ss a
	 *	yyyy-MM-dd'T'HH:mm:ss'Z'
	 *	yyyy-MM-dd'T'HH:mm:ssZ
	 *	yyyy-MM-dd'T'HH:mm:ssz
	 * @param date ����
	 * @param pattern ���ڸ�ʽ
	 * @return String --��ʽ���������ַ���
	 * @see java.util.Date
	 */
	public static String getFormatTimeString(Date date, String pattern) {
		SimpleDateFormat sDateFormat = getDateFormat(pattern);
		return sDateFormat.format(date);
	}

	/**
	 * ��Dateת��ΪĬ�ϵ�YYYYMMDDHHMMSS ��ʽ���ַ���
	 * @param date
	 * @return
	 */
	public static String getDefaultFormateTimeString(Date date) {
		return getFormatTimeString(date, DATE_FORMATE_STRING_DEFAULT);
	}
	/**
	 * ����patternȡ�õ�date formate
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
	 * ����ʽ�������ַ���ת��ΪDate����
	 * 
	 * @param date �ַ���
	 * @param pattern ��ʽ���£�
	 * 	yyyyMMddHHmmss
	 *	yyyy-MM-dd HH:mm:ss
	 *	yyyy-MM-dd
	 *	MM/dd/yyyy HH:mm:ss a
	 *	yyyy-MM-dd HH:mm:ss a
	 *	yyyy-MM-dd'T'HH:mm:ss'Z'
	 *	yyyy-MM-dd'T'HH:mm:ssZ
	 *	yyyy-MM-dd'T'HH:mm:ssz
	 * @return ����Date����
	 * @throws ParseException
	 * @see java.util.Date
	 */
	public static Date getDateFromString(String date, String pattern) throws ParseException {
		SimpleDateFormat sDateFormat = getDateFormat(pattern);
		return sDateFormat.parse(date);
	}
	/**
	 * �������ַ���ת����Ĭ�ϸ�ʽYYYYMMDDHHMMSS��Date����
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Date getDefaultDateFromString(String date) throws ParseException {
		return getDateFromString(date, DATE_FORMATE_STRING_DEFAULT);
	}
	/**
	 * ȡ��ǰʱ��,��ʽΪYYYYMMDDHHMMSS�������ַ���
	 * 
	 * @return ��ǰ�����ַ���
	 * @throws ParseException
	 * @see java.util.Date
	 */
	public static String getNowDefault() {
		return getNow(DATE_FORMATE_STRING_DEFAULT);
	}
	/**
	 * ����pattern��ʽȡ��ǰ�����ַ���
	 * @param pattern	�����ַ�����ʽ
	 * @return			��ʽ����ĵ�ǰ�����ַ���
	 */
	public static String getNow(String pattern) {
		return getFormatTimeString(new Date(), pattern);
	}

	/**
	 * ȡ��ǰʱ��,��ʽΪYYYYMMDD
	 * 
	 * @return ��ǰ�����ַ���
	 * @throws ParseException
	 * @see java.util.Date
	 */
	public static String getNowII() {
		return getFormatTimeString(new Date(), DATE_FORMATE_STRING_H);
	}

	/**
	 * ������pattern��ʽ�������ַ���ת��Ϊȡʱ��ĺ����� since 1976
	 * 
	 * @return ʱ�������
	 * @throws ParseException
	 * @see java.util.Date
	 */
	public static long dateString2Long(String str, String pattern) throws ParseException {
		return getDateFromString(str, pattern).getTime();
	}

	/**
	 * ��since1976�ĺ�����ת��Ĭ�ϸ�ʽyyyyMMddHHmmss��String�����ַ���
	 * @param time
	 * @return
	 */
	public static String longToDateStringDefault(long time) {
		return getFormatTimeString(new Date(time), DATE_FORMATE_STRING_DEFAULT);
	}
	/**
	 * ��ʱ��ĺ����� since 1976ת��Ϊ����pattern��ʽ�������ַ���
	 * 
	 * @return �����ַ���
	 * @see java.util.Date
	 */
	public static String longToDateString(long time, String pattern) {
		return getFormatTimeString(new Date(time), pattern);
	}
	/**
	 * ��Date����ת��since 1976�ĺ�����
	 * @param date
	 * @return	since1976�ĺ�����
	 */
	public static long date2Long(Date date) {
		return date.getTime();
	}
	/**
	 * ��since1976������ת��Date����
	 * @param time
	 * @return
	 */
	public static Date longToDate(long time){
		return new Date(time);
	}
	/**
	 * �Զ��������ָ�ʽ�������ַ���ת��Ϊdate����
	 * A��ʽ	:	yyyy-MM-dd HH:mm:ss
	 * B��ʽ	:	yyyy-MM-dd
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
	 * ��ȡ��ǰʱ���ǰ�󼸸��µ�ʱ��,��ʽΪ��yyyy-MM-dd HH24:MI:SS�ַ���
	 * ����ȡǰ6���µ�ʱ�䴫��-6,��6���µ�ʱ�䴫��6
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
		dateStr.append(year);//��
		dateStr.append("-");
		dateStr.append(month);//��
		dateStr.append("-");
		dateStr.append(day);//��
		dateStr.append(" ");
		dateStr.append(hour);//Сʱ
		dateStr.append(":");
		dateStr.append(mi);//��
		dateStr.append(":");
		dateStr.append(ss);//��
		return dateStr.toString();
	}

}
