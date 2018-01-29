package com.linkage.commons.exception;

import java.io.Serializable;

/**
 * BSS����Ĵ��������ϵ

 * @author zhaoxin
 *
 */
public class Result implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6961977579213088716L;

	/**
	 * bssϵͳ���õĴ������,�ɹ���ϵͳ����, 0��-9��ͷ��Ϊ�ڲ���������,������ϵͳ����ʹ��

	 */
	public final static Result BSS_SUCCESS = new Result(0, "�ɹ�");
	public final static Result BSS_SYS_ERROR = new Result(-9999, "ϵͳ����");

	private int code = 0;
	private String msg = "�ɹ�";

	/**
	 * Result���캯��

	 * @param code
	 * @param msg
	 */
	public Result(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Result(Result result) {
		this.code = result.getCode();
		this.msg = result.getMsg();
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * ֻҪ���������ͬ,����Ϊ����������ͬ

	 * @param r
	 * @return
	 */
	@Override
	public boolean equals(Object r) {
		boolean b = false;
		if (r instanceof Result) {
			if (getCode() == ((Result) r).getCode())
				b = true;
			else
				b = false;
		} else
			b = super.equals(r);
		return b;
	}
	@Override
	public int hashCode(){
		return super.hashCode();
	}
	/**
	 * ����Result�����toString�ַ���

	 * @return
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("<code>");
		sb.append(getCode());
		sb.append("</code>");
		sb.append("<msg>");
		sb.append(getMsg());
		sb.append("</msg>");
		return sb.toString();
	}
}
