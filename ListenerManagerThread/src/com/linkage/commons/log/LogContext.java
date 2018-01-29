package com.linkage.commons.log;

import java.io.Serializable;

/**
 * 保存日志的上下文信息，如员工号、日志开关等
 * @author zhaoxin
 *
 */
public class LogContext implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1824909131431581344L;
	private String staff;
	private boolean logSwitch;
	public LogContext(String staff, boolean logSwitch){
		this.staff = staff;
		this.logSwitch = logSwitch;
	}
	public String getStaff() {
		return staff;
	}
	public void setStaff(String staff) {
		this.staff = staff;
	}
	/**
	 * 日志开关

	 * @return true表示该员工开启了日志,false表示关闭日志
	 */
	public boolean getLogSwitch() {
		return logSwitch;
	}
	public void setLogSwitch(boolean logSwitch) {
		this.logSwitch = logSwitch;
	}

}
