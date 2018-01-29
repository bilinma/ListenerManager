package com.linkage.commons.dao.jdbc;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.Assert;

import com.linkage.commons.dao.Page;
import com.linkage.commons.dao.support.OracleDialect;
import com.linkage.commons.util.Log;

/**
 * jdbc�ķ�ҳ��װ��,�ڲ�ʹ����spring��jdbcTemplate
 * ʹ�÷���:
 *   JdbcPageTemplate jdbcPageTemplate = new JdbcPageTemplate(jdbcTemplate);
 *   List<Map<String, Object>> list = queryForPageList(sql, page);
 *   System.out.println("list.size:" + list.size();
 * @author zhaoxin
 *
 */
public class JdbcPageTemplate {
	protected final static Log logger = Log.getLog(JdbcPageTemplate.class);
	/**
	 * The JdbcTemplate we are wrapping
	 */
	private final JdbcOperations classicJdbcTemplate;

	/**
	 * Create a new JdbcPageTemplate for the given {@link DataSource}.
	 * <p>Creates a classic Spring {@link org.springframework.jdbc.core.JdbcTemplate} and wraps it.
	 * @param dataSource the JDBC DataSource to access
	 */
	public JdbcPageTemplate(DataSource dataSource) {
		Assert.notNull(dataSource, "The [dataSource] argument cannot be null.");
		this.classicJdbcTemplate = new JdbcTemplate(dataSource);
	}

	/**
	 * Create a new JdbcPageTemplate for the given classic
	 * Spring {@link org.springframework.jdbc.core.JdbcTemplate}.
	 * @param classicJdbcTemplate the classic Spring JdbcTemplate to wrap
	 */
	public JdbcPageTemplate(JdbcOperations classicJdbcTemplate) {
		Assert.notNull(classicJdbcTemplate, "The [classicJdbcTemplate] argument cannot be null.");
		this.classicJdbcTemplate = classicJdbcTemplate;
	}

	/**
	 * Expose the classic Spring JdbcTemplate to allow invocation of
	 * less commonly used methods.
	 */
	public JdbcOperations getJdbcOperations() {
		return this.classicJdbcTemplate;
	}

	/**
	 * ��ҳ����
	 * @param statementName
	 * @param page
	 * @return
	 */
	public List<Map<String, Object>> queryForPageList(String sql, Page page) throws DataAccessException{
		int index = (page.getCurPage() - 1) * page.getPageSize();
		int size = page.getPageSize();
		return queryForPageList(sql, index, size);
	}
	/**
	 * ��ҳ����
	 * @param sql
	 * @param index ��ʼ��¼��
	 * @param size	ÿҳ��¼��
	 * @return
	 */
	public List<Map<String, Object>> queryForPageList(String sql, int index, int size)throws DataAccessException {
		String pageSql = OracleDialect.getLimitString(sql, true);
		return getJdbcOperations().queryForList(pageSql, new Object[] { index+size, index });
	}

	/**
	 * �������ķ�ҳ����
	 * @param sql
	 * @param page
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> queryForPageList(String sql, Page page, Object[] params)throws DataAccessException {
		int index = (page.getCurPage() - 1) * page.getPageSize();
		int size = page.getPageSize();
		return queryForPageList(sql, index, size, params);
	}
	/**
	 * �������ķ�ҳ����
	 * @param sql	sql���
	 * @param index	��ʼ��¼
	 * @param size	ÿҳ��¼��
	 * @param params	sql�ɱ����
	 * @return
	 */
	public List<Map<String, Object>> queryForPageList(String sql, int index, int size, Object[] params) throws DataAccessException{

		String pageSql = OracleDialect.getLimitString(sql, true);

		//��rownumҲ�����󶨲��������Ƕ�̬SQLƴ�ӣ��������
		int length = 2;
		if (params != null && params.length > 0)
			length += params.length;

		Object[] args = new Object[length];
		if (params != null && params.length > 0)
			System.arraycopy(params, 0, args, 0, params.length);
		args[length - 2] = index + size;
		args[length - 1] = index;

		return getJdbcOperations().queryForList(pageSql, args);
	}
}