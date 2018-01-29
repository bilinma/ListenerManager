package com.linkage.listener.test;

import java.util.ArrayList;
import java.util.List;

import com.linkage.listener.commons.RunListener;
import com.linkage.listener.dto.ListenerDTO;
import com.linkage.listener.web.ListenerConfigProcesser;

public class TestStop {

	/**
	 * @param args
	 */
	public static void main(String[] args){
		ListenerConfigProcesser listenerConfigProcesser = new ListenerConfigProcesser();
		
		List<String> names = new ArrayList();
		names.add("WorkFlowAlarmThread");
		List<ListenerDTO> list = listenerConfigProcesser.readListenerConfig(names);
		String info = RunListener.stopListener(list);
	}
	

}
