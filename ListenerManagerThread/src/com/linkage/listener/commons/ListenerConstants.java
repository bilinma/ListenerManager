/*
 * @(#)OrderManagerConstants.java
 */
package com.linkage.listener.commons;

/**
 * 存放订单调度需要用到的常量数据 
 * @version 0.5
 * @author 
 */

public class ListenerConstants {
	
	//侦听运行状态
	public static final String LISTENER_STATE_RUNNING = "running"; //正在启动运行
	public static final String LISTENER_STATE_RUNN = "run"; //运行
	public static final String LISTENER_STATE_STOPING = "stoping"; //正在停止
	public static final String LISTENER_STATE_STOP = "stop"; //停止
	
	//帧听名称
	public static final String PROCESS_O2UMSG_LISTENER_NAME = "ProcessO2UMsgListener";
	public static final String TRANSFEREVENT_LISTENER_NAME = "ArchIntfTransEventListener";
//	public static final String DEALSPFORCRMARCH_LISTENER_NAME="WorkFlowAlarmListener";
	
	//订单类型
	public static final String CANCLE_ORDER_TYPE_CD = "4"; 
	
	//服开接口方法
	public static final String SP_INTERFACE_METHOD_IOMCANCELORDER= "CrmCancelOrder"; //撤单
	
	//调用usb送订单数据给sp存储过程返回结果
	public static final String CALL_SEND_TO_SP_PROCEDURE_RESULT_FAIL= "-1"; //异常
	public static final String CALL_SEND_TO_SP_PROCEDURE_RESULT_SUCCESS = "0"; //成功
	
	//od送usb接口表状态
	public static final String O2U_MSG_STATE_FAIL = "F"; //处理失败
	public static final String O2U_MSG_STATE_SUCCESS = "C"; //处理成功
	public static final String O2U_MSG_STATE_CANCLE = "R"; //被撤销
	public static final String O2U_MSG_STATE_MODIFY = "U"; //被修改
	
	public static final String CO_RELA_TYPE_CO_MODIFY = "100";//订单被修改
	public static final String CO_RELA_TYPE_CO_CANCEL = "101";//订单被撤消
	
	//处理时间
	public static final String ORDER_CONTEXT_DEAL_DAY="10";//消息备份转储时间
}
