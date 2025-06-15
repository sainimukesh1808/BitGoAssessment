package com.code.selenium;


import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigManager {
	private static final Logger logger = LogManager.getLogger(ConfigManager.class);
	private static Properties properties = new Properties();
    static {
        try (FileInputStream fis = new FileInputStream("src/resources/config.properties")) {
            properties.load(fis);
        } catch (IOException e) {
        	logger.error("Failed to load configuration file", e);
            e.printStackTrace();
        }
    }
    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
