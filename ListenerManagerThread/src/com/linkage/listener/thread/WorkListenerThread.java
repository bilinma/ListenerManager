package com.linkage.listener.thread;

import com.linkage.commons.util.Log;

/**
 * 
 * @author maxb
 *
 */
public class WorkListenerThread extends Thread{
	private static Log log = Log.getLog(WorkListenerThread.class);

	private int threadIndex; //当前线程索引
	private int threadCount; //当前线程总数
	private boolean runbl; //线程控制运行标识
	private boolean isStop; //当前子线程运行状态标识
	private RunThread runThread;
	private String threadObjectName;
	private String method;
	private String waitTime;
	/**
	 * @param arg0
	 */
	public WorkListenerThread(Runnable arg0) {
		super(arg0);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public WorkListenerThread(ThreadGroup arg0, Runnable arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public WorkListenerThread(Runnable arg0, String arg1) {
		super(arg0, arg1);
	}

	/**
	 * @param arg0
	 * @param arg1
	 * @param arg2
	 */
	public WorkListenerThread(ThreadGroup arg0, Runnable arg1, String arg2) {
		super(arg0, arg1, arg2);
	}

	/**
	 * 待线程标识的构造
	 * @param pthreadIndex
	 * @param pthreadCount
	 */
	public WorkListenerThread(int pthreadIndex, int pthreadCount) {
		super();
		threadIndex = pthreadIndex;
		threadCount = pthreadCount;
	}

	public RunThread getRunThread() {
		return runThread;
	}

	public void setRunThread(RunThread runThread) {
		this.runThread = runThread;
	}

	public int getThreadIndex() {
		return threadIndex;
	}

	public void setThreadIndex(int threadIndex) {
		this.threadIndex = threadIndex;
	}

	public int getThreadCount() {
		return threadCount;
	}

	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}

	public String getThreadObjectName() {
		return threadObjectName;
	}

	public void setThreadObjectName(String threadObjectName) {
		this.threadObjectName = threadObjectName;
	}

	public String getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(String waitTime) {
		this.waitTime = waitTime;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void stopThread() {
		runbl = false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Thread#start()
	 */
	public synchronized void start() {
		runbl = true;
		super.start();
	}

	/**
	 * 当前子线程是否在运行
	 * @return
	 */
	public boolean isStop() {
		return isStop;
	}

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public synchronized void run() {
		isStop = false;
		while (runbl) {
			log.debug("----------------threadIndex：" + threadIndex + " | threadCount :" + threadCount + " 处理数据！");
			try {
				Class destClass =  Class.forName(threadObjectName);
				if(destClass.newInstance() instanceof RunThread){
					log.debug("-------------invoke start");
					Class<RunThread> runThread = destClass;
					runThread.newInstance().runWork();
				}
				//每隔一定时间执行一次
				sleep(Long.parseLong(waitTime)*1000);
			} catch (Exception e) {
				log.error("发送服开接口表信息转储异常："+e.getMessage());
				e.printStackTrace();
			}
		}
		isStop = true;
	}
	
}
