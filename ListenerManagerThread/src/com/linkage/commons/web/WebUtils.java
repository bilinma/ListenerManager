package com.linkage.commons.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * ���� J2EE WEB�Ĺ�����,��ȡ��cookie��,����session��

 * @author zhaoxin
 *
 */
public class WebUtils {

	/**
	 * ���ݴ����cookie nameȡ�ÿͻ��������cookie

	 * @param request http������

	 * @param name cookie����
	 * @return �����Ӧ���Ƶ�cookie������,����null
	 */
	public static Cookie getCookie(HttpServletRequest request, String name) {
		Cookie cookies[] = request.getCookies();
		if (cookies != null) {
			for (int i = 0; i < cookies.length; i++) {
				if (name.equals(cookies[i].getName())) {
					return cookies[i];
				}
			}
		}
		return null;
	}
	/**
	 * ����cookie���Ʒ���cookie��ֵ

	 * @param request http����
	 * @param name	cookie����
	 * @return	����cookieֵ,ֵ�������򷵻�null
	 */
	public static String getCookieValue(HttpServletRequest request, String name){
		Cookie cookie = getCookie(request,name);
		if(cookie==null)
			return null;
		return cookie.getValue();
	}

	/**
	 * ��http��Ӧ������cookie���ظ��ͻ���
	 * @param response http��Ӧ
	 * @param secure	�Ƿ�ȫcookie,��ȫcookie��Ҫhttps����
	 * @param domain	cookie������

	 * @param path	cookie��·��

	 * @param expiry	cookie����Ч��,-1Ϊ�Ựcookie,��ʾ�ر��������ʱ��ر�cookie
	 * @param key	cookie������

	 * @param value	cookie��ֵ

	 * @return ���ӵ�cookie����
	 */
	public static Cookie addCookie(HttpServletResponse response, boolean secure, String domain, String path,
			int expiry, String key, String value) {
		Cookie cook = new Cookie(key, value);
		cook.setSecure(secure);
		cook.setMaxAge(expiry);
		cook.setDomain(domain);
		cook.setPath(path);
		response.addCookie(cook);
		return cook;
	}

	/**
	 * ɾ��ĳ��cookie
	 * @param response	http��Ӧ
	 * @param key	Ҫɾ����cookie������

	 * @param request	http����
	 */
	public static void delCookie(HttpServletResponse response, String key,String domain, String path, HttpServletRequest request) {
		Cookie cooks[] = request.getCookies();
		if (cooks == null)
			return;
		for (int i = 0; i < cooks.length; i++) {
			Cookie cook = cooks[i];
			String name = cook.getName();
			if (name.equals(key)) {
				cook.setMaxAge(0);
				cook.setDomain(domain);
				cook.setPath(path);
				response.addCookie(cook);
			}
		}

	}

	/**
	 * ������������ȡ��session������,���session�����ڻ������Բ�����,�򷵻�null
	 * @param req http������

	 * @param attrName	session����������

	 * @return ��������ֵ,�Ҳ����򷵻�null
	 */
	public static Object getSessionAttr(HttpServletRequest req, String attrName) {
		HttpSession session = req.getSession(false);
		return (session != null ? session.getAttribute(attrName) : null);
	}
	/**
	 * ��session�������ֵ

	 * @param req	http����
	 * @param attrName	��������

	 * @param value	����ֵ

	 */
	public static void setSessionAttr(HttpServletRequest req, String attrName, Object value) {
		HttpSession session = req.getSession(false);
		if(session==null)
			return;
		session.setAttribute(attrName, value);
	}

}
