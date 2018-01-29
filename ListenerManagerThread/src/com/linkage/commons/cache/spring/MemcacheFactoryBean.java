package com.linkage.commons.cache.spring;

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.alisoft.xplatform.asf.cache.ICacheManager;
import com.alisoft.xplatform.asf.cache.IMemcachedCache;
import com.linkage.commons.util.Log;

/**
 * ��alisoft��memcacheʵ�ַ�װΪspring��FactoryBean����,������spring�����ù���

 * 1,��spring������memcache��ʾ������鿴test.unitĿ¼�±�package�µ�bss-common-memcache-demo.xml
 * 2,�ڴ�����ʹ�ô�memcache��鿴test.unitĿ¼�±�packeage�µ�Demo.java
 * @author zhaoxin
 *
 */
public class MemcacheFactoryBean implements FactoryBean, BeanNameAware,
		InitializingBean {

	protected final static Log logger = Log.getLog(MemcacheFactoryBean.class);

	private ICacheManager<IMemcachedCache> cacheManager;
	private String cacheName;
	private String beanName;
	private IMemcachedCache cache;

	public void setCacheManager(ICacheManager<IMemcachedCache> cacheManager) {
		this.cacheManager = cacheManager;
	}

	public void setCacheName(String cacheName) {
		this.cacheName = cacheName;
	}

	public Object getObject() throws Exception {
		return cache;
	}

	public Class getObjectType() {
		return this.cache.getClass();
	}

	public boolean isSingleton() {
		return true;
	}

	public void afterPropertiesSet() throws Exception {
		//û�����ã�����beanName����Ϊcache������

		if (this.cacheName == null) {
			this.cacheName = this.beanName;
		}
		cache = cacheManager.getCache(cacheName);
	}

	public void setBeanName(String name) {
		this.beanName = name;
		
	}
}