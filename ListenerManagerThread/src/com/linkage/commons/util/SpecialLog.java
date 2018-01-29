/**
 * 
 */
package com.linkage.commons.util;

import com.linkage.commons.log.BssLoggerWrapper;

/**
 * ����		:	�����̱߳��������Ƿ��Debug��־
 * @author linxy 2012-06-24
 *
 */
public class SpecialLog {
	private static final String defaultLogName = "com.linkage.bss.commons.util.Log";
	static final BssLoggerWrapper specailDebugLog = new BssLoggerWrapper(defaultLogName);
	
	//�Ƿ���debug��־����
	private static ThreadLocal<Boolean> specailDebugFlag = new ThreadLocal<Boolean>();
	//������Ϣ
	private static ThreadLocal<String> desc = new ThreadLocal<String>();
	
	public static String getDesc() {
		return desc.get();
	}

	public static void setDesc(String info) {
		desc.set(info);
	}

	public static Boolean getSpecailDebugFlag() {
		Boolean flag = specailDebugFlag.get();
		return (null != flag) && flag;
	}

	public static void setSpecailDebugFlag(Boolean flag) {
		specailDebugFlag.set(flag);
	}
	
}
