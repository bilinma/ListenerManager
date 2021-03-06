package com.linkage.commons.dao.ibatis;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.impl.SqlMapClientImpl;
import com.linkage.commons.dao.DBUtil;
import com.linkage.commons.dao.Page;
import com.linkage.commons.util.ClassUtil;
import com.linkage.commons.util.StringUtil;

/**
 * BSS的ibatis的DAO的基类
 * 增加对JTA以及多数据源的支持,为了使用soManger等代码，类名不变
 * @author zhaoxin
 * @author huanghk
 *
 */
public class BaseDAO implements ApplicationContextAware{
	
	SqlMapClientTemplate sqlMapClientTemplate;
	
	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}
	
	public SqlMapClient getSqlMapClient(){
		return getSqlMapClientTemplate().getSqlMapClient();
	}
	
	public SqlMapClientTemplate getSqlMapClientTemplate(){
		String ds = DBUtil.getTargetDataSource();
		if(!StringUtil.isEmpty(ds)){
			sqlMapClientTemplate.setDataSource((DataSource)ctx.getBean(ds));
		}
		return this.sqlMapClientTemplate;
	}
	
	ApplicationContext ctx;
	/**
	 * 分页方法
	 * @param <T>
	 * @param statementName
	 * @param page
	 * @return
	 */
	public <T> List<T> queryForPageList(String statementName, Page page) throws DataAccessException{
		int index = (page.getCurPage() - 1) * page.getPageSize();
		int size = page.getPageSize();
		return queryForPageList(statementName, index, size);
	}
	public <T> List<T> queryForPageList(String statementName, int index, int size) throws DataAccessException{

		return getSqlMapClientTemplate().queryForList(statementName, index, size);
	}
	/**
	 * 带参数的分页方法
	 * @param <T>
	 * @param statementName
	 * @param param
	 * @param page
	 * @return
	 */
	public <T> List<T> queryForPageList(String statementName, Object param, Page page) throws DataAccessException{
		int index = (page.getCurPage() - 1) * page.getPageSize();
		int size = page.getPageSize();
		return this.queryForPageList(statementName, param, index, size);
	}
	
	public <T> List<T> queryForPageList(String statementName, Object param, int index, int size) throws DataAccessException{
		return getSqlMapClientTemplate().queryForList(statementName, param, index, size);
	}

	public void setApplicationContext(ApplicationContext ctx)
			throws BeansException {
		this.ctx = ctx;
	}

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
