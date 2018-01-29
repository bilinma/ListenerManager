/*
 * @(#)ListenerCleanServlet.java
 * ���ഴ����2009��11��2�� 
 */
package com.linkage.listener.web;

import java.io.File;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.linkage.listener.commons.RunListener;
import com.linkage.listener.dto.ListenerDTO;

/**
 * ����״̬�ļ������ 
 * @version 0.5
 * @author ��С��
 */

public class ListenerCleanServlet extends HttpServlet implements Servlet {
	private static final long serialVersionUID = -7127556141525570573L;

	@Override
	public void init() throws ServletException {
		ListenerConfigProcesser lcp = new ListenerConfigProcesser();
		//web��������ʱ�������ļ�û�н���URL����ת���Ľ���ת��
		lcp.transferListenerConfigEncode();
		List<ListenerDTO> listenerDTOs = lcp.readAllListenerConfig();
		for (ListenerDTO listenerDTO : listenerDTOs) {
			String listenerName = listenerDTO.getName();
			String propertiesFileName = listenerName + ".properties";
			File file = new File(propertiesFileName);
			if (file.exists()) {
				file.delete();
			}
		}
	}

	@Override
	public void destroy() {
		ListenerConfigProcesser lcp = new ListenerConfigProcesser();
		List<ListenerDTO> listenerDTOs = lcp.readAllListenerConfig();
		String info = RunListener.stopListener(listenerDTOs);
		System.out.println("WarӦ��ֹͣ�������Զ�ֹͣ���Ϊ��" + info);
	}

}
