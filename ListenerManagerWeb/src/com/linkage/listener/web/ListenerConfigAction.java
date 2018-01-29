/*
 * @(#)ListenerConfigAction.java
 * 本类创建于2009年10月23日 
 */
package com.linkage.listener.web;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.linkage.commons.util.Log;
import com.linkage.listener.commons.RunListener;
import com.linkage.listener.dto.ListenerDTO;
import com.opensymphony.xwork2.ActionSupport;

/** 
 * 侦听的配置action类. 
 * @version 0.5
 * @author 马小斌
 */

public class ListenerConfigAction extends ActionSupport {

	private static final Log log = Log.getLog(ListenerConfigAction.class);
	private static final long serialVersionUID = 3415167450347580222L;

	/**
	 * 检查侦听配置信息
	 * @return
	 */
	public String checkListenerStateForConfig() {
		//取response对象 
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		ListenerConfigProcesser listenerConfigProcesser = new ListenerConfigProcesser();
		List<ListenerDTO> list = listenerConfigProcesser.readAllListenerConfig();
		String info = RunListener.checkListenerStateForConfig(list);
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		try {
			Writer wr = response.getWriter();
			wr.write(info);
			wr.close();
		} catch (IOException e) {
			log.error("checkListenerStateForConfig error：", e);
		}
		return null;
	}

	/**
	 * 保存侦听配置.
	 * @return
	 */
	public String saveListenerConfig() {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		try {
			String dataStr = request.getParameter("dataInfoArray");
			log.debug("前台传过来的参数是{}", dataStr);
			String[] strArr = dataStr.split("\\,");
			ListenerConfigProcesser listenerConfigProcesser = new ListenerConfigProcesser();
			listenerConfigProcesser.addListenerConfig(strArr);
		} catch (Exception e) {
			log.error("saveListenerConfig error：", e);
		}
		try {
			Writer wr = response.getWriter();
			wr.write("success");
			wr.close();
		} catch (IOException e) {
			log.error("saveListenerConfig error：", e);
		}
		return null;
	}

	/**
	 * 删除侦听配置.
	 * @return
	 */
	public String delListenerConfig() {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		ListenerDTO listenerDTO = new ListenerDTO();
		try {
			String listenerClass = request.getParameter("listenerClass");
			String[] className = listenerClass.split("\\.");
			listenerDTO.setName(className[className.length - 1]);

		} catch (Exception e) {
			log.error("delListenerConfig error：", e);
		}

		ListenerConfigProcesser listenerConfigProcesser = new ListenerConfigProcesser();
		listenerConfigProcesser.delListenerConfig(listenerDTO);
		try {
			Writer wr = response.getWriter();
			wr.write("");
			wr.close();
		} catch (IOException e) {
			log.error("delListenerConfig error：", e);
		}
		return null;
	}

	/**
	 * 校验侦听类名.
	 * @return
	 */
	public String checkListenerClass() {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		String retMsg = "";
		String listenerClass = request.getParameter("newClassName");
		try {
			Class.forName(listenerClass);
		} catch (ClassNotFoundException e2) {
			retMsg = "无效的侦听类名 [" + listenerClass + "]!";
		}
		try {
			Writer wr = response.getWriter();
			wr.write(retMsg);
			wr.close();
		} catch (IOException e) {
			log.error("checkListenerClass error：", e);
		}
		return null;
	}
	
}
