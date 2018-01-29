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
	
	//侦听运行状态
	private boolean runbl = true;
	//侦听的线程池
	private List<WorkListenerThread> threadPool;

	private void mainSleep(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			log.error("线程中断:" + e);
		}
	}


	//侦听的初始化工作
	private void ini(String className,Integer threadCount,String listenerName,String method,String waitTime){
		// 启动的线程数，默认为1个线程
		threadPool = new ArrayList<WorkListenerThread>();
		if (threadCount > 0) {
			for (int i = 0; i < threadCount; i++) {
				log.debug("业务动作接口线程初始化，共有{}个线程，当前是第{}个", threadCount, i + 1);
				WorkListenerThread workListenerThread = new WorkListenerThread(i + 1, threadCount);
				workListenerThread.setThreadObjectName(className);
				workListenerThread.setMethod(method);
				workListenerThread.setWaitTime(waitTime);
				workListenerThread.start();
				threadPool.add(workListenerThread);
				try {
					Thread.sleep(500 + (int) (Math.random() * 500));
				} catch (InterruptedException e) {
					//打到控制台的日志，以免被正常的业务日志给冲掉
					e.printStackTrace();
				}
			}
		}
		// 侦听运行状态为运行
		runbl = true;
		// 记录侦听运行状态为运行（如果侦听属性文件不存在，则生成文件）
		ListenerProperties.recordListenerState(ListenerConstants.LISTENER_STATE_RUNNING,listenerName);
	}

	/**
	 * 帧听应用启动
	 * @param args 启动参数
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
				//如果停止，那就要等到全部停止才能结束主程序，否则一直循环等待
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
		//记录侦听运行状态为停止（如果侦听属性文件不存在，则生成文件）
		ListenerProperties.recordListenerState(ListenerConstants.LISTENER_STATE_STOP, listenerName);
		log.info("-------------WorkListener stop!");
	}


}
