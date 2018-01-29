/*
 * @(#)ListenerConfigProcesser.java
 * 本类创建于2009年10月23日 
 */
package com.linkage.listener.web;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.linkage.commons.util.Log;
import com.linkage.listener.dto.ListenerDTO;

/** 
 * 侦听的配置处理类，用于侦听控制台和运行的配置. 
 * @version 0.5
 * @author 马小斌
 */

public class ListenerConfigProcesser {
	private static Log log = Log.getLog(ListenerConfigProcesser.class);

	private static String ListenerFileName = "csip_listenerConfig.xml";

	/**
	 * 读取配置中所有的侦听配置
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ListenerDTO> readAllListenerConfig() {
		List<ListenerDTO> list = new ArrayList<ListenerDTO>();
		try {
			Document configDoc = qryConfig();
			List<Element> listenerNodes = configDoc.selectNodes("//listeners/listener");
			for (Element eListener : listenerNodes) {
				ListenerDTO listenerDTO = new ListenerDTO();
				listenerDTO.setClassName(eListener.attributeValue("class"));
				listenerDTO.setDescription(URLDecoder.decode(eListener.attributeValue("description"), "UTF-8"));
				listenerDTO.setName(eListener.attributeValue("name"));
				listenerDTO.setThreadCount(eListener.attributeValue("threadCount"));
				list.add(listenerDTO);
			}
		} catch (Exception e) {
			log.error("readAllListenerConfig error：", e);
		}
		return list;
	}

	/**
	 * 读取配置中指定名称的侦听配置
	 * @param names 侦听名称的数组
	 * @return
	 */
	public List<ListenerDTO> readListenerConfig(List<String> names) {
		List<ListenerDTO> list = new ArrayList<ListenerDTO>();
		try {
			Document configDoc = qryConfig();
			for (String name : names) {
				Element eListener = (Element) configDoc
						.selectSingleNode("//listeners/listener[(@name='" + name + "')]");
				if (eListener != null) {
					ListenerDTO listenerDTO = new ListenerDTO();
					listenerDTO.setClassName(eListener.attributeValue("class"));
					listenerDTO.setDescription(URLDecoder.decode(eListener.attributeValue("description"), "UTF-8"));
					listenerDTO.setName(eListener.attributeValue("name"));
					listenerDTO.setThreadCount(eListener.attributeValue("threadCount"));
					list.add(listenerDTO);
				}
			}
		} catch (Exception e) {
			log.error("readListenerConfig error：", e);
		}
		return list;
	}

	/**
	 * 读取配置中指定名称的侦听配置
	 * @param name 侦听名称
	 * @return
	 */
	public ListenerDTO readListenerConfig(String name) {
		ListenerDTO listenerDTO = new ListenerDTO();
		try {
			Document configDoc = qryConfig();
			Element eListener = (Element) configDoc.selectSingleNode("//listeners/listener[(@name='" + name + "')]");
			if (eListener != null) {
				listenerDTO.setName(eListener.attributeValue("name"));
				listenerDTO.setClassName(eListener.attributeValue("class"));
				listenerDTO.setMethod(eListener.attributeValue("method"));
				listenerDTO.setThreadCount(eListener.attributeValue("threadCount"));
				listenerDTO.setWaitTime(eListener.attributeValue("waitTime"));
				listenerDTO.setDescription(URLDecoder.decode(eListener.attributeValue("description"), "UTF-8"));
			}
		} catch (Exception e) {
			log.error("readListenerConfig error：", e);
		}
		return listenerDTO;
	}

	/**
	 * 添加侦听配置
	 * @param strArr 前台传入的侦听配置信息对象
	 */
	public void addListenerConfig(String[] strArr) {
		String listenerName = null;
		String listenerDesc = null;
		String listenerClass = null;
		String threadCount = null;
		String delFlag = null;
		try {
			Document configDoc = qryConfig();
			Element elisteners = (Element) configDoc.selectSingleNode("//listeners");
			for (int i = 0; i < strArr.length; i++) {
				String[] strArr0 = strArr[i].split("\\|");
				delFlag = strArr0[0];
				listenerDesc = strArr0[1];
				String[] strName = strArr0[2].split("\\.");
				listenerName = strName[strName.length - 1].trim();
				listenerClass = strArr0[2];
				threadCount = strArr0[3];
				if (delFlag != null && delFlag.equals("1")) {
					Element listenerEle = (Element) elisteners.selectSingleNode("//listener[(@name='" + listenerName
							+ "')]");
					if (listenerEle != null) {
						listenerEle.attribute("threadCount").setValue(threadCount);
						listenerEle.attribute("description").setValue(listenerDesc);
						listenerEle.attribute("class").setValue(listenerClass);
					} else {
						elisteners.addElement("listener").addAttribute("name", listenerName).addAttribute(
								"threadCount", threadCount).addAttribute("description", listenerDesc).addAttribute(
								"class", listenerClass);
					}
				}

				if (delFlag != null && delFlag.equals("0")) {
					Element removeListenerEle = (Element) elisteners.selectSingleNode("//listener[(@name='"
							+ listenerName + "')]");
					elisteners.remove(removeListenerEle);
				}
			}
			saveConfig(configDoc);
		} catch (Exception e) {
			log.error("addListenerConfig error：", e);
		}
	}

	/**
	 * 将侦听配置文件中文描述编码转换为URL编码,避免不同的操作系统出现乱码
	 */
	public void transferListenerConfigEncode() {
		try {
			Document configDoc = qryConfig();
			List<Element> listenerNodes = configDoc.selectNodes("//listeners/listener");
			for (Element eListener : listenerNodes) {
				//没有进行转换才转换,用%号判断有待完善,描述中不能出现%号
				if (eListener.attributeValue("description").indexOf("%") == -1) {
					eListener.attribute("description").setValue(
							URLEncoder.encode(eListener.attributeValue("description"), "UTF-8"));
				}
			}
			saveConfig(configDoc);
		} catch (Exception e) {
			log.error("addListenerConfig error：", e);
		}
	}

	/**
	 * 删除帧听配置
	 * @param listenerDTO 侦听配置的DTO对象
	 */
	public void delListenerConfig(ListenerDTO listenerDTO) {
		try {
			Document configDoc = qryConfig();
			String name = listenerDTO.getName();
			Element elisteners = (Element) configDoc.selectSingleNode("//listeners");
			Element elistener = (Element) elisteners.selectSingleNode("//listener[(@name='" + name + "')]");
			elisteners.remove(elistener);
			saveConfig(configDoc);
		} catch (Exception e) {
			log.error("delListenerConfig error：", e);
		}
	}

	//读取侦听配置文件的数据
	private Document qryConfig() throws Exception {
		Document document = null;
		try {
			URL url = this.getClass().getClassLoader().getResource(ListenerFileName);
			log.debug("qryConfig path = " + url.getFile());
			InputStream in = new FileInputStream(URLDecoder.decode(url.getFile(), "UTF-8"));
			//InputStream in = this.getClass().getClassLoader().getResourceAsStream(ListenerFileName);
			SAXReader reader = new SAXReader();
			document = reader.read(in);
			in.close();
		} catch (Exception e) {
			log.error("qryConfig error：", e);
			throw new Exception("读取配置文件发生异常，错误信息:\n" + e.getMessage());
		}
		return document;
	}

	//写侦听配置文件的数据
	private void saveConfig(Document configDoc) throws Exception {
		if (configDoc != null) {
			try {
				URL url = this.getClass().getClassLoader().getResource(ListenerFileName);
				log.debug("saveConfig.getPath()=" + url.getPath());
				Writer writer1 = new OutputStreamWriter(
						new FileOutputStream(URLDecoder.decode(url.getFile(), "UTF-8")), "UTF-8");
				OutputFormat format = OutputFormat.createPrettyPrint();
				XMLWriter output = new XMLWriter(writer1, format);
				output.write(configDoc);
				output.close();
			} catch (Exception e) {
				log.error("saveConfig error：", e);
				throw new Exception("写配置文件发生异常，错误信息:\n" + e.getMessage());
			}
		}
	}
}
