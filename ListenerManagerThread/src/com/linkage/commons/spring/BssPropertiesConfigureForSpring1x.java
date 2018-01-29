package com.linkage.commons.spring;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.linkage.commons.util.DESEncryptUtil;
import com.linkage.commons.util.Log;
import com.linkage.commons.util.StringUtil;

/**
 * properites�����ļ��������Լ���/���ܴ���
 * 
 * ����	:	�̳�spring��properties�ļ���ȡ��,����properties�ļ���������Ϣ����/���ܵķ�ʽ

 * �÷� :	
 * 1)�������ݿ�ĵ�¼������properties����������ʽ,ͨ����������������
 * ��Ҫ���Ĵ�ŵ��������Ʋ�������������ǰ�����"BSS_ENCRYPT:"ǰ׺�ķ�ʽ,ʾ������:
 * <property name="password" value="BSS_ENCRYPT:${password}"></property>
 * 2)properties�����ļ��Ķ�ȡ�������µ�����:
 * 	<bean id="propertyConfigurer"
 *		class="com.linkage.commons.spring.BssPropertiesConfigure">
 *		<property name="locations">
 *			<list>
 *				<value>classpath:props/bluePrint.properties</value>
 *			</list>
 *		</property>
 *		<property name="key" value="i am key,let me encrypt you! 1234haha"/>
 *	</bean>
 * 3)�����properties�ļ�����Ҫ���ܵ����Բ������Ĵ��,��:
 *   #password�������Ĵ��
 *   password=uiQlMBLFz5k=
 * ����Ŀ��Բο�bluePrint���̵�bss-BluePrint-spring.db.xml����
 * 
 * @author zhaoxin
 * 
 */
public class BssPropertiesConfigureForSpring1x extends PropertyPlaceholderConfigurer {

	private static Log log = Log.getLog(BssPropertiesConfigureForSpring1x.class);

	private final static String DEFAULT_ENCRYPT_PLACEHOLDER_PREFIX = "BSS_ENCRYPT:";
	private final static String EMPTY_STRING = "";

	//����,������ͬһ����Կ

	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * ���ظ���ĸ÷���,ʵ�ֶԲ��������ļ��е���������ֵ���н��ܴ���
	 * ����spring1.x�����Ե����⴦������������ʱ����Ҫ��spring1.x��jar������
	 * ȥ��spring2.x��jar�������Ұ�parseStringValue()����ע�͵����ܱ���ͨ��
	 * ����ĸ�ʹ��spring1.x�Ĺ��̱����ã�ƽʱ���벻ͨ������ע�͵�!!!!!
	 */
	protected String parseString(Properties props, String strVal, String originalPlaceholder) throws BeansException {

		//����"BSS_ENCRYPT:"��ͷ������,˵�������еļ��ܹ�������,��Ҫ���н��ܴ���
		if (!StringUtil.isEmpty(strVal) && strVal.startsWith(DEFAULT_ENCRYPT_PLACEHOLDER_PREFIX)) {

			strVal = strVal.replaceFirst(DEFAULT_ENCRYPT_PLACEHOLDER_PREFIX, EMPTY_STRING);

//			String value = super.parseString(props, strVal, originalPlaceholder);
			String value = "";// û����ʱע�͵�
			log.debug("spring1.x���еļ�����������, ��������:{},����ֵ:{}", strVal, value);

			return DESEncryptUtil.decrypt(value, getKey());
		} else {
		//	String value = super.parseString(props, strVal, originalPlaceholder);
			String value = "";// û����ʱע�͵�
			return value;
		}
	}
	
}