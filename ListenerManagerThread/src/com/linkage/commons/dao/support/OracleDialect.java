package com.linkage.commons.dao.support;

/**
 * 特定于oracle数据库的一些特性方言，如分页等

 * @author zhaoxin
 *
 */
public class OracleDialect {

	protected static final String SQL_END_DELIMITER = ";";

	public static String getLimitString(String sql, boolean hasOffset) {
		StringBuilder pagingSelect = new StringBuilder( sql.length()+100 );
		if (hasOffset) {
			pagingSelect.append("select * from ( select row_.*, rownum rownum_ from ( ");
		}
		else {
			pagingSelect.append("select * from ( ");
		}
		pagingSelect.append(sql);
		if (hasOffset) {
			pagingSelect.append(" ) row_ where rownum <= ?) where rownum_ > ?");
		}
		else {
			pagingSelect.append(" ) where rownum <= ?");
		}
		return pagingSelect.toString();
	}

	public static String getLimitString(String sql, int offset, int limit) {
		if (offset == 1) {
			offset = 0;
		}
		StringBuffer pageStr = new StringBuffer();
		pageStr.append("select * from ( select row_limit.*, rownum rownum_ from (");
		pageStr.append(OracleDialect.trim(sql));
		pageStr.append(" ) row_limit where rownum <= ");
		pageStr.append(limit + offset);
		pageStr.append(" ) where rownum_ >");
		pageStr.append(offset);
		return pageStr.toString();
	}

	/**    
	 * 去掉当前SQL 后分号    
	 *     
	 * @param sql    
	 * @return    
	 */
	private static String trim(String sql) {
		sql = sql.trim();
		if (sql.endsWith(SQL_END_DELIMITER)) {
			sql = sql.substring(0, sql.length() - 1 - SQL_END_DELIMITER.length());
		}
		return sql;
	}
}
