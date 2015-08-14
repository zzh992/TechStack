package com.techstack.component.logback;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogBackTest {
	
	private static Logger log = LoggerFactory.getLogger(LogBackTest.class); 

	public static void main(String[] args) {
		log.trace("======trace");  
        log.debug("======debug");  
        log.info("======info");  
        log.warn("======warn");  
        log.error("======error");  
	}

}
