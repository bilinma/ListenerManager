package com.linkage.demo.thread;

import com.linkage.demo.smo.WorkFlowAlarmSmo;
import com.linkage.listener.thread.RunThread;

public class WorkFlowAlarmThread implements RunThread{
	
	private WorkFlowAlarmSmo workFlowAlarmSmo;

	public void setWorkFlowAlarmSMO(WorkFlowAlarmSmo workFlowAlarmSmo) {
		this.workFlowAlarmSmo = workFlowAlarmSmo;
	}

	public void runWork() {
		System.out.println("-------------业务处理");
	}
	
}
