package com.linkage.commons.cache.manager;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.jdbc.core.JdbcTemplate;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * ibatis��ʹ�õĻ���Ĺ�����,��������ibatis�Ļ����ڼ�Ⱥ�����µİ汾
 * @author zhaoxin
 *
 */
public class IbatisCacheManagerImpl implements ICacheManager {
	private static final String KEY_MEM_CACHE_VERSION = "BSS_IBATIS_CACHE_VERSION";
	private ConcurrentHashMap<Object, Object> versionMap = new ConcurrentHashMap<Object, Object>();
	
	private JdbcTemplate jdbcTemplate;
	private SqlMapClient sqlMapClient;
	private String sqlQueryDbCacheVersion;
	public void init() {
		versionMap.clear();
		setMemCacheVersion(getDBCacheVersion());
	}

	private void setMemCacheVersion(String version) {
		versionMap.put(KEY_MEM_CACHE_VERSION, version);
	}
	public boolean needRefresh() {
		String dbVersion = getDBCacheVersion();
		String memVerison = getMemCacheVersion();
		if (!memVerison.equals(dbVersion)) {
			return true;
		}
		return false;
	}
	public synchronized void refresh() {
		//���ibatis����
		sqlMapClient.flushDataCache();
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

	public SqlMapClient getSqlMapClient() {
		return sqlMapClient;
	}

	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		this.sqlMapClient = sqlMapClient;
	}
}