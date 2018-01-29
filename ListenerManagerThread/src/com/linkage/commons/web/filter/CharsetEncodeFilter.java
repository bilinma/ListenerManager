package com.linkage.commons.web.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * �ַ���������
 * ʹ������,�����ӱ�ʾ�����е�������ַ���ת��ΪUTF-8
 *	<filter>
 *		<filter-name>encodingFilter</filter-name>
 *		<filter-class>com.linkage.bss.commons.web.filter.CharsetEncodeFilter</filter-class>
 *		<init-param>
 *			<param-name>encoding</param-name>
 *			<param-value>UTF-8</param-value>
 *		</init-param>
 *	</filter>
 *	<filter-mapping>
 *		<filter-name>encodingFilter</filter-name>
 *		<url-pattern>/*</url-pattern>
 *	</filter-mapping>
 * @author zhaoxin
 *
 */
public class CharsetEncodeFilter extends OnceRequestFilter {
	private static final String FILTER_PARAM_ENCODING = "encoding";
	private String encoding;
	private static final String DEFAULT_CHAR_SET = "UTF-8";

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		if (this.encoding != null && request.getCharacterEncoding() == null) {
			request.setCharacterEncoding(this.encoding);
			response.setCharacterEncoding(this.encoding);
		}
		filterChain.doFilter(request, response);
	}

	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doInit(FilterConfig cfg) throws ServletException {

		this.encoding = cfg.getInitParameter(FILTER_PARAM_ENCODING);
		if (this.encoding == null) {
			this.encoding = DEFAULT_CHAR_SET;
		}
	}
}