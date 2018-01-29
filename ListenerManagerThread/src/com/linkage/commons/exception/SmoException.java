package com.linkage.commons.exception;
/**
 * SMO���׳����쳣��

 * @author zhaoxin
 *
 */
public class SmoException extends BssRuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7547091609507916092L;

	/**
	 * ���췽��

	 * @param result ����ֵ

	 * @param cause  �쳣��ջ
	 */
	public SmoException(Result result, Throwable cause) {
		super(result, cause);
	}

	/**
	 * ���췽��

	 * @param code ������

	 * @param msg  ������Ϣ
	 */
	public SmoException(int code, String msg) {
		super(code,msg);
	}

	/**
	 * ���췽��

	 * @param result ����ֵ

	 * @param detail ����ķ�����Ϣ

	 */
	public SmoException(Result result, String detail) {
		super(result, detail);
	}

	/**
	 * ���췽��

	 * @param result ����ֵ

	 * @param detail ����ķ�����Ϣ

	 * @param cause  �쳣��ջ
	 */
	public SmoException(Result result, String detail, Throwable cause) {
		super(result,detail, cause);
	}

	/**
	 * ���췽��

	 * @param code	������

	 * @param msg	������Ϣ
	 * @param cause �쳣��ջ
	 */
	public SmoException(int code, String msg, Throwable cause) {
		super(code,msg, cause);
	}

	/**
	 * ���췽��

	 * @param code	������

	 * @param cause	�쳣��ջ
	 */
	public SmoException(int code, Throwable cause) {
		super(code,cause);
	}

}