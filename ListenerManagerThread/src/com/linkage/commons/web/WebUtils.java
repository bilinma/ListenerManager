package com.linkage.commons.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 处理 J2EE WEB的工具类,如取得cookie等,操作session等

 * @author zhaoxin
 *
 */
public class WebUtils {

	/**
	 * 根据传入的cookie name取得客户端请求的cookie

	 * @param request http的请求

	 * @param name cookie名称
	 * @return 如果对应名称的cookie不存在,返回null
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
	 * 根据cookie名称返回cookie的值

	 * @param request http请求
	 * @param name	cookie名称
	 * @return	返回cookie值,值不存在则返回null
	 */
	public static String getCookieValue(HttpServletRequest request, String name){
		Cookie cookie = getCookie(request,name);
		if(cookie==null)
			return null;
		return cookie.getValue();
	}

	/**
	 * 在http响应中增加cookie返回给客户端
	 * @param response http响应
	 * @param secure	是否安全cookie,安全cookie需要https访问
	 * @param domain	cookie的域名

	 * @param path	cookie的路径

	 * @param expiry	cookie的有效期,-1为会话cookie,表示关闭浏览器的时候关闭cookie
	 * @param key	cookie的名称

	 * @param value	cookie的值

	 * @return 增加的cookie对象
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
	 * 删除某个cookie
	 * @param response	http响应
	 * @param key	要删除的cookie的名称

	 * @param request	http请求
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
	 * 根据属性名称取得session的属性,如果session不存在或者属性不存在,则返回null
	 * @param req http的请求

	 * @param attrName	session的属性名称

	 * @return 返回属性值,找不到则返回null
	 */
	public static Object getSessionAttr(HttpServletRequest req, String attrName) {
		HttpSession session = req.getSession(false);
		return (session != null ? session.getAttribute(attrName) : null);
	}
	/**
	 * 给session添加属性值

	 * @param req	http请求
	 * @param attrName	属性名称

	 * @param value	属性值

	 */
	public static void setSessionAttr(HttpServletRequest req, String attrName, Object value) {
		HttpSession session = req.getSession(false);
		if(session==null)
			return;
		session.setAttribute(attrName, value);
	}

}
