package com.linkage.commons.exception;

/**
 * BMO���׳����쳣��

 * @author zhaoxin
 *
 */
public class BmoException extends BssRuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5319183277545262016L;

	/**
	 * ���췽��

	 * @param result ����ֵ

	 * @param cause  �쳣��ջ
	 */
	public BmoException(Result result, Throwable cause) {
		super(result, cause);
	}

	/**
	 * ���췽��

	 * @param code ������

	 * @param msg  ������Ϣ
	 */
	public BmoException(int code, String msg) {
		super(code,msg);
	}

	/**
	 * ���췽��

	 * @param result ����ֵ

	 * @param detail ����ķ�����Ϣ

	 */
	public BmoException(Result result, String detail) {
		super(result, detail);
	}

	/**
	 * ���췽��

	 * @param result ����ֵ

	 * @param detail ����ķ�����Ϣ

	 * @param cause  �쳣��ջ
	 */
	public BmoException(Result result, String detail, Throwable cause) {
		super(result,detail, cause);
	}

	/**
	 * ���췽��

	 * @param code	������

	 * @param msg	������Ϣ
	 * @param cause �쳣��ջ
	 */
	public BmoException(int code, String msg, Throwable cause) {
		super(code,msg, cause);
	}

	/**
	 * ���췽��

	 * @param code	������

	 * @param cause	�쳣��ջ
	 */
	public BmoException(int code, Throwable cause) {
		super(code,cause);
	}


}
