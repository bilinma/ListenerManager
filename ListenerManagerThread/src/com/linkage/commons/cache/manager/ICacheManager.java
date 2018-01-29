package com.linkage.commons.cache.manager;

/**
 * 缓存管理器，可以进行缓存的刷新等工作
 * @author zhaoxin
 *
 */
public interface ICacheManager {
	public void refresh();
	public boolean needRefresh();
	public void init();
}
