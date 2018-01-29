package com.linkage.demo.bmo;

import com.linkage.demo.dao.WorkFlowAlarmDao;


public class WorkFlowAlarmBmoImpl implements WorkFlowAlarmBmo {
	private WorkFlowAlarmDao workFlowAlarmDao;

	public void setWorkFlowAlarmDao(WorkFlowAlarmDao workFlowAlarmDao) {
		this.workFlowAlarmDao = workFlowAlarmDao;
	}
	
}
