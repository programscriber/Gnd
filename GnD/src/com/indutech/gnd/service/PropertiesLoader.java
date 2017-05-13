package com.indutech.gnd.service;

import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;

public class PropertiesLoader {	
	
	static Logger logger = Logger.getLogger(PropertiesLoader.class);
	
		private static Properties properties;
	 private InputStream inputStream =getClass().getResourceAsStream("/camel-config.properties");
	 private static PropertiesLoader propertiesLoader;
	 
	 public static PropertiesLoader getInstance() {
		 
	     if (propertiesLoader == null)  
	     { 
	    	 logger.info("first time properties loader instance is going to be created");
	    	 propertiesLoader = new  PropertiesLoader();  
	     }  
	     return propertiesLoader;  
     }  
   
    public Properties loadProperties() {
    	
    	try {
    		if(properties == null) {
    			logger.info("first time properties instance is going to be created");
    			properties = new Properties();
    			properties.load(inputStream);
    		}
    		
    	} catch(Exception e) {
    		e.printStackTrace();
    	}
    	 return properties;
    }
}


