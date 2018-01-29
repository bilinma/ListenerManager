package com.linkage.commons.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.WebApplicationContextUtils;
/**
 * web.xml����ʱ��ע��spring context
 * ����ʹ���߿���ͨ��SpringBeanInvokerֱ�ӵ��÷���
 * web.xml��������:
 * 	<listener>
 *		<listener-class>
 *			com.linkage.bss.commons.spring.ContextHolderListener
 *		</listener-class>
 *	</listener>
 * @author zhaoxin
 *
 */
public class ContextHolderListener extends ContextLoaderListener {

	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		ServletContext context = event.getServletContext();
		ApplicationContext ctx = WebApplicationContextUtils
				.getRequiredWebApplicationContext(context);
		ContextHolder.setApplicationContext(ctx);
	}
}