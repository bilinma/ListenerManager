package com.linkage.commons.cache.manager;

/**
 * ��������������Խ��л����ˢ�µȹ���
 * @author zhaoxin
 *
 */
public interface ICacheManager {
	public void refresh();
	public boolean needRefresh();
	public void init();
}
