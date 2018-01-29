package com.linkage.commons.cache;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import com.alisoft.xplatform.asf.cache.IMemcachedCache;

/**
 * 封装了alisoft的memcache客户端

 * 使用示例请查看test.unit/com.linkage.bss.commons.cache.Demo.java
 * @author zhaoxin
 * 
 */
public class AliMemcacheCacheImpl implements ICache<String, Object> {

	private IMemcachedCache cache;

	public IMemcachedCache getCache() {
		return cache;
	}

	public void setCache(IMemcachedCache cache) {
		this.cache = cache;
	}

	public boolean clear() {
		return cache.clear();
	}

	public boolean containsKey(String key) {
		return cache.containsKey(key);
	}

	public void destroy() {
		cache.destroy();

	}

	public Object get(String key) {

		return cache.get(key);
	}

	public Set<String> keySet() {
		return cache.keySet();
	}

	public Object put(String key, Object value, Date expiry) {
		return cache.put(key, value, expiry);
	}

	public Object put(String key, Object value, int timeOutSeconds) {
		return cache.put(key, value, timeOutSeconds);
	}

	public Object put(String key, Object value) {
		return cache.put(key, value);
	}

	public Object remove(String key) {
		return cache.remove(key);
	}

	public int size() {
		return cache.size();
	}

	public Collection<Object> values() {
		return cache.values();
	}

}
