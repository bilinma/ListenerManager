package com.linkage.commons.exception;
/**
 * SMO中抛出的异常类

 * @author zhaoxin
 *
 */
public class SmoException extends BssRuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7547091609507916092L;

	/**
	 * 构造方法

	 * @param result 返回值

	 * @param cause  异常堆栈
	 */
	public SmoException(Result result, Throwable cause) {
		super(result, cause);
	}

	/**
	 * 构造方法

	 * @param code 返回码

	 * @param msg  错误消息
	 */
	public SmoException(int code, String msg) {
		super(code,msg);
	}

	/**
	 * 构造方法

	 * @param result 返回值

	 * @param detail 具体的返回消息

	 */
	public SmoException(Result result, String detail) {
		super(result, detail);
	}

	/**
	 * 构造方法

	 * @param result 返回值

	 * @param detail 具体的返回消息

	 * @param cause  异常堆栈
	 */
	public SmoException(Result result, String detail, Throwable cause) {
		super(result,detail, cause);
	}

	/**
	 * 构造方法

	 * @param code	返回码

	 * @param msg	返回消息
	 * @param cause 异常堆栈
	 */
	public SmoException(int code, String msg, Throwable cause) {
		super(code,msg, cause);
	}

	/**
	 * 构造方法

	 * @param code	返回码

	 * @param cause	异常堆栈
	 */
	public SmoException(int code, Throwable cause) {
		super(code,cause);
	}

}
