/*
 * @(#)CommonUtil.java
 * ���ഴ����2009��8��29�� 
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
 * �������ȵĹ���������. 
 * @version 0.5
 * @author ������ 
 * @author κ��ʤ 
 */

public class CommonUtil {

	private static final Log log = Log.getLog(CommonUtil.class);
	//�������͵�����
	private static final int DATE_TYPE_ITEM = 4;
	//ʱ�����͵�����
	private static final int TIME_TYPE_ITEM = 16;

	/**
	 * ����CLOBת��STRING����
	 * @param clob CLOB�����ݶ���
	 * @return CLOB��ȡת������ַ�������
	 * @throws SQLException
	 * @throws IOException
	 */
	public static String clobToString(oracle.sql.CLOB clob) throws SQLException, IOException {
		String reString = "";
		Reader is = clob.getCharacterStream();// �õ���
		BufferedReader br = new BufferedReader(is);
		String s = br.readLine();
		StringBuffer sb = new StringBuffer();
		//ִ��ѭ�����ַ���ȫ��ȡ����ֵ��StringBuffer��StringBufferת��STRING
		while (s != null) {
			sb.append(s);
			s = br.readLine();
		}
		reString = sb.toString();
		return reString;
	}

	/**
	 * ��ȡָ������(���ֽڳ��Ȼ�ȡ)���ַ���
	 * @param src Դ�ַ���
	 * @param length ����
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
	 * ��ȡ�쳣��Ϣ����
	 * @param e �쳣����
	 * @param length ָ������
	 * @return �����쳣��Ϣ����
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
	 * ��ȡ�쳣��Ϣ����
	 * @param e �쳣����
	 * @return �����쳣��Ϣ����
	 */
	public static String getExceptionString(Exception e) {
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(os);
		e.printStackTrace(ps);
		String msg = os.toString();
		return msg;
	}

	/**
	 * ���쳣�����װ��ǰ̨չʾ���쳣DTO����
	 * @param e �쳣����
	 * @return �����쳣��ϢDto
	 */
	/*public static ExceptionDto getExceptionDto(Exception e) {
		ExceptionDto dto = new ExceptionDto();
		dto.setFlag(true);
		if (e instanceof UncategorizedSQLException) {
			dto.setExceptionCode(-9999);
			dto.setExceptionMsg("�ײ������쳣!");
			dto.setExceptionStack(getExceptionString(e));
		} else if (e instanceof BssRuntimeException) {
			BssRuntimeException be = (BssRuntimeException) e;
			dto.setExceptionCode(be.getResult().getCode());
			dto.setExceptionMsg(be.getMessage());
			dto.setExceptionStack(getExceptionString(be));
		} else {
			dto.setExceptionCode(-9999);
			dto.setExceptionMsg("δ֪�쳣" + e.getMessage() == null ? "" : e.getMessage());
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
	 * ���ڴ���ǰ̨ͨ��GET�ύ������������⣬��һ������ת��
	 * @param originalString  Դ�ַ���
	 * @return ����ת������ַ���
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
