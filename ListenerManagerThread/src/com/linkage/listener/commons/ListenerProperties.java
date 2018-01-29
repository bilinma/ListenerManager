/*
 * @(#)ListenerProperties.java
 * 本类创建于2009年9月1日 
 */
package com.linkage.listener.commons;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import com.linkage.commons.util.Log;

/**
 * 侦听属性文件的操作类 
 * @version 0.5
 * @author 景伯文 
 */

public class ListenerProperties {

	private static final Log log = Log.getLog(ListenerProperties.class);

	public static boolean checkListenerState(String listenerName) {
		boolean result = false;
		String propertiesFileName = listenerName + ".properties";
		File file = new File(propertiesFileName);
		if (file.exists()) {
			//记录属性
			Properties prop = new Properties();//属性集合对象         
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(propertiesFileName);
				prop.load(fis);
				String state = prop.getProperty("state");
				if (state != null && state.equals(ListenerConstants.LISTENER_STATE_RUNNING)) {
					log.debug("-------------+" + listenerName + " checkListenerState  running  true!");
					result = true;
				} else {
					log.debug("-------------+" + listenerName + " checkListenerState  running  false!");
					result = false;
				}
			} catch (FileNotFoundException e) {
				log.debug("-------------+" + listenerName + " checkListenerState  running  false!");
				result = false;
			} catch (IOException e) {
				log.debug("-------------+" + listenerName + " checkListenerState  running  false!");
				result = false;
			} finally {
				//关闭流 
				try {
					if (fis != null) {
						fis.close();
					}
				} catch (Exception e) {
					log.error("------------Exception :" + e);
				}
			}
			return result;

		} else {
			log.debug("-------------+" + listenerName + " checkListenerState  running  false!");
			return false;
		}
	}

	public static void recordListenerState(String state, String listenerName) {
		String propertiesFileName = listenerName + ".properties";
		//判断是否存在，不在就创建文件
		File file = new File(propertiesFileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				log.error("------------IOException :" + e);
				e.printStackTrace();
			}
		}
		//记录属性
		Properties prop = new Properties();//属性集合对象         
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(propertiesFileName);
			prop.setProperty("id", String.valueOf(Thread.currentThread().getName()));
			prop.setProperty("state", state);
			prop.store(fos, "listener state");
		} catch (FileNotFoundException e) {
			log.error("------------属性文件没找到 :" + e);
		} catch (IOException e) {
			log.error("------------IOException :" + e);
		} finally {
			//关闭流 
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (Exception e) {
				log.error("------------Exception :" + e);
			}
		}
	}
}
