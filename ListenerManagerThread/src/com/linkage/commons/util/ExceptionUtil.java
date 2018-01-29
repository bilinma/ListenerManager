package com.linkage.commons.util;
/**
 * BSS�쳣����������
 * @author zhaoxin
 *
 */
public class ExceptionUtil {
	/**
	 * �����쳣��ջ��Ϣ
	 * @param message
	 * @param cause
	 * @return
	 */
	public static String buildMessage(String message, Throwable cause) {
		if (cause != null) {
			StringBuilder buf = new StringBuilder();
			if (message != null) {
				buf.append(message).append("; ");
			}
			buf.append("nested exception is ").append(cause);
			return buf.toString();
		}
		else {
			return message;
		}
	}
}