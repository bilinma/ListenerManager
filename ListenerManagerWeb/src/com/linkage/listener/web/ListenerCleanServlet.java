/*
 * @(#)ListenerCleanServlet.java
 * 本类创建于2009年11月2日 
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
 * 侦听状态文件清除类 
 * @version 0.5
 * @author 马小斌
 */

public class ListenerCleanServlet extends HttpServlet implements Servlet {
	private static final long serialVersionUID = -7127556141525570573L;

	@Override
	public void init() throws ServletException {
		ListenerConfigProcesser lcp = new ListenerConfigProcesser();
		//web工程启动时将配置文件没有进行URL编码转换的进行转换
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
		System.out.println("War应用停止，侦听自动停止结果为：" + info);
	}

}
