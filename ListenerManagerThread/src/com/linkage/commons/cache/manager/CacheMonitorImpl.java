package com.linkage.commons.cache.manager;

import java.util.List;

import com.linkage.commons.util.Log;

/**
 * 缓存监控类,随web应用启动而启动.
 * 通过定时比较内存缓存版本与在数据库中缓存版本来保证集群环境下的缓存一致性
 * @author zhaoxin
 *
 */
public class CacheMonitorImpl implements ICacheMonitor {

	protected static final Log log = Log.getLog(CacheMonitorImpl.class);
	
	//线程监控的时间间隔,默认1分钟
	protected int period = 60000;
	protected volatile boolean stop = false;

	private List<ICacheManager> cacheManagers;
	
	public void start() {
		log.debug(":::缓存监控启动:::");
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
		log.debug(":::缓存监控停止:::");
	}

	/**
	 * 如果数据库的缓存版本与内存缓存版本不一致,则重新刷新缓存
	 */
	protected void monitorCache() {
		for(ICacheManager manager : cacheManagers){
			if(manager.needRefresh()){
				manager.refresh();
			}
		}
	}

	/**
	 * 监控缓存的线程
	 * @author zhaoxin
	 *
	 */
	public class MonitorThread extends Thread {
		@Override
		public void run() {
			log.debug(":::开始监控缓存:::");
			while (!stop) {
				monitorCache();
				try {
					Thread.sleep(period);
				} catch (InterruptedException e) {
					log.error("休眠错误:", e);
				}
			}
			log.debug(":::结束监控缓存:::");
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
