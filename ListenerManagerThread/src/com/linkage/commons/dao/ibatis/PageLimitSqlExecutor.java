package com.linkage.commons.dao.ibatis;

import java.sql.Connection;
import java.sql.SQLException;

import com.ibatis.sqlmap.engine.execution.SqlExecutor;
import com.ibatis.sqlmap.engine.mapping.statement.RowHandlerCallback;
import com.ibatis.sqlmap.engine.scope.StatementScope;
import com.linkage.commons.dao.support.OracleDialect;

/**
 * ����ibatis��sqlExcutor��Ĭ�ϵķ�ҳʵ�֣���oracle��������ҳ

 * @author zhaoxin
 *
 */
public class PageLimitSqlExecutor extends SqlExecutor {
	/**  
	 * ��дSqlExecutor.executeQuery���� ʵ��ORACLE��SQL������ҳ 
	 * @see com.linkage.bss.commons.dao.Dialect  
	 */
	@Override
	public void executeQuery(StatementScope request, Connection conn,      
            String sql, Object[] parameters, int skipResults, int maxResults,      
            RowHandlerCallback callback) throws SQLException {      
        if (isLimit(sql, skipResults, maxResults)) {// �з�ҳ��Ϣ����������ҳSQL      
            sql = OracleDialect.getLimitString(sql, skipResults, maxResults);// ���������ҳSQL      
            skipResults = NO_SKIPPED_RESULTS;// ����skipResultsΪSqlExecutor����ҳ      
            maxResults = NO_MAXIMUM_RESULTS;// ����maxResultsΪSqlExecutor����ҳ      
        }      
        super.executeQuery(request, conn, sql, parameters, skipResults,      
                maxResults, callback);// ʹ�ò���ҳ���Ƶ���SqlExecutor��ѯ����      
    }      

	 /**    
     * �Ƿ�����ִ�з�ҳ    
     *     
     * @param sql    
     * @param skipResults    
     * @param maxResults    
     * @return    
     */     
    private boolean isLimit(String sql, int skipResults, int maxResults) {      
        return (skipResults != NO_SKIPPED_RESULTS || maxResults != NO_MAXIMUM_RESULTS)      
                && isSelect(sql);      
    }      
     
    /**    
     * �Ƿ��������ҳSQL    
     *     
     * @param sql    
     * @return    
     */     
    private boolean isSelect(String sql) {      
        if (sql.toLowerCase().indexOf("select") >= 0) {      
            return true;      
        }      
        return false;      
    }     

}