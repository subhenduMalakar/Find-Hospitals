package com.practo.utils;

import java.io.File;
import java.io.IOException;
//import org.apache.commons.io.FileUtils; 
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;

public class Screenshot {	
	 public static void  takeScreenshot(WebDriver driver,String filename) throws IOException{
		 TakesScreenshot ts=(TakesScreenshot)driver;
		 File src=ts.getScreenshotAs(OutputType.FILE);
		 FileHandler.copy(src,new File(System.getProperty("user.dir")+"//ScreenShots//"+filename+".png"));
	 }

}
