package com.linkage.listener.thread;

import java.util.ArrayList;
import java.util.List;

import com.linkage.commons.util.Log;
import com.linkage.listener.commons.ListenerConstants;
import com.linkage.listener.commons.ListenerProperties;
/**
 * 
 * @author Ma_xbin
 *
 */
public class WorkListener {
	private static final Log log = Log.getLog(WorkListener.class);
	
	//��������״̬
	private boolean runbl = true;
	//�������̳߳�
	private List<WorkListenerThread> threadPool;

	private void mainSleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			log.error("�߳��ж�:" + e);
		}
	}


	//�����ĳ�ʼ������
	private void ini(String className,Integer threadCount,String listenerName,String method,String waitTime){
		// �������߳�����Ĭ��Ϊ1���߳�
		threadPool = new ArrayList<WorkListenerThread>();
		if (threadCount > 0) {
			for (int i = 0; i < threadCount; i++) {
				log.debug("ҵ�����ӿ��̳߳�ʼ��������{}���̣߳���ǰ�ǵ�{}��", threadCount, i + 1);
				WorkListenerThread workListenerThread = new WorkListenerThread(i + 1, threadCount);
				workListenerThread.setThreadObjectName(className);
				workListenerThread.setMethod(method);
				workListenerThread.setWaitTime(waitTime);
				workListenerThread.start();
				threadPool.add(workListenerThread);
				try {
					Thread.sleep(500 + (int) (Math.random() * 500));
				} catch (InterruptedException e) {
					//�򵽿���̨����־�����ⱻ������ҵ����־�����
					e.printStackTrace();
				}
			}
		}
		// ��������״̬Ϊ����
		runbl = true;
		// ��¼��������״̬Ϊ���У�������������ļ������ڣ��������ļ���
		ListenerProperties.recordListenerState(ListenerConstants.LISTENER_STATE_RUNNING,listenerName);
	}

	/**
	 * ֡��Ӧ������
	 * @param args ��������
	 */
	public void start(String className,Integer threadCount,String listenerName,String method,String waitTime) {
		ini(className,threadCount,listenerName,method,waitTime);
		while (runbl) {
			mainSleep(3000);
			if (!ListenerProperties.checkListenerState(listenerName)) {
				log.info("-------------checkListenerState  running  false!");
				for (WorkListenerThread workListenerThread : threadPool) {
					workListenerThread.stopThread();
				}
				boolean isStop = false;
				//���ֹͣ���Ǿ�Ҫ�ȵ�ȫ��ֹͣ���ܽ��������򣬷���һֱѭ���ȴ�
				while (!isStop) {
					isStop = true;
					for (WorkListenerThread WorkListenerThreadThread : threadPool) {
						if (!WorkListenerThreadThread.isStop()) {
							isStop = false;
						}
					}
					mainSleep(2000);
				}
				log.info("-------------WorkListener stopping .....!");
				runbl = false;
			}
		}
		//��¼��������״̬Ϊֹͣ��������������ļ������ڣ��������ļ���
		ListenerProperties.recordListenerState(ListenerConstants.LISTENER_STATE_STOP, listenerName);
		log.info("-------------WorkListener stop!");
	}


}
