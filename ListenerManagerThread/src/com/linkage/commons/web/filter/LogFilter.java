package com.linkage.commons.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.linkage.commons.log.LogContext;
import com.linkage.commons.log.LogContextHolder;
import com.linkage.commons.util.Log;
import com.linkage.commons.util.StringUtil;
import com.linkage.commons.web.WebUtils;

/**
 * 日志过滤器，实现日志开关、员工号与当前请求的线程绑定
 * @author zhaoxin
 *
 */
public class LogFilter extends OnceRequestFilter {
	private static Log log = Log.getLog(LogFilter.class);
	
	//filter的参数配置

	private static final String FILTER_PARAM_LOG_MODE = "log_mode";
	private static final String FILTER_PARAM_VALUE_LOG_MODEL_DEVELOP="develop";
	private static final String FILTER_PARAM_VALUE_LOG_MODEL_RUNTIME="runtime";
	
	//cookie的日志上下文的值

	private static final String COOKIE_NAME_STAFF = "bssStaffNumber";
	private static final String COOKIE_NAME_LOG_SWITCH = "bssLogSwith";
	private static final String COOKIE_VALUE_LOG_OPEN = "open";
	private static final String COOKIE_VALUE_LOG_CLOSE = "close";
	

	
	private String _log_model = FILTER_PARAM_VALUE_LOG_MODEL_DEVELOP;

	@Override
	public void doInit(FilterConfig filterConfig) throws ServletException {
		if (filterConfig != null) {
			String log_mode = filterConfig.getInitParameter(FILTER_PARAM_LOG_MODE);
			if (!StringUtil.isEmpty(log_mode)) {
				_log_model = log_mode;
			}
		}
		log.debug("_debug:{}", _log_model);

	}

	/**
	 * 功能：日志记录过滤器，与当前请求线程绑定日志上下文环境

	 * 逻辑：

	 * 1)如果web.xml配置此过滤器的log_mode是开发模式(develop),则按照原有的Logback.xml配置文件记录日志
	 * 2)如果web.xml配置此过滤器的log_mode是生产模式(runtime),则按照客户端传入的cookie的日志开关来决定是否记录日志
	 *   此时，需要logback.xml的配置文件中的日志级别为debug。日志记录与否交由这个filter来决定

	 *   
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain)
			throws ServletException, IOException {
		String uri = req.getRequestURI();
		if (StringUtil.isEmpty(uri) || uri.endsWith(".js") || uri.endsWith(".css") || uri.endsWith(".jpg")
				|| uri.endsWith(".png") || uri.endsWith(".gif") || uri.endsWith(".flash")) {
			//对于图片等与日志开关无关的内容，do nothing here，提高性能
		} else {
			if (_log_model.equals(FILTER_PARAM_VALUE_LOG_MODEL_RUNTIME)) {
				//DEBUG FLAG and staff id GET FROM COOKIE or session
				//只要cookie中没有开的标识,默认都是关的
				String staff = WebUtils.getCookieValue(req, COOKIE_NAME_STAFF);
				String debugSwitch = WebUtils.getCookieValue(req, COOKIE_NAME_LOG_SWITCH);
				if (!StringUtil.isEmpty(debugSwitch) && debugSwitch.equalsIgnoreCase(COOKIE_VALUE_LOG_OPEN)) {
					LogContext logContext = new LogContext(staff, true);
					LogContextHolder.setLogContext(logContext);
				} else {
					LogContext logContext = new LogContext(staff, false);
					LogContextHolder.setLogContext(logContext);
				}
			} else {
				//develop开发模式，不判断cookie中的状态,默认开着，do nothing here
			}
		}
		try {
			filterChain.doFilter(req, resp);
		} finally {
			//clear current request thread log context
			LogContextHolder.clearLogContext();
		}
	}

}
