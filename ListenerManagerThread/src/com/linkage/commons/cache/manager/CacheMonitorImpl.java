package com.linkage.commons.cache.manager;

import java.util.List;

import com.linkage.commons.util.Log;

/**
 * ��������,��webӦ������������.
 * ͨ����ʱ�Ƚ��ڴ滺��汾�������ݿ��л���汾����֤��Ⱥ�����µĻ���һ����
 * @author zhaoxin
 *
 */
public class CacheMonitorImpl implements ICacheMonitor {

	protected static final Log log = Log.getLog(CacheMonitorImpl.class);
	
	//�̼߳�ص�ʱ����,Ĭ��1����
	protected int period = 60000;
	protected volatile boolean stop = false;

	private List<ICacheManager> cacheManagers;
	
	public void start() {
		log.debug(":::����������:::");
		init();
		Thread monitor = new MonitorThread();
		monitor.setDaemon(true);
		monitor.start();
	}
	protected void init(){
		for(ICacheManager manager : cacheManagers){
			manager.init();
		}
	}
	public void stop() {
		stop = true;
		log.debug(":::������ֹͣ:::");
	}

	/**
	 * ������ݿ�Ļ���汾���ڴ滺��汾��һ��,������ˢ�»���
	 */
	protected void monitorCache() {
		for(ICacheManager manager : cacheManagers){
			if(manager.needRefresh()){
				manager.refresh();
			}
		}
	}

	/**
	 * ��ػ�����߳�
	 * @author zhaoxin
	 *
	 */
	public class MonitorThread extends Thread {
		@Override
		public void run() {
			log.debug(":::��ʼ��ػ���:::");
			while (!stop) {
				monitorCache();
				try {
					Thread.sleep(period);
				} catch (InterruptedException e) {
					log.error("���ߴ���:", e);
				}
			}
			log.debug(":::������ػ���:::");
		}
	}


	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public void setCacheManagers(List<ICacheManager> cacheManagers) {
		this.cacheManagers = cacheManagers;
	}

	public List<ICacheManager> getCacheManagers() {
		return cacheManagers;
	}
}