package com.linkage.commons.spring;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.linkage.commons.util.DESEncryptUtil;
import com.linkage.commons.util.Log;
import com.linkage.commons.util.StringUtil;

/**
 * properites配置文件敏感属性加密/解密处理
 * 
 * 机制	:	继承spring的properties文件读取类,采用properties文件中敏感信息加密/解密的方式

 * 用法 :	
 * 1)比如数据库的登录密码在properties中是密文形式,通过这个类解析成明文
 * 需要密文存放的属性名称采用在属性名称前面加上"BSS_ENCRYPT:"前缀的方式,示例如下:
 * <property name="password" value="BSS_ENCRYPT:${password}"></property>
 * 2)properties配置文件的读取采用如下的配置:
 * 	<bean id="propertyConfigurer"
 *		class="com.linkage.commons.spring.BssPropertiesConfigure">
 *		<property name="locations">
 *			<list>
 *				<value>classpath:props/bluePrint.properties</value>
 *			</list>
 *		</property>
 *		<property name="key" value="i am key,let me encrypt you! 1234haha"/>
 *	</bean>
 * 3)具体的properties文件中需要加密的属性采用密文存放,如:
 *   #password采用密文存放
 *   password=uiQlMBLFz5k=
 * 具体的可以参考bluePrint工程的bss-BluePrint-spring.db.xml配置
 * 
 * @author zhaoxin
 * 
 */
public class BssPropertiesConfigureForSpring1x extends PropertyPlaceholderConfigurer {

	private static Log log = Log.getLog(BssPropertiesConfigureForSpring1x.class);

	private final static String DEFAULT_ENCRYPT_PLACEHOLDER_PREFIX = "BSS_ENCRYPT:";
	private final static String EMPTY_STRING = "";

	//加密,解密用同一个密钥

	private String key;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * 重载父类的该方法,实现对部分配置文件中的敏感属性值进行解密处理
	 * 对于spring1.x兼容性的特殊处理，编译此类的时候，需要把spring1.x的jar包引入
	 * 去掉spring2.x的jar包，并且把parseStringValue()方法注释掉才能编译通过
	 * 特殊的给使用spring1.x的工程编译用，平时编译不通过，就注释掉!!!!!
	 */
	protected String parseString(Properties props, String strVal, String originalPlaceholder) throws BeansException {

		//对于"BSS_ENCRYPT:"开头的属性,说明是敏感的加密过的数据,需要进行解密处理
		if (!StringUtil.isEmpty(strVal) && strVal.startsWith(DEFAULT_ENCRYPT_PLACEHOLDER_PREFIX)) {

			strVal = strVal.replaceFirst(DEFAULT_ENCRYPT_PLACEHOLDER_PREFIX, EMPTY_STRING);

//			String value = super.parseString(props, strVal, originalPlaceholder);
			String value = "";// 没用暂时注释掉
			log.debug("spring1.x敏感的加密属性数据, 属性名称:{},属性值:{}", strVal, value);

			return DESEncryptUtil.decrypt(value, getKey());
		} else {
		//	String value = super.parseString(props, strVal, originalPlaceholder);
			String value = "";// 没用暂时注释掉
			return value;
		}
	}
	
}
