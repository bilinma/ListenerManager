package com.linkage.demo.smo;

import com.linkage.demo.bmo.WorkFlowAlarmBmo;

public class WorkFlowAlarmSmoImpl implements WorkFlowAlarmSmo{
	private WorkFlowAlarmBmo workFlowAlarmBmo;
	
	public void setWorkFlowAlarmBmo(WorkFlowAlarmBmo workFlowAlarmBmo) {
		this.workFlowAlarmBmo = workFlowAlarmBmo;
	}

	public void procesAlarmEvent() {
		System.out.println("-------------业务处理");
	}


}
