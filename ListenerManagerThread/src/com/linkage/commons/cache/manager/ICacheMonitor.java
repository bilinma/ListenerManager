package com.linkage.commons.cache.manager;
/**
 * 集群环境下的缓存版本监控类
 * @author zhaoxin
 *
 */
public interface ICacheMonitor {
	public void start();
	public void stop();
}
