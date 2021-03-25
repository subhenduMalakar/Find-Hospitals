package com.practo.utils;
import java.io.File;
import java.io.IOException;
 
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import com.practo.utils.*;

public class Screenshot {	
	public static WebDriver driver;
	public Screenshot(WebDriver d)
	{
		 driver=d;
	}
	
	 public  void  takeScreenshot(String filename) {
		 
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