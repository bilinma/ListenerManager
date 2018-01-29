package com.linkage.commons.log;

/**
 * �̰߳󶨵�ǰ����־��������Ϣ
 * @author zhaoxin
 *
 */
public class LogContextHolder {
	//private static final ThreadLocal<LogContext> logContextHolder = new ThreadLocal<LogContext>();

	private static final ThreadLocal<LogContext> logContextHolder = new InheritableThreadLocal<LogContext>();
	public static void clearLogContext(){
		setLogContext(null);
		logContextHolder.remove();
	}

	public static void setLogContext(LogContext logContext) {
		logContextHolder.set(logContext);
	}

	public static LogContext getLogContext() {
		return logContextHolder.get();
	}
}
