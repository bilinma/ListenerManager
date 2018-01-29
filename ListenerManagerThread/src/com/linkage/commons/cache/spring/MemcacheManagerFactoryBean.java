package com.linkage.commons.cache.spring;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.alisoft.xplatform.asf.cache.ICacheManager;
import com.alisoft.xplatform.asf.cache.IMemcachedCache;
import com.alisoft.xplatform.asf.cache.memcached.CacheUtil;
import com.alisoft.xplatform.asf.cache.memcached.MemcachedCacheManager;
import com.linkage.commons.util.Log;

/**
 * ����spring��FactoryBean��װ��alisoft��memcache�ͻ��˵�Manager������

 * �����memcache�ͻ��˽���������ֹͣ�Ȳ���
 * ��spring�е����÷���ʾ��:
 * 1,��spring�����õ�ʾ������鿴test.unitĿ¼�±�package�µ�bss-common-memcache-demo.xml
 * 2,�ڴ�����ʹ�ô���鿴test.unitĿ¼�±�packeage�µ�Demo.java
 * 3,configFile�������û�����ã���Ĭ��Ϊclasspath·���µ�memcached.xml�ļ�
 * 4,memcached.xml������ã���ο�����Ŀ����ַ:http://code.google.com/p/memcache-client-forjava/
 * 
 * @author zhaoxin
 * 
 */
public class MemcacheManagerFactoryBean implements FactoryBean,
		InitializingBean, DisposableBean {

	protected static final Log logger = Log
			.getLog(MemcacheManagerFactoryBean.class);

	private ICacheManager<IMemcachedCache> cacheManager;
	private String configFile;

	public String getConfigFile() {
		return configFile;
	}

	public void setConfigFile(String configFile) {
		this.configFile = configFile;
	}

	public Object getObject() throws Exception {
		return cacheManager;
	}

	public Class getObjectType() {
		return this.cacheManager.getClass();
	}

	public boolean isSingleton() {
		return true;
	}

	public void afterPropertiesSet() throws Exception {
		logger.info("Initializing Memcached CacheManager");
		cacheManager = CacheUtil.getCacheManager(IMemcachedCache.class,
				MemcachedCacheManager.class.getName());
		cacheManager.setConfigFile(getConfigFile());
		cacheManager.start();
	}

	public void destroy() throws Exception {
		logger.info("Shutting down Memcached CacheManager");
		cacheManager.stop();
	}
}