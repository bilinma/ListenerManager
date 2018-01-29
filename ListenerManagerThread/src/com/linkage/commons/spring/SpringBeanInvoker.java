package com.linkage.commons.spring;

/**
 * spring容器管理的bean的调用器
 * @author zhaoxin
 *
 */
public class SpringBeanInvoker {
	 /**
     * 提供给需要集成spring的调用者调用的方法
     * 例如 IOrderSMO orderSMO = (IOrderSMO)SpringBeanInvoker.getBean("order.orderSMO");
     * @param  beanName spring容器中配置的bean名称
     * @throws  
     * @return  Object --spring bean的实例

     * @since  
     */
	public static Object getBean(String beanName){
		return ContextHolder.getSpringContext().getBean(beanName);
	}
}
