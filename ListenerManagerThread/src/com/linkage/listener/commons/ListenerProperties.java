/*
 * @(#)ListenerProperties.java
 * ���ഴ����2009��9��1�� 
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
 * ���������ļ��Ĳ����� 
 * @version 0.5
 * @author ������ 
 */

public class ListenerProperties {

	private static final Log log = Log.getLog(ListenerProperties.class);

	public static boolean checkListenerState(String listenerName) {
		boolean result = false;
		String propertiesFileName = listenerName + ".properties";
		File file = new File(propertiesFileName);
		if (file.exists()) {
			//��¼����
			Properties prop = new Properties();//���Լ��϶���         
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
				//�ر��� 
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
		//�ж��Ƿ���ڣ����ھʹ����ļ�
		File file = new File(propertiesFileName);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				log.error("------------IOException :" + e);
				e.printStackTrace();
			}
		}
		//��¼����
		Properties prop = new Properties();//���Լ��϶���         
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(propertiesFileName);
			prop.setProperty("id", String.valueOf(Thread.currentThread().getName()));
			prop.setProperty("state", state);
			prop.store(fos, "listener state");
		} catch (FileNotFoundException e) {
			log.error("------------�����ļ�û�ҵ� :" + e);
		} catch (IOException e) {
			log.error("------------IOException :" + e);
		} finally {
			//�ر��� 
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
