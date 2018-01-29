package com.linkage.commons.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;


/**
 * 读取跨域URL属性文件,前台页面使用</br>
 * 参加以前的模式不变
 * @version 0.5 
 * @author 魏铁胜 
 */
public class ReadCrossDomainUrl {
	private static final Log log = Log.getLog(ReadCrossDomainUrl.class);

	private static Properties prop;

	public String writeCrossUrlScript() {
		if (prop == null) {
			InputStream input = this.getClass().getClassLoader().getResourceAsStream("crossDomainUrl.properties");
			prop = new Properties();
			try {
				prop.load(input);
			} catch (IOException e) {
				log.error(e.toString());
				return "";
			} catch (Exception e) {
				log.error(e.toString());
				return "";
			} finally {
				try {
					input.close();
				} catch (Exception e) {
					log.error(e.toString());
				}
			}
		}
		Enumeration enum1 = prop.keys();
		StringBuffer buffer = new StringBuffer("");
		buffer.append("<script>\n");
		while (enum1.hasMoreElements()) {
			String key = enum1.nextElement().toString();
			buffer.append("var " + key + "= '" + prop.getProperty(key).trim() + "';\n");
		}
		buffer.append("</script> \n");
		return buffer.toString();
	}

	public void clearCach() {
		prop = null;
	}

	public static void main(String[] args) {
		ReadCrossDomainUrl reader = new ReadCrossDomainUrl();
		System.out.println("str:{}" + reader.writeCrossUrlScript());
	}
}
