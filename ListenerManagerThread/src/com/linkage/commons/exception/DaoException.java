package com.linkage.commons.exception;

/**
 * DAO���׳����쳣��

 * @author zhaoxin
 *
 */
public class DaoException extends BssRuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6745616593610389157L;

	/**
	 * ���췽��

	 * @param result ����ֵ

	 * @param cause  �쳣��ջ
	 */
	public DaoException(Result result, Throwable cause) {
		super(result, cause);
	}

	/**
	 * ���췽��

	 * @param code ������

	 * @param msg  ������Ϣ
	 */
	public DaoException(int code, String msg) {
		super(code,msg);
	}

	/**
	 * ���췽��

	 * @param result ����ֵ

	 * @param detail ����ķ�����Ϣ

	 */
	public DaoException(Result result, String detail) {
		super(result, detail);
	}

	/**
	 * ���췽��

	 * @param result ����ֵ

	 * @param detail ����ķ�����Ϣ

	 * @param cause  �쳣��ջ
	 */
	public DaoException(Result result, String detail, Throwable cause) {
		super(result,detail, cause);
	}

	/**
	 * ���췽��

	 * @param code	������

	 * @param msg	������Ϣ
	 * @param cause �쳣��ջ
	 */
	public DaoException(int code, String msg, Throwable cause) {
		super(code,msg, cause);
	}

	/**
	 * ���췽��

	 * @param code	������

	 * @param cause	�쳣��ջ
	 */
	public DaoException(int code, Throwable cause) {
		super(code,cause);
	}

}