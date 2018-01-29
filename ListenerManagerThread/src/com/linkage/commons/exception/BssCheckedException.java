package com.linkage.commons.exception;

import java.io.PrintStream;
import java.io.PrintWriter;

import com.linkage.commons.util.ExceptionUtil;

/**
 * BSS ��check�쳣������, ���е�BSS CHECK����쳣���̳��������

 * @author zhaoxin
 *
 */
public class BssCheckedException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7698665227494141616L;
	private Result result;

	/**
	 * ���췽��

	 * @param result ����ֵ

	 * @param cause  �쳣��ջ
	 */
	public BssCheckedException(Result result, Throwable cause) {
		super(result.getMsg(), cause);
		this.result = result;
	}

	/**
	 * ���췽��

	 * @param code ������

	 * @param msg  ������Ϣ
	 */
	public BssCheckedException(int code, String msg) {
		super(msg);
		this.result = new Result(code, msg);
	}

	/**
	 * ���췽��

	 * @param result ����ֵ

	 * @param detail ����ķ�����Ϣ

	 */
	public BssCheckedException(Result result, String detail) {
		super(result.getMsg() + "," + detail);
		this.result = new Result(result.getCode(), result.getMsg() + "," + detail);
	}

	/**
	 * ���췽��

	 * @param result ����ֵ

	 * @param detail ����ķ�����Ϣ

	 * @param cause  �쳣��ջ
	 */
	public BssCheckedException(Result result, String detail, Throwable cause) {
		super(result.getMsg() + "," + detail, cause);
		this.result = new Result(result.getCode(), result.getMsg() + "," + detail);
	}

	/**
	 * ���췽��

	 * @param code	������

	 * @param msg	������Ϣ
	 * @param cause �쳣��ջ
	 */
	public BssCheckedException(int code, String msg, Throwable cause) {
		super(msg, cause);
		this.result = new Result(code, msg);
	}

	/**
	 * ���췽��

	 * @param code	������

	 * @param cause	�쳣��ջ
	 */
	public BssCheckedException(int code, Throwable cause) {
		super(cause);
		this.result = new Result(code, null);
	}

	/**
	 * �����쳣��Ϣ
	 * @return �쳣��Ϣ
	 */
	@Override
	public String getMessage() {
		return ExceptionUtil.buildMessage(super.getMessage(), getCause());
	}
	/**
	 * �쳣
	 * @return
	 */
	public String toXmlString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<exception>");
		
		if (getResult() != null)
			sb.append(result.toString());

		sb.append("<exceptionTrace>");
		sb.append(getMessage());
		sb.append("</exceptionTrace>");
		
		sb.append("<exception/>");
		return sb.toString();
	}

	@Override
	public void printStackTrace(PrintStream ps) {
		ps.print("<exception>");
		if (getResult() != null) {
			ps.print(result.toString());
		} 
		ps.append("<exceptionTrace>");
		
		Throwable cause = getCause();
		if (cause == null) {
			super.printStackTrace(ps);
		} else {
			ps.println(this);
			ps.print("Caused by: ");
			cause.printStackTrace(ps);
		}
		ps.append("</exceptionTrace>");
		ps.println("</exception>");
	}

	@Override
	public void printStackTrace(PrintWriter pw) {
		pw.print("<exception>");
		if (getResult() != null) {
			pw.print(result.toString());
		} 
		pw.append("<exceptionTrace>");
		
		Throwable cause = getCause();
		if (cause == null) {
			super.printStackTrace(pw);
		} else {
			pw.println(this);
			pw.print("Caused by: ");
			cause.printStackTrace(pw);
		}
		pw.append("</exceptionTrace>");
		pw.println("</exception>");
	}

	/**
	 * �����쳣ֵ

	 * @return	�쳣ֵ����

	 */
	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

}