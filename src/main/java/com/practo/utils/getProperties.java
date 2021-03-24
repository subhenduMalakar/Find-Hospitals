package com.practo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class getProperties {
	public static Properties prop;
	public static FileInputStream fileInput = null;
	public static Properties getPropertiesData()
	{
		
		File file = new File(System.getProperty("user.dir") + "\\config.properties");
		try {
			fileInput = new FileInputStream(file);
		    prop = new Properties();
			prop.load(fileInput);
			fileInput.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	
	return prop;
	}

}

