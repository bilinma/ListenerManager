package com.linkage.commons.spring;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import com.jamonapi.Monitor;
import com.jamonapi.MonitorFactory;

/**
 * ˵����
 *   ԭ��spring�ṩ����jamon���ɵ�AOP������org.springframework.aop.interceptor.JamonPerformanceMonitorInterceptor
 *   �ǳ����ᣬҪ��log4j.properties�ļ������ã�
 *       log4j.logger.org.springframework.aop.interceptor.JamonPerformanceMonitorInterceptor=TRACE
 *   ����ſ��ԣ��������������������������ø���
 *       log4j.logger.org.springframework=error,stdout
 *   JamonPerformanceMonitorInterceptor�ͻ᲻ͣ��дtrace��־��
 *   Ϊ�˲�дlog4j��־��������log4j.properties���ֿ���ʹ��jamon�����Ծ��Լ�дһ��spring��AOP������
 * @author zhaoxin
 *
 */
public class JamonPerformanceMonitorInterceptor implements MethodInterceptor {
	//Ĭ��Ϊtrue,��ʾjamon��monitorĬ�Ͽ���
	private boolean monitorPerformance = true;

	public void setMonitorPerformance(boolean monitorPerformance) {
		this.monitorPerformance = monitorPerformance;
	}

	public boolean isMonitorPerformance() {
		return monitorPerformance;
	}

	public Object invoke(MethodInvocation mi) throws Throwable {
		Monitor monitor = isMonitorPerformance() ? MonitorFactory.start(methodSignature(mi)) : null;
		try {
			return mi.proceed();
		} finally {
			if (isMonitorPerformance())
				monitor.stop();
		}
	}

	/**
	 * �����·�������ԭ����spring�ṩ��JamonPerformanceMonitorInterceptorû�з�������
	 * ���������
	 * @param mi
	 * @return
	 */
	private String methodSignature(MethodInvocation mi) {
		StringBuffer methodSignature = new StringBuffer(ClassUtils.getQualifiedMethodName(mi.getMethod()));
		methodSignature.append("(");
		Class[] paramTypes = mi.getMethod().getParameterTypes();
		for (int i = 0; i < paramTypes.length; i++) {
			if (i > 0)
				methodSignature.append(",");
			methodSignature.append(getShortName(paramTypes[i]));
		}
		methodSignature.append(")");
		return methodSignature.toString();
	}

	private String getShortName(Class clazz) {
		String name = clazz.getName();
		Package p = clazz.getPackage();
		if (p != null)
			name = StringUtils.delete(name, p.getName() + ".");
		return name;
	}
}
