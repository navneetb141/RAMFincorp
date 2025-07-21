package com.saucedemo.Test.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class ReadConfig {

	static Properties pro;
	//static Properties prop;
	
	public ReadConfig() {
		File src = new File("./Configuration\\global.properties");
		try {
			FileInputStream fis = new FileInputStream (src);
			pro = new Properties();
			pro.load(fis);
			
		} catch (Exception e) {
			System.out.println("Expection is " + e.getMessage());
		}	
	}
	
	public String openBrowser() {
		String browser = pro.getProperty("browser");
		return browser;
	}
	
	
	public String getApplicationUrl() {
		String url = pro.getProperty("qaAppUrl");
		return url;
	}
	
	public String getUserName() {
		String uid = pro.getProperty("qaUsername");
		return uid;
	}
	
	public String getPassword() {
		String pwd = pro.getProperty("qaPassword");
		return pwd;
	}

}
