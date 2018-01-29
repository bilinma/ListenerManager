package com.linkage.commons.util;


public class TestLog {
	private static Log logger= Log.getLog(TestLog.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		logger.debug("aaaa");
		logger.error("error "+"aaa",new Exception("bbbbbbbb"));
		logger.error("error ",new Exception("bbbbbbbb"));
		logger.error("error2 {} ","zhaoxin..");
		logger.info("ccccccccc");
		logger.warn("warn");
		logger.trace("trace");
		logger.debug("my name is {}, i like {} very much, i have {} money,i feel {}", "zhaoxin", "money"
				, 500, "happy");
	}

}
