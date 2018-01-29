/*
 * @(#)ListenerConsoleAction.java
 * 本类创建于2009年9月29日 
 */
package com.linkage.listener.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.linkage.commons.util.Log;
import com.linkage.listener.commons.RunListener;
import com.linkage.listener.dto.ListenerDTO;
import com.opensymphony.xwork2.ActionSupport;

/** 
 * 帧听action类. 
 * @version 0.5
 * @author 马小斌
 */

public class ListenerConsoleAction extends ActionSupport {
	private static final Log log = Log.getLog(ListenerConsoleAction.class);
	private static final long serialVersionUID = 2542452791523709442L;

	/**
	 * 检查侦听状态
	 * @return
	 */
	public String checkListenerState() {
		//取response对象 
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		ListenerConfigProcesser listenerConfigProcesser = new ListenerConfigProcesser();
		List<ListenerDTO> list = listenerConfigProcesser.readAllListenerConfig();
		String info = RunListener.checkListenerState(list);
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		try {
			Writer wr = response.getWriter();
			wr.write(info);
			wr.close();
		} catch (IOException e) {
			log.error("checkListenerState error：", e);
		}
		return null;
	}

	/**
	 * 启动侦听
	 * @return
	 */
	public String startListener() {
		//取request,response对象 
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		ListenerConfigProcesser listenerConfigProcesser = new ListenerConfigProcesser();
		ListenerDTO listenerDTO = listenerConfigProcesser.readListenerConfig(name);
		String info = RunListener.startListener(listenerDTO);
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		try {
			Writer wr = response.getWriter();
			wr.write(info);
			wr.close();
		} catch (IOException e) {
			log.error("startListener error：", e);
		}
		return null;
	}

	/**
	 * 检查侦听直到运行完成
	 * @return
	 */
	public String checkListenerRun() {
		//取request,response对象 
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		List<String> names = getListenerNames(request);
		ListenerConfigProcesser listenerConfigProcesser = new ListenerConfigProcesser();
		List<ListenerDTO> list = listenerConfigProcesser.readListenerConfig(names);
		String info = RunListener.checkListenerRun(list);
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		try {
			Writer wr = response.getWriter();
			wr.write(info);
			wr.close();
		} catch (IOException e) {
			log.error("checkListenerRun error：", e);
		}
		return null;
	}

	/**
	 * 停止帧听
	 * @return
	 */
	public String stopListener() {
		//取request,response对象 
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		List<String> names = new ArrayList<String>();
		System.out.println("stop name is:" + name);
		names.add(name);
		ListenerConfigProcesser listenerConfigProcesser = new ListenerConfigProcesser();
		List<ListenerDTO> list = listenerConfigProcesser.readListenerConfig(names);
		String info = RunListener.stopListener(list);
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		try {
			Writer wr = response.getWriter();
			wr.write(info);
			wr.close();
		} catch (IOException e) {
			log.error("stopListener error：", e);
		}
		return null;
	}

	/**
	 * 获取前台传入的侦听名称数组
	 * @param request
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private List<String> getListenerNames(HttpServletRequest request) {
		List<String> names = new ArrayList<String>();
		try {
			InputStream in = request.getInputStream();
			SAXReader xmlReader = new SAXReader();
			Document document = xmlReader.read(in);
			List<Element> eNames = document.selectNodes("//root/name");
			for (Element eName : eNames) {
				names.add(eName.getText());
			}
		} catch (Exception e) {
			log.error("getListenerNames error：", e);
		}
		return names;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String name;
	
}
