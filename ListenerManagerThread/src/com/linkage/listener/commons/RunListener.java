/*
 * @(#)RunListener.java
 * 本类创建于2009年9月29日 
 */
package com.linkage.listener.commons;

import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.linkage.commons.util.CommonUtil;
import com.linkage.commons.util.Log;
import com.linkage.listener.dto.ListenerDTO;
import com.linkage.listener.thread.WorkListener;

/** 
 * 帧听运行类. 
 * @version 0.5
 * @author  
 */

public class RunListener {

	private static Log log = Log.getLog(RunListener.class);

	/**
	 * 检查调度送业务受理消息侦听状态
	 * @return
	 */
	public static String checkListenerState(List<ListenerDTO> list) {
		log.debug("-------------checkListenerState");
		String info = null;
		String msgId = null;
		String msgContext = null;
		try {
			Document document = creatDocument();
			for (ListenerDTO listenerDTO : list) {
				String name = listenerDTO.getName();
				String description = listenerDTO.getDescription();
				try {
					boolean isRun = ListenerProperties.checkListenerState(name);
					if (isRun) {
						msgId = "0";
						msgContext = "运行！";
					} else {
						msgId = "1";
						msgContext = "停止！";
					}
				} catch (Exception e) {
					e.printStackTrace();
					msgId = "2";
					msgContext = "未知状态！" + CommonUtil.getExceptionString(e, 500);
				}
				addListenerInfo(document, name, description, msgId, msgContext);
			}
			info = document.asXML();
		} catch (Exception e) {
			e.printStackTrace();
			msgContext = "出错！" + CommonUtil.getExceptionString(e, 500);
			info = creatErrorInfo(msgContext).asXML();
		}
		log.debug("-------------info = " + info);
		return info;
	}

	/**
	 * 检查调度送业务受理消息侦听状态（侦听配置加载时调用）
	 * @return
	 */
	public static String checkListenerStateForConfig(List<ListenerDTO> list) {
		log.debug("start checkListenerStateForConfig ! ");
		String info = null;
		String msgId = null;
		String msgContext = null;
		Document document = null;
		try {
			document = creatDocument();
			for (ListenerDTO listenerDTO : list) {
				String name = listenerDTO.getName().trim();
				String description = listenerDTO.getDescription().trim();
				String threadCount = listenerDTO.getThreadCount().trim();
				String className = listenerDTO.getClassName().trim();
				try {
					boolean isRun = ListenerProperties.checkListenerState(name);
					if (isRun) {
						msgId = "0";
						msgContext = "运行！";
					} else {
						msgId = "1";
						msgContext = "停止！";
					}
				} catch (Exception e) {
					e.printStackTrace();
					msgId = "2";
					msgContext = "未知状态！" + CommonUtil.getExceptionString(e, 500);
				}
				addListenerInfoForConfig(document, name, description, threadCount, className, msgId, msgContext);
			}
			info = document.asXML();
		} catch (Exception e) {
			e.printStackTrace();
			msgContext = "出错！" + CommonUtil.getExceptionString(e, 500);
			info = creatErrorInfo(msgContext).asXML();
		}
		log.debug("-------------checkListenerStateForConfig  info = " + info);
		return info;
	}

	/**
	 * 启动调度送业务受理消息侦听
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String startListener(ListenerDTO listenerDTO) {
		log.debug("-------------startListener");
		String info = null;
		String msgId = null;
		String msgContext = null;
		try {
			Document document = creatDocument();
			String name = listenerDTO.getName();
			String threadCount = listenerDTO.getThreadCount();
			String className = listenerDTO.getClassName();
			String method = listenerDTO.getMethod();
			String waitTime = listenerDTO.getWaitTime();
			String description = listenerDTO.getDescription();
			try {
				WorkListener workListener = new WorkListener();
				workListener.start(className, Integer.parseInt(threadCount), name, method, waitTime);
				msgId = "0";
				msgContext = "运行结束！";
			} catch (Exception e) {
				e.printStackTrace();
				msgId = "1";
				msgContext = "启动失败！" + CommonUtil.getExceptionString(e, 500);
			}
			addListenerInfo(document, name, description, msgId, msgContext);
			info = document.asXML();
		} catch (Exception e) {
			e.printStackTrace();
			msgContext = "出错！" + CommonUtil.getExceptionString(e, 500);
			info = creatErrorInfo(msgContext).asXML();
		}
		log.debug("-------------startListener info = " + info);
		return info;
	}

	/**
	 * 检查调度送业务受理消息侦听状态
	 * @return
	 */
	public static String checkListenerRun(List<ListenerDTO> list) {
		log.debug("-------------checkListenerRun");
		String info = null;
		String msgId = null;
		String msgContext = null;
		Document document = creatDocument();
		for (ListenerDTO listenerDTO : list) {
			String name = listenerDTO.getName();
			String description = listenerDTO.getDescription();
			try {
				boolean isRun = false;
				while (!isRun) {
					isRun = ListenerProperties.checkListenerState(name);
					Thread.sleep(2000);
				}
				msgId = "0";
				msgContext = "启动成功！";
				addListenerInfo(document, name, description, msgId, msgContext);
			} catch (Exception e) {
				e.printStackTrace();
				msgId = "1";
				msgContext = "启动失败！" + CommonUtil.getExceptionString(e, 500);
				addListenerInfo(document, name, description, msgId, msgContext);
			}
		}
		info = document.asXML();
		log.debug("-------------checkListenerRun info = " + info);
		return info;
	}

	/**
	 * 停止调度送业务受理消息侦听
	 * @return
	 */
	public static String stopListener(List<ListenerDTO> list) {
		log.debug("-------------stopListener");
		String info = null;
		String msgId = null;
		String msgContext = null;
		try {
			Document document = creatDocument();
			for (ListenerDTO listenerDTO : list) {
				String name = listenerDTO.getName();
				String description = listenerDTO.getDescription();
				try {
					ListenerProperties.recordListenerState(ListenerConstants.LISTENER_STATE_STOP, name);
					boolean isrun = true;
					while (isrun) {
						isrun = ListenerProperties.checkListenerState(name);
						Thread.sleep(2000);
					}
					msgId = "0";
					msgContext = "停止成功！";
				} catch (Exception e) {
					e.printStackTrace();
					msgId = "1";
					msgContext = "停止失败！" + CommonUtil.getExceptionString(e, 500);
				}
				addListenerInfo(document, name, description, msgId, msgContext);
			}
			info = document.asXML();
		} catch (Exception e) {
			e.printStackTrace();
			msgContext = "出错！" + CommonUtil.getExceptionString(e, 500);
			info = creatErrorInfo(msgContext).asXML();
		}
		log.debug("-------------stopListener info = " + info);
		return info;
	}

	private static Document creatDocument() {
		Document document = DocumentHelper.createDocument();
		document.addElement("root");
		return document;
	}

	private static Document addListenerInfo(Document document, String listenerName, String description, String msgId,
			String msgContext) {
		Element root = document.getRootElement();
		Element listener = root.addElement("listener");
		Element eName = listener.addElement("name");
		Element eDescription = listener.addElement("description");
		Element eMsgId = listener.addElement("msgId");
		Element eMsgContext = listener.addElement("msgContext");
		eName.addText(listenerName);
		eDescription.addText(description);
		eMsgId.addText(msgId);
		eMsgContext.addText(msgContext);
		return document;
	}

	private static Document addListenerInfoForConfig(Document document, String listenerName, String description,
			String threadCount, String className, String msgId, String msgContext) {
		Element root = document.getRootElement();
		Element listener = root.addElement("listener");

		if (listenerName != null && !"".equals(listenerName)) {
			Element eName = listener.addElement("name");
			eName.addText(listenerName);
		}

		if (description != null && !"".equals(description)) {
			Element eDescription = listener.addElement("description");
			eDescription.addText(description);
		}

		if (threadCount != null && !"".equals(threadCount)) {
			Element eThreadCount = listener.addElement("threadCount");
			eThreadCount.addText(threadCount);
		}

		if (className != null && !"".equals(className)) {
			Element eClassName = listener.addElement("className");
			eClassName.addText(className);
		}

		Element eMsgId = listener.addElement("msgId");
		eMsgId.addText(msgId);

		Element eMsgContext = listener.addElement("msgContext");
		eMsgContext.addText(msgContext);
		return document;
	}

	private static Document creatErrorInfo(String errorInfo) {
		Document document = DocumentHelper.createDocument();
		Element error = document.addElement("error");
		Element errorContext = error.addElement("errorContext");
		errorContext.addText(errorInfo);
		return document;
	}
}
