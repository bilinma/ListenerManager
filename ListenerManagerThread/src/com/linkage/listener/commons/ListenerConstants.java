/*
 * @(#)OrderManagerConstants.java
 */
package com.linkage.listener.commons;

/**
 * ��Ŷ���������Ҫ�õ��ĳ������� 
 * @version 0.5
 * @author 
 */

public class ListenerConstants {
	
	//��������״̬
	public static final String LISTENER_STATE_RUNNING = "running"; //������������
	public static final String LISTENER_STATE_RUNN = "run"; //����
	public static final String LISTENER_STATE_STOPING = "stoping"; //����ֹͣ
	public static final String LISTENER_STATE_STOP = "stop"; //ֹͣ
	
	//֡������
	public static final String PROCESS_O2UMSG_LISTENER_NAME = "ProcessO2UMsgListener";
	public static final String TRANSFEREVENT_LISTENER_NAME = "ArchIntfTransEventListener";
//	public static final String DEALSPFORCRMARCH_LISTENER_NAME="WorkFlowAlarmListener";
	
	//��������
	public static final String CANCLE_ORDER_TYPE_CD = "4"; 
	
	//�����ӿڷ���
	public static final String SP_INTERFACE_METHOD_IOMCANCELORDER= "CrmCancelOrder"; //����
	
	//����usb�Ͷ������ݸ�sp�洢���̷��ؽ��
	public static final String CALL_SEND_TO_SP_PROCEDURE_RESULT_FAIL= "-1"; //�쳣
	public static final String CALL_SEND_TO_SP_PROCEDURE_RESULT_SUCCESS = "0"; //�ɹ�
	
	//od��usb�ӿڱ�״̬
	public static final String O2U_MSG_STATE_FAIL = "F"; //����ʧ��
	public static final String O2U_MSG_STATE_SUCCESS = "C"; //����ɹ�
	public static final String O2U_MSG_STATE_CANCLE = "R"; //������
	public static final String O2U_MSG_STATE_MODIFY = "U"; //���޸�
	
	public static final String CO_RELA_TYPE_CO_MODIFY = "100";//�������޸�
	public static final String CO_RELA_TYPE_CO_CANCEL = "101";//����������
	
	//����ʱ��
	public static final String ORDER_CONTEXT_DEAL_DAY="10";//��Ϣ����ת��ʱ��
}
