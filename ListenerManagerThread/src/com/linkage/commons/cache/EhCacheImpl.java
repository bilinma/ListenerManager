package com.linkage.commons.cache;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

/**
 * bss����ģ���Ecache �ķ�װ

 * ehCache ��ʹ����ο�:������test.unitĿ¼�µ�Demo.java�Լ�
 * @author zhaoxin
 *
 */
public class EhCacheImpl implements ICache<Object, Object> {

	private Ehcache cache;

	public Ehcache getCache() {
		return cache;
	}

	public void setCache(Ehcache cache) {
		this.cache = cache;
	}

	public boolean clear() {
		cache.removeAll();
		return true;
	}

	public boolean containsKey(Object key) {
		//isKeyInCacheֻ���ж�key�Ƿ���cache�д���,�����ж�key�Ƿ��Ѿ�������,�ʶ�����getValue�Ƿ�Ϊnull���ж�key�Ƿ����
		//ע�͵� return cache.isKeyInCache(key);
		return this.cache.get(key) != null;
	}

	public void destroy() {
		cache.removeAll();
		cache = null;

	}

	public Object get(Object key) {
		Element element = cache.get(key);
		if (element != null)
			return element.getValue();
		return null;
	}

	public Set<Object> keySet() {
		Set<Object> set = new CopyOnWriteArraySet<Object>();
		set.addAll(cache.getKeys());
		return set;
	}

	public Object put(Object key, Object value, Date expiry) {

		return put(key, value);
	}

	public Object put(Object key, Object value, int timeout) {
		return put(key, value);
	}

	public Object put(Object key, Object value) {
		Element element = new Element(key, value);
		cache.put(element);
		return value;
	}

	public Object remove(Object key) {
		return cache.remove(key);
	}

	public int size() {
		return cache.getSize();
	}

	public Collection<Object> values() {
		List<Object> list = new ArrayList<Object>();
		List keys = cache.getKeys();
		for (Iterator<Object> iter = keys.iterator(); iter.hasNext();) {
			Serializable key = (Serializable) iter.next();
			Element element = cache.get(key);
			if (element != null) {
				list.add(element.getObjectValue());
			}
		}
		return list;
	}
}