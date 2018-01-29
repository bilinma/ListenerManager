package com.linkage.commons.spring;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
/**
 * 持有Spring Application context的类
 * @author zhaoxin
 *
 */
public class ContextHolder {

	private static ApplicationContext applicationContext;

    public ContextHolder() {
    }

    public static void setApplicationContext(ApplicationContext ctx)
            throws BeansException {
    	applicationContext = ctx;
    }

    /** Access to spring wired beans. */
    public static ApplicationContext getSpringContext() {
        return applicationContext;
    }
}
