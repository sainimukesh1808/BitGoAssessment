package com.code.selenium.utils;

import com.code.selenium.ConfigManager;

public class Constants {
	public static final String QA_BASE_URL = "https://blockstream.info/block/000000000000000000076c036ff5119e5a5a74df77abf64203473364509f7732";
	public static final String SANDBOX_BASE_URL = "SANDBOX";
	public static final String PROD_BASE_URL = "PROD";
	public static String BASE_URL = QA_BASE_URL;
	
	static {
        String activeEnv = ConfigManager.getProperty("active_env"); // Corrected key name
        if ("QA".equalsIgnoreCase(activeEnv)) {
            BASE_URL = QA_BASE_URL;
        } else if ("SANDBOX".equalsIgnoreCase(activeEnv)) {
            BASE_URL = SANDBOX_BASE_URL;
        } else if ("PROD".equalsIgnoreCase(activeEnv)) {
            BASE_URL = PROD_BASE_URL;
        } else {
            BASE_URL = QA_BASE_URL; // Default fallback
        }
    }
	

}
