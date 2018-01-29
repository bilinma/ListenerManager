/*
 * @(#)CommonUtil.java
 * 本类创建于2009年8月29日 
 */
package com.linkage.commons.util;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** 
 * 订单调度的公共工具类. 
 * @version 0.5
 * @author 景伯文 
 * @author 魏铁胜 
 */

public class CommonUtil {

	private static final Log log = Log.getLog(CommonUtil.class);
	//日期类型的属性
	private static final int DATE_TYPE_ITEM = 4;
	//时间类型的属性
	private static final int TIME_TYPE_ITEM = 16;

	/**
	 * 将字CLOB转成STRING类型
	 * @param clob CLOB的数据对象
	 * @return CLOB读取转化后的字符串对象
	 * @throws SQLException
	 * @throws IOException
	 */
	public static String clobToString(oracle.sql.CLOB clob) throws SQLException, IOException {
		String reString = "";
		Reader is = clob.getCharacterStream();// 得到流
		BufferedReader br = new BufferedReader(is);
		String s = br.readLine();
		StringBuffer sb = new StringBuffer();
		//执行循环将字符串全部取出付值给StringBuffer由StringBuffer转成STRING
		while (s != null) {
			sb.append(s);
			s = br.readLine();
		}
		reString = sb.toString();
		return reString;
	}

	/**
	 * 获取指定长度(按字节长度获取)的字符串
	 * @param src 源字符串
	 * @param length 长度
	 * @return
	 */
	public static String getSubStr(String src, int length) {
		if (StringUtil.isEmpty(src)) {
			return null;
		}
		byte[] b = src.getBytes();
		if (b.length > length) {
			byte[] s = new byte[length];
			for (int i = 0; i < length; i++) {
				s[i] = b[i];
			}
			return new String(s);
		} else {
			return src;
		}
	}

	/**
	 * 获取异常信息内容
	 * @param e 异常对象
	 * @param length 指定长度
	 * @return 返回异常信息内容
	 */
	public static String getExceptionString(Exception e, int length) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		e.printStackTrace(ps);
		String msg = os.toString();
		if (msg.length() > length) {
			msg = getSubStr(msg, length);
		}
		return msg;
	}

	/**
	 * 获取异常信息内容
	 * @param e 异常对象
	 * @return 返回异常信息内容
	 */
	public static String getExceptionString(Exception e) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		e.printStackTrace(ps);
		String msg = os.toString();
		return msg;
	}

	/**
	 * 将异常对象封装成前台展示的异常DTO对象
	 * @param e 异常对象
	 * @return 返回异常信息Dto
	 */
	/*public static ExceptionDto getExceptionDto(Exception e) {
		ExceptionDto dto = new ExceptionDto();
		dto.setFlag(true);
		if (e instanceof UncategorizedSQLException) {
			dto.setExceptionCode(-9999);
			dto.setExceptionMsg("底层数据异常!");
			dto.setExceptionStack(getExceptionString(e));
		} else if (e instanceof BssRuntimeException) {
			BssRuntimeException be = (BssRuntimeException) e;
			dto.setExceptionCode(be.getResult().getCode());
			dto.setExceptionMsg(be.getMessage());
			dto.setExceptionStack(getExceptionString(be));
		} else {
			dto.setExceptionCode(-9999);
			dto.setExceptionMsg("未知异常" + e.getMessage() == null ? "" : e.getMessage());
			dto.setExceptionStack(getExceptionString(e));
		}
		return dto;
	}*/

	public static String formatDate(Date date, String formatStr) {
		SimpleDateFormat dateformat = new SimpleDateFormat(formatStr);
		String dateStr = dateformat.format(date);
		return dateStr;
	}

	/**
	 * 用于处理前台通过GET提交中文乱码的问题，做一个编码转换
	 * @param originalString  源字符串
	 * @return 编码转换后的字符串
	 */
	public static String changeEncode(String originalString) {
		try {
			return new String(originalString.getBytes("ISO-8859-1"), "UTF-8");
		} catch (Exception e) {
			return null;
		}
	}

	public static String formatString(String originalString) {
		if (originalString == null) {
			return "";
		}
		return originalString;
	}

	
}
