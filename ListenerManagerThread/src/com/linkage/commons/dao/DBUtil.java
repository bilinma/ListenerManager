package com.linkage.commons.dao;

import java.sql.SQLException;

import org.springframework.jdbc.UncategorizedSQLException;

/**
 * 一些database的实用工具类
 * @author zhaoxin
 *
 */
public class DBUtil {
	
	private static ThreadLocal local = new ThreadLocal();
	/**
	 * 判断抛出的异常是否是存储过程包失效
	 * @param e 调用存储过程的时候抛出的异常
	 * @return true表示失效异常,false表示不是失效异常
	 */
	public static boolean isPackageDiscarded(Exception e) {
		return isOraclePackageDiscarded(e);
	}

	/**
	 * 判断抛出的异常是否是oracle存储过程包是否失效
	 * @param e 调用存储过程的时候抛出的异常
	 * @return true表示失效异常,false表示不是失效异常
	 */
	private static boolean isOraclePackageDiscarded(Exception e) {
		if (e == null)
			return false;
		//调用JDBC的SQLException异常
		if (e instanceof SQLException) {
			SQLException e1 = (SQLException) e;
			return isDiscardedSQLException(e1);
		}
		//可能是由spring抛出的封装后的异常
		if (e instanceof UncategorizedSQLException) {
			SQLException e1 = ((UncategorizedSQLException) e).getSQLException();
			return isDiscardedSQLException(e1);
		}
		return false;
	}
	/**
	 * SQLState为72000而且异常编码为4061,4068,6508之一，存储过程失效异常
	 * @param e1
	 * @return
	 */
	private static boolean isDiscardedSQLException(SQLException e1) {
		return "72000".equals(e1.getSQLState())
				&& (e1.getErrorCode() == 4061 || e1.getErrorCode() == 4068 || e1.getErrorCode() == 6508);
	}
	
	

	public static void setTargetDataSource(String dataSource) {
		local.set(dataSource);
	}

	public static String getTargetDataSource() {
		return (String) local.get();
	}
}
