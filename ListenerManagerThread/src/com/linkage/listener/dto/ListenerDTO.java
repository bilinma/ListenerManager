/*
 * @(#)ListenerDTO.java
 * 本类创建于2009年9月29日 
 */
package com.linkage.listener.dto;

import java.io.Serializable;

/** 
 * 帧听DTO类. 
 * @version 0.5
 * @author 
 */

public class ListenerDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String threadCount;
	private String description;
	private String className;
	private String state;
	private String method;
	private String waitTime;
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getThreadCount() {
		return threadCount;
	}
	
	public void setThreadCount(String threadCount) {
		this.threadCount = threadCount;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(String waitTime) {
		this.waitTime = waitTime;
	}
}
