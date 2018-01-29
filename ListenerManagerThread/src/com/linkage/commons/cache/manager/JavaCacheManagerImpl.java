package com.linkage.commons.cache.manager;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.jdbc.core.JdbcTemplate;

import com.linkage.commons.cache.ICache;
/**
 * JAVA����Ĺ�����,���Թ�����ICache�ӿ�ʵ�ֵĻ�����
 * @author zhaoxin
 *
 */
public class JavaCacheManagerImpl implements ICacheManager {
	private static final String KEY_MEM_CACHE_VERSION = "BSS_JAVA_CACHE_VERSION";
	private String sqlQueryDbCacheVersion;

	private ConcurrentHashMap<Object, Object> versionMap = new ConcurrentHashMap<Object, Object>();

	private JdbcTemplate jdbcTemplate;
	private ICache<Object, Object> cache;
	public void init() {
		versionMap.clear();
		setMemCacheVersion(getDBCacheVersion());
	}

	private void setMemCacheVersion(String version) {
		versionMap.put(KEY_MEM_CACHE_VERSION, version);
	}
	public boolean needRefresh() {
		String dbVersion = getDBCacheVersion();
		String memVersion = getMemCacheVersion();
		//System.out.println("dbVersion:"+dbVersion+",memVersion:"+memVersion);
		if (!memVersion.equals(dbVersion)) {
			return true;
		}
		return false;
	}
	public synchronized void refresh() {
		//���ibatis����
		cache.clear();
		init();
	}
	private String getDBCacheVersion() {
		String version = (String) jdbcTemplate.queryForObject(this.sqlQueryDbCacheVersion, String.class);
		return version;
	}

	private String getMemCacheVersion() {
		String version = (String) versionMap.get(KEY_MEM_CACHE_VERSION);
		return version;
	}

	public String getSqlQueryDbCacheVersion() {
		return sqlQueryDbCacheVersion;
	}

	public void setSqlQueryDbCacheVersion(String sqlQueryDbCacheVersion) {
		this.sqlQueryDbCacheVersion = sqlQueryDbCacheVersion;
	}

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void setCache(ICache<Object, Object> cache) {
		this.cache = cache;
	}

	public ICache<Object, Object> getCache() {
		return cache;
	}
}
