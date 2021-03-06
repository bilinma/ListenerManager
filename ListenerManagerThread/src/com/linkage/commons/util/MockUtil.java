package com.linkage.commons.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

/**
 * <pre>
 * 使用方式：
 * 1.在需要使用本工具的工程下新建目录 /mockData
 * 2.每个mock对象需要放一个目录，目录的名字为取Mock对象时候用的name名字。
 * 3.Java Mock对象文件命名方式：NAME+"Mock.xml";Json Mock对象文件命名方式：NAME + "Mock.json"
 *   Xml Mock对象文件命名方式：NAME + "XmlMock.xml";TXT Mock对象文件命名方式：NAME + "Mock.txt"
 * @author 汪凡欣
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
	 * 根据要读取的bean的名，构造默认的路径"/mockData/#name/#name+JavaMock.xml"来作为<br>
	 * spring的配置文件路径，并以bean的传入的name作为beanId来读取bean。
	 * @param name 要读取的beanId
	 * @return bean对象
	 * </pre>
	 */
	public static Object getJavaObject(String name) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[] { comboFileName(name, 1) });
		return ctx.getBean(name);
	}

	/**
	 * <pre>
	 * 根据要读取的bean的名，构造默认的路径"/mockData/#name/#name+JavaMock.xml"来作为
	 * spring的配置文件路径，但根据指定的beanId来读取bean。
	 * @param name 文件的名字
	 * @param beanId bean的Id
	 * @return bean对象
	 * </pre>
	 */
	public static Object getJavaObjectById(String name, String beanId) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[] { comboFileName(name, 1) });
		return ctx.getBean(beanId);
	}

	/**
	 * <pre>
	 * 根据指定的文件名，mock类型和mock对象的Id，来获取mock对象。
	 * @param filePath mock数据所在的文件，需要的路径格式如：/mockdata/partyMock.json，也可以是String数组，
	 * 以支持ClassPathXmlApplicationContext方式读取java bean。
	 * @param mockId 指明需要读取的bean的Id。
	 * @return java bean
	 * </pre>
	 */
	public static Object getJavaObjectByPaths(String[] filePaths, String beanId) {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(filePaths);
		return ctx.getBean(beanId);
	}

	/**
	 * <pre>
	 * 根据指定的文件路径，返回这个文件中的数据，适合读取 json,xml,txt格式的mock数据。
	 * @param filePath mock数据所在的文件，需要的路径格式如：/mockdata/party/partyMock.json
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
			throw new MockUtilException("查找文件" + "filePath失败，不能获取Mock对象！", e);
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
