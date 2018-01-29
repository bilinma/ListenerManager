package com.linkage.commons.dao.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;
import com.linkage.commons.util.ClassUtil;

/**
 * bss封装的ibatis支持类

 * @author zhaoxin
 *
 */
public abstract class IBatisDaoSupport extends SqlMapClientDaoSupport {

	private SqlExecutor sqlExecutor;

	public SqlExecutor getSqlExecutor() {
		return sqlExecutor;
	}

	public void setSqlExecutor(SqlExecutor sqlExecutor) {
		this.sqlExecutor = sqlExecutor;
	}
	/**
	 * 
	 * 初始化的时候注入hack过的sqlExcutor
	 * 在spring中配置此bean的时候增加init-method="init"
	 *
	 */
	public void init() {
		if (sqlExecutor != null) {
			SqlMapClient sqlMapClient = getSqlMapClientTemplate().getSqlMapClient();
			if (sqlMapClient instanceof SqlMapClientImpl) {
				ClassUtil.setFieldValue(((SqlMapClientImpl) sqlMapClient).getDelegate(), "sqlExecutor",
						SqlExecutor.class, sqlExecutor);
			}
		}
	}
	

}
