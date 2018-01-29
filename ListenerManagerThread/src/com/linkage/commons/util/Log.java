package com.linkage.commons.util;

import com.linkage.commons.log.BssLoggerWrapper;
import com.linkage.commons.log.LogContext;
import com.linkage.commons.log.LogContextHolder;

/**
 * ����		:	bss��װ����־��,���е�bss�Ĵ��������������
 * �÷�ʾ��	:	private static Log log = Log.getLog(Sample.class);
 *          	log.debug("my name is {}", "zhaoxin");
 * @author zhaoxin
 *
 */
public class Log {

	private BssLoggerWrapper log;
	private static final String defaultLogName = "com.linkage.bss.commons.util.Log";

	/**
	 * ���캯��
	 * @param name
	 */
	private Log(String name) {
		log = new BssLoggerWrapper(name);
	}

	/**
	 * ��̬���캯��,����name����log,�ͻ����������������
	 * @param name
	 * @return
	 */
	public static Log getLog(String name) {
		String s = name;
		if (s == null)
			s = defaultLogName;
		return new Log(s);
	}

	/**
	 * ��̬���캯��,����name����log,�ͻ����������������
	 * @param clazz
	 * @return
	 */
	public static Log getLog(Class clazz) {
		String s = defaultLogName;
		if (clazz != null)
			s = clazz.getName();
		return getLog(s);
	}

	/**
	 * �ж���־�ļ�¼ģʽ
	 * @return ö������LogModel,�ֱ�ΪRunTimeClose,RunTimeOpen,RunTimeClose
	 */
	private static LogModel getLogModel() {
		LogModel model = LogModel.RunTimeClose;
		LogContext logContext = LogContextHolder.getLogContext();
		if (logContext != null) {
			if (logContext.getLogSwitch()) {
				model = LogModel.RunTimeOpen;
			} else
				model = LogModel.RunTimeClose;
		} else {
			//��־ģʽΪ:����ģʽ
			model = LogModel.Develop;
		}
		return model;
	}

	/**
	 * �ж��Ƿ���־,logModelΪ����ģʽĬ��Ϊ����־,��logback.xmlȥ�ж�
	 * @param model
	 * @return
	 */
	private static boolean isLogOpen(LogModel model) {
		if (model == LogModel.RunTimeOpen || model == LogModel.Develop)
			return true;
		else
			return false;
	}

	/**
	 * Ĭ���ж���־���ط���
	 * @return
	 */
	private static boolean isLogOpen() {
		return isLogOpen(getLogModel());
	}

	/**
	 * ������־�е�Ա���ţ��̺߳ŵ���Ϣ
	 * @return
	 */
	private static String buildLogContextInfo() {
		long threadId = Thread.currentThread().getId();
		String staff = null;
		LogContext logContext = LogContextHolder.getLogContext();
		if (logContext != null) {
			staff = logContext.getStaff();
		}
		StringBuilder sb = new StringBuilder();
		sb.append("BSS_LOGGER:threadId=");
		sb.append(threadId);
		sb.append(",staff=");
		sb.append(staff);
		sb.append(",info=");
		return sb.toString();
	}

	/**
	 * ����	����¼debug��־
	 * ʾ��	��log.debug("My name is {}, i am {} years old", "zhaox", "30");
	 * 
	 * @param message	��־��Ϣ,��������������{}����ʾ
	 * @param args		�ɱ��������,���Դ���0�����,��Message�е�{}�Ŷ�Ӧ
	 */
	public void debug(String message, Object... args) {
		try{
			boolean flag = SpecialLog.getSpecailDebugFlag();
			BssLoggerWrapper theLog = flag? SpecialLog.specailDebugLog : log;
			if(flag){
				message = "[ LogName:" + log.getName() + " Desc:" + SpecialLog.getDesc() + "] " + message;
			}
			LogModel model = getLogModel();
			if (isLogOpen(model)) {
				if (model == LogModel.RunTimeOpen) {
					theLog.debug(buildLogContextInfo() + message, args);
				} else
					theLog.debug(message, args);
			}
		}catch(Throwable e){
			log.error(e.getMessage());
		}

	}

	/**
	 * ����	����¼info��־
	 * ʾ��	��log.info("My name is {}, i am {} years old", "zhaox", "30");
	 * 
	 * @param message	��־��Ϣ,��������������{}����ʾ
	 * @param args		�ɱ��������,���Դ���0�����,��Message�е�{}�Ŷ�Ӧ
	 */
	public void info(String message, Object... args) {
		try {
			LogModel model = getLogModel();
			if (isLogOpen(model)) {
				if (model == LogModel.RunTimeOpen) {
					log.info(buildLogContextInfo() + message, args);
				} else
					log.info(message, args);
			}
		} catch (Throwable e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * ����	����¼warn��־
	 * ʾ��	��log.warn("My name is {}, i am {} years old", "zhaox", "30");
	 * 
	 * @param message	��־��Ϣ,��������������{}����ʾ
	 * @param args		�ɱ��������,���Դ���0�����,��Message�е�{}�Ŷ�Ӧ
	 */
	public void warn(String message, Object... args) {
		try {
			LogModel model = getLogModel();
			if (isLogOpen(model)) {
				if (model == LogModel.RunTimeOpen) {
					log.warn(buildLogContextInfo() + message, args);
				} else
					log.warn(message, args);
			}
		} catch (Throwable e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * ����	����¼trace��־
	 * ʾ��	��log.trace("My name is {}, i am {} years old", "zhaox", "30");
	 * 
	 * @param message	��־��Ϣ,��������������{}����ʾ
	 * @param args		�ɱ��������,���Դ���0�����,��Message�е�{}�Ŷ�Ӧ
	 */
	public void trace(String message, Object... args) {
		try {
			LogModel model = getLogModel();
			if (isLogOpen(model)) {
				if (model == LogModel.RunTimeOpen) {
					log.trace(buildLogContextInfo() + message, args);
				} else
					log.trace(message, args);
			}
		} catch (Throwable e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * ����	����¼error��־
	 * ʾ��	��log.error("My name is {}, i am {} years old", "zhaox", "30");
	 * 
	 * @param message	��־��Ϣ,��������������{}����ʾ
	 * @param args		�ɱ��������,���Դ���0�����,��Message�е�{}�Ŷ�Ӧ
	 */
	public void error(String message, Object... args) {
		try {
			LogModel model = getLogModel();
			//error����������ʱ���� if (isLogOpen(model)){
			if (model == LogModel.RunTimeOpen) {
				log.error(buildLogContextInfo() + message, args);
			} else
				log.error(message, args);
			//}
		} catch (Throwable e) {
			log.error(e.getMessage());
		}
	}

	/**
	 * ����	����¼error��־
	 * ʾ��	��log.error("My name is " + "zhaox", exception);
	 * 
	 * @param message	��־��Ϣ,
	 * @param e			�쳣
	 */
	public void error(String message, Throwable e) {
		try {
			LogModel model = getLogModel();
			//error����������ʱ����if (isLogOpen(model)){
			if (model == LogModel.RunTimeOpen) {
				log.error(buildLogContextInfo() + message, e);
			} else
				log.error(message, e);
			//}
		} catch (Throwable error) {
			error.printStackTrace();
		}
	}
	/**
	 * �Ƿ�debug����
	 * @return
	 */
	public boolean isDebugEnabled() {
		try {
			if (log == null)
				return false;

			return isLogOpen() && log.isDebugEnabled();
		} catch (Throwable e) {
			return false;
		}
	}
	/**
	 * �Ƿ�error�������
	 * @return
	 */
	public boolean isErrorEnabled() {
		try {
			if (log == null)
				return false;
			//zhaoxin 2009-8-24 modify : error�������־��������ʱ��������,Ĭ����logback.xml�������������Ƿ��ӡ
			//return isLogOpen() && _log.isErrorEnabled();
			return log.isErrorEnabled();
		} catch (Throwable e) {
			return false;
		}
	}
	/**
	 * �Ƿ�info�������
	 * @return
	 */
	public boolean isInfoEnabled() {
		try {
			if (log == null)
				return false;
			return isLogOpen() && log.isInfoEnabled();
		} catch (Throwable e) {
			return false;
		}
	}
	/**
	 * �Ƿ�trace�������
	 * @return
	 */
	public boolean isTraceEnabled() {
		try {
			if (log == null)
				return false;
			return isLogOpen() && log.isTraceEnabled();
		} catch (Throwable e) {
			return false;
		}
	}
	/**
	 * �Ƿ�warn�������
	 * @return
	 */
	public boolean isWarnEnabled() {
		try {
			if (log == null)
				return false;
			return isLogOpen() && log.isWarnEnabled();
		} catch (Throwable e) {
			return false;
		}
	}

	/**
	 * ��־״̬���ֱ�Ϊ'����ʱ����','����ʱ�ر�','����ģʽ'
	 * @author zhaoxin
	 *
	 */
	private enum LogModel {
		RunTimeOpen, RunTimeClose, Develop
	}
}