package com.linkage.commons.util;
/**
 * BSS异常处理工具类
 * @author zhaoxin
 *
 */
public class ExceptionUtil {
	/**
	 * 创建异常堆栈信息
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
