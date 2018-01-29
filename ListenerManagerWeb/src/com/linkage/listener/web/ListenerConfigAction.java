/*
 * @(#)ListenerConfigAction.java
 * ���ഴ����2009��10��23�� 
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
 * ����������action��. 
 * @version 0.5
 * @author ��С��
 */

public class ListenerConfigAction extends ActionSupport {

	private static final Log log = Log.getLog(ListenerConfigAction.class);
	private static final long serialVersionUID = 3415167450347580222L;

	/**
	 * �������������Ϣ
	 * @return
	 */
	public String checkListenerStateForConfig() {
		//ȡresponse���� 
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
			log.error("checkListenerStateForConfig error��", e);
		}
		return null;
	}

	/**
	 * ������������.
	 * @return
	 */
	public String saveListenerConfig() {
		HttpServletResponse response = ServletActionContext.getResponse();
		HttpServletRequest request = ServletActionContext.getRequest();
		response.setContentType("text/xml;charset=utf-8");
		response.setHeader("Cache-Control", "no-cache");
		try {
			String dataStr = request.getParameter("dataInfoArray");
			log.debug("ǰ̨�������Ĳ�����{}", dataStr);
			String[] strArr = dataStr.split("\\,");
			ListenerConfigProcesser listenerConfigProcesser = new ListenerConfigProcesser();
			listenerConfigProcesser.addListenerConfig(strArr);
		} catch (Exception e) {
			log.error("saveListenerConfig error��", e);
		}
		try {
			Writer wr = response.getWriter();
			wr.write("success");
			wr.close();
		} catch (IOException e) {
			log.error("saveListenerConfig error��", e);
		}
		return null;
	}

	/**
	 * ɾ����������.
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
			log.error("delListenerConfig error��", e);
		}

		ListenerConfigProcesser listenerConfigProcesser = new ListenerConfigProcesser();
		listenerConfigProcesser.delListenerConfig(listenerDTO);
		try {
			Writer wr = response.getWriter();
			wr.write("");
			wr.close();
		} catch (IOException e) {
			log.error("delListenerConfig error��", e);
		}
		return null;
	}

	/**
	 * У����������.
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
			retMsg = "��Ч���������� [" + listenerClass + "]!";
		}
		try {
			Writer wr = response.getWriter();
			wr.write(retMsg);
			wr.close();
		} catch (IOException e) {
			log.error("checkListenerClass error��", e);
		}
		return null;
	}
	
}
