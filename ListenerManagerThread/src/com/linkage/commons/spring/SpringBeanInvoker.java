package com.linkage.commons.spring;

/**
 * spring���������bean�ĵ�����
 * @author zhaoxin
 *
 */
public class SpringBeanInvoker {
	 /**
     * �ṩ����Ҫ����spring�ĵ����ߵ��õķ���
     * ���� IOrderSMO orderSMO = (IOrderSMO)SpringBeanInvoker.getBean("order.orderSMO");
     * @param  beanName spring���������õ�bean����
     * @throws  
     * @return  Object --spring bean��ʵ��

     * @since  
     */
	public static Object getBean(String beanName){
		return ContextHolder.getSpringContext().getBean(beanName);
	}
}
