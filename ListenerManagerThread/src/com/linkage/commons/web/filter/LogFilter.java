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
 * ��־��������ʵ����־���ء�Ա�����뵱ǰ������̰߳�
 * @author zhaoxin
 *
 */
public class LogFilter extends OnceRequestFilter {
	private static Log log = Log.getLog(LogFilter.class);
	
	//filter�Ĳ�������

	private static final String FILTER_PARAM_LOG_MODE = "log_mode";
	private static final String FILTER_PARAM_VALUE_LOG_MODEL_DEVELOP="develop";
	private static final String FILTER_PARAM_VALUE_LOG_MODEL_RUNTIME="runtime";
	
	//cookie����־�����ĵ�ֵ

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
	 * ���ܣ���־��¼���������뵱ǰ�����̰߳���־�����Ļ���

	 * �߼���

	 * 1)���web.xml���ô˹�������log_mode�ǿ���ģʽ(develop),����ԭ�е�Logback.xml�����ļ���¼��־
	 * 2)���web.xml���ô˹�������log_mode������ģʽ(runtime),���տͻ��˴����cookie����־�����������Ƿ��¼��־
	 *   ��ʱ����Ҫlogback.xml�������ļ��е���־����Ϊdebug����־��¼��������filter������

	 *   
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest req, HttpServletResponse resp, FilterChain filterChain)
			throws ServletException, IOException {
		String uri = req.getRequestURI();
		if (StringUtil.isEmpty(uri) || uri.endsWith(".js") || uri.endsWith(".css") || uri.endsWith(".jpg")
				|| uri.endsWith(".png") || uri.endsWith(".gif") || uri.endsWith(".flash")) {
			//����ͼƬ������־�����޹ص����ݣ�do nothing here���������
		} else {
			if (_log_model.equals(FILTER_PARAM_VALUE_LOG_MODEL_RUNTIME)) {
				//DEBUG FLAG and staff id GET FROM COOKIE or session
				//ֻҪcookie��û�п��ı�ʶ,Ĭ�϶��ǹص�
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
				//develop����ģʽ�����ж�cookie�е�״̬,Ĭ�Ͽ��ţ�do nothing here
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
