/**
 * 
 */
package com.linkage.commons.util;

import com.linkage.commons.log.BssLoggerWrapper;

/**
 * 功能		:	根据线程变量设置是否打开Debug日志
 * @author linxy 2012-06-24
 *
 */
public class SpecialLog {
	private static final String defaultLogName = "com.linkage.bss.commons.util.Log";
	static final BssLoggerWrapper specailDebugLog = new BssLoggerWrapper(defaultLogName);
	
	//是否开启debug日志开关
	private static ThreadLocal<Boolean> specailDebugFlag = new ThreadLocal<Boolean>();
	//描述信息
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
