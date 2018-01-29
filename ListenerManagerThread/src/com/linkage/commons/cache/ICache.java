package com.linkage.commons.cache;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

/**
 * bssϵͳ��װ�ĳ���cache�ӿ�,bssϵͳ����Ҫ�õ�cache�ĵط����ô˽ӿ�
 * ʹ�÷�����ο�test.unit/com.linkage.bss.commons.cache.Demo.java
 * @author zhaoxin
 *
 * @param <K>
 * @param <V>
 */
public interface ICache<K,V>
{
	/**
	 * ��������
	 * @param key
	 * @param value
	 * @return
	 */
	public V put(K key,V value);	
	
	/**
	 * ��������Ч�ڵ�����

	 * @param key
	 * @param value
	 * @param ��Ч��

	 * @return
	 */
	public V put(K key,V value, Date expiry);
	
	/**
	 * ��������Ч�ڵ�����

	 * @param key
	 * @param value
	 * @param ���ݳ�ʱ������

	 * @return
	 */
	public V put(K key,V value, int TTL);
	
	/**
	 * ��ȡ��������
	 * @param key
	 * @return
	 */
	public V get(K key);
	
	/**
	 * �Ƴ���������
	 * @param key
	 * @return
	 */
	public V remove(K key);	
	
	/**
	 * ɾ�����л����ڵ�����

	 * @return
	 */
	public boolean clear();
	
	/**
	 * ������������
	 * @return
	 */
	public int size();
	
	/**
	 * �������е�key�ļ���

	 * @return
	 */
	public Set<K> keySet();
	
	/**
	 * ���������value�ļ���

	 * ������ ������������ʹ��,����ĳ��cache�л����˾޴������,�������������

	 * @return
	 */
	public Collection<V> values();
	
	/**
	 * �Ƿ������ָ��key������

	 * @param key
	 * @return
	 */
	public boolean containsKey(K key);
	
	/**
	 * �ͷ�Cacheռ�õ���Դ

	 */
	public void destroy();
}
