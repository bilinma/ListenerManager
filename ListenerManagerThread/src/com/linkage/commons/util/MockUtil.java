package com.linkage.commons.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * <pre>
 * ʹ�÷�ʽ��
 * 1.����Ҫʹ�ñ����ߵĹ������½�Ŀ¼ /mockData
 * 2.ÿ��mock������Ҫ��һ��Ŀ¼��Ŀ¼������ΪȡMock����ʱ���õ�name���֡�
 * 3.Java Mock�����ļ�������ʽ��NAME+"Mock.xml";Json Mock�����ļ�������ʽ��NAME + "Mock.json"
 *   Xml Mock�����ļ�������ʽ��NAME + "XmlMock.xml";TXT Mock�����ļ�������ʽ��NAME + "Mock.txt"
 * @author ������
 * </pre>
 *
 */
public class MockUtil {
	private static Log log = Log.getLog(MockUtil.class);
	private static String JAVAFILEPATH = "JavaMock.xml";
	private static String JSONFILEPATH = "Mock.json";
	private static String XMLFILEPATH = "XmlMock.xml";
	private static String TXTFILEPATH = "Mock.txt";

	public static int MOCK_JAVA_BEAN_TYPE = 1;
	public static int MOCK_PLAIN_DATA_TYPE = 2;

	private static String PIX = "/mockdata/";

	/**
	 * <pre>
	 * ����Ҫ��ȡ��bean����������Ĭ�ϵ�·��"/mockData/#name/#name+JavaMock.xml"����Ϊ<br>
	 * spring�������ļ�·��������bean�Ĵ����name��ΪbeanId����ȡbean��
	 * @param name Ҫ��ȡ��beanId
	 * @return bean����
	 * </pre>
	 */
	public static Object getJavaObject(String name) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[] { comboFileName(name, 1) });
		return ctx.getBean(name);
	}

	/**
	 * <pre>
	 * ����Ҫ��ȡ��bean����������Ĭ�ϵ�·��"/mockData/#name/#name+JavaMock.xml"����Ϊ
	 * spring�������ļ�·����������ָ����beanId����ȡbean��
	 * @param name �ļ�������
	 * @param beanId bean��Id
	 * @return bean����
	 * </pre>
	 */
	public static Object getJavaObjectById(String name, String beanId) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[] { comboFileName(name, 1) });
		return ctx.getBean(beanId);
	}

	/**
	 * <pre>
	 * ����ָ�����ļ�����mock���ͺ�mock�����Id������ȡmock����
	 * @param filePath mock�������ڵ��ļ�����Ҫ��·����ʽ�磺/mockdata/partyMock.json��Ҳ������String���飬
	 * ��֧��ClassPathXmlApplicationContext��ʽ��ȡjava bean��
	 * @param mockId ָ����Ҫ��ȡ��bean��Id��
	 * @return java bean
	 * </pre>
	 */
	public static Object getJavaObjectByPaths(String[] filePaths, String beanId) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(filePaths);
		return ctx.getBean(beanId);
	}

	/**
	 * <pre>
	 * ����ָ�����ļ�·������������ļ��е����ݣ��ʺ϶�ȡ json,xml,txt��ʽ��mock���ݡ�
	 * @param filePath mock�������ڵ��ļ�����Ҫ��·����ʽ�磺/mockdata/party/partyMock.json
	 * @return
	 * </pre>
	 */
	public static String getMockDataByPath(String filePath) {
		return getContextFromFile(filePath);
	}

	public static String getJsonObject(String name) {
		return getContextFromFile(comboFileName(name, 2));
	}

	public static String getXmlObject(String name) {
		return getContextFromFile(comboFileName(name, 3));
	}

	public static String getTextObject(String name) {
		return getContextFromFile(comboFileName(name, 4));
	}

	private static String getContextFromFile(String filePath) {
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new ClassPathResource(filePath).getFile()));
			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line).append(System.getProperty("line.separator"));
			}
			return sb.toString();
		} catch (IOException e) {
			log.error(e.getMessage());
			throw new MockUtilException("�����ļ�" + "filePathʧ�ܣ����ܻ�ȡMock����", e);
		}finally{
			if (br != null)
				try {
					br.close();
				} catch (IOException e) {
					log.error(e.getMessage());
				}
		}
	}

	private static String comboFileName(String name, int i) {
		String pix = PIX + name + "/" + name;
		switch (i) {
		case 1:
			return pix + JAVAFILEPATH;
		case 2:
			return pix + JSONFILEPATH;
		case 3:
			return pix + XMLFILEPATH;
		case 4:
			return pix + TXTFILEPATH;
		default:
			return null;
		}
	}
}

@SuppressWarnings("serial")
class MockUtilException extends RuntimeException {
	public MockUtilException(String message) {
		super(message);
	}

	public MockUtilException(String message, Throwable cause) {
		super(message, cause);
	}

	public MockUtilException(Throwable cause) {
		super(cause);
	}
}