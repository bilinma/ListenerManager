package com.linkage.commons.log;

/**
 * 对各种日志框架：slf4j, log4j的包装类
 * @author zhaoxin
 *
 */
public class BssLoggerWrapper {
	// 这里可以替换成Log4jLogger
	private org.slf4j.Logger _logger;
	private static final String defaultLogName = "com.linkage.bss.commons.util.Log";
	private String name;

	public BssLoggerWrapper(String name) {
		// 这里可以替换成Log4jLogger
		_logger = org.slf4j.LoggerFactory.getLogger(name);
		this.name = name;
	}

	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public void debug(String message, Object... args) {
		_logger.debug(message, args);
	}

	public void info(String message, Object... args) {
		_logger.info(message, args);
	}

	public void warn(String message, Object... args) {
		_logger.warn(message, args);
	}

	public void trace(String message, Object... args) {
		_logger.trace(message, args);
	}

	public void error(String message, Object... args) {
		_logger.error(message, args);
	}

	public void error(String message, Throwable e) {
		_logger.error(message, e);
	}

	public boolean isDebugEnabled() {
		if (_logger == null)
			return false;
		return _logger.isDebugEnabled();
	}

	public boolean isErrorEnabled() {
		if (_logger == null)
			return false;
		return _logger.isErrorEnabled();
	}

	public boolean isInfoEnabled() {
		if (_logger == null)
			return false;
		return _logger.isInfoEnabled();
	}

	public boolean isTraceEnabled() {
		if (_logger == null)
			return false;
		return _logger.isTraceEnabled();
	}

	public boolean isWarnEnabled() {
		if (_logger == null)
			return false;
		return _logger.isWarnEnabled();
	}
}
