package com.linkage.listener.test;

import com.linkage.listener.commons.RunListener;
import com.linkage.listener.dto.ListenerDTO;
import com.linkage.listener.web.ListenerConfigProcesser;

public class TestRun {

	/**
	 * @param args
	 */
	public static void main(String[] args){
		ListenerConfigProcesser listenerConfigProcesser = new ListenerConfigProcesser();
		ListenerDTO listenerDTO = listenerConfigProcesser.readListenerConfig("WorkFlowAlarmThread");
		String info = RunListener.startListener(listenerDTO);
	}
	

}
