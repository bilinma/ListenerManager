package com.linkage.commons.dao;

import java.sql.SQLException;

import org.springframework.jdbc.UncategorizedSQLException;

/**
 * һЩdatabase��ʵ�ù�����
 * @author zhaoxin
 *
 */
public class DBUtil {
	
	private static ThreadLocal local = new ThreadLocal();
	/**
	 * �ж��׳����쳣�Ƿ��Ǵ洢���̰�ʧЧ
	 * @param e ���ô洢���̵�ʱ���׳����쳣
	 * @return true��ʾʧЧ�쳣,false��ʾ����ʧЧ�쳣
	 */
	public static boolean isPackageDiscarded(Exception e) {
		return isOraclePackageDiscarded(e);
	}

	/**
	 * �ж��׳����쳣�Ƿ���oracle�洢���̰��Ƿ�ʧЧ
	 * @param e ���ô洢���̵�ʱ���׳����쳣
	 * @return true��ʾʧЧ�쳣,false��ʾ����ʧЧ�쳣
	 */
	private static boolean isOraclePackageDiscarded(Exception e) {
		if (e == null)
			return false;
		//����JDBC��SQLException�쳣
		if (e instanceof SQLException) {
			SQLException e1 = (SQLException) e;
			return isDiscardedSQLException(e1);
		}
		//��������spring�׳��ķ�װ����쳣
		if (e instanceof UncategorizedSQLException) {
			SQLException e1 = ((UncategorizedSQLException) e).getSQLException();
			return isDiscardedSQLException(e1);
		}
		return false;
	}
	/**
	 * SQLStateΪ72000�����쳣����Ϊ4061,4068,6508֮һ���洢����ʧЧ�쳣
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
