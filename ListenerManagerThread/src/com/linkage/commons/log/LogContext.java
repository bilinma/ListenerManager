package com.linkage.commons.log;

import java.io.Serializable;

/**
 * ������־����������Ϣ����Ա���š���־���ص�
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
	 * ��־����

	 * @return true��ʾ��Ա����������־,false��ʾ�ر���־
	 */
	public boolean getLogSwitch() {
		return logSwitch;
	}
	public void setLogSwitch(boolean logSwitch) {
		this.logSwitch = logSwitch;
	}

}
