package com.linkage.commons.log;

/**
 * 线程绑定当前的日志上下文信息
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
