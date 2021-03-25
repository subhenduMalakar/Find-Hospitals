package com.practo.utils;
import java.io.File;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

public class Screenshot {	
	public static WebDriver driver;
	//To initialize the driver by Constructor calling
	public Screenshot(WebDriver d)
	{
		driver=d;
	}
	// To Take the screenshot
	public  void  takeScreenshot(String filename) {
		// Copy the file to a location and use try catch block to handle exception
		try {
			TakesScreenshot ts=(TakesScreenshot)driver;
			File src=ts.getScreenshotAs(OutputType.FILE);
			FileHandler.copy(src,new File(System.getProperty("user.dir")+"//ScreenShots//"+filename+" "+DateUtil.getTimeStamp()+".png"));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}