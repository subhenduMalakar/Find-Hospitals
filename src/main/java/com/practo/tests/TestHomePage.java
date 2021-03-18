package com.practo.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.practo.pages.Homepage;
import com.practo.utils.DriverSetup;

public class TestHomePage {
	

	public static String baseUrl;
	public static WebDriver driver;
	public static String browserName;
	public DriverSetup setup;
	
	Properties prop;
	FileInputStream fileInput = null;

	
	@BeforeSuite
	public void getDriver() throws InterruptedException, IOException
	{
		setup=new DriverSetup();
		driver=DriverSetup.getWebDriver();
		
		File file = new File(System.getProperty("user.dir") + "\\config.properties");
		fileInput = new FileInputStream(file);
	    prop = new Properties();
		prop.load(fileInput);
	}
	
//	@Test()
//	public void demo() throws InterruptedException
//	{
//		Homepage H=new Homepage(driver);
//		H.addLocation("Bangalore");
// 		H.selectDropDownLocation()
//		H.addSearch("Hospital");	
//		H.selectDropDownSearch()
//	}
	
	
//	@Test
//	public void TC_FH_001()
//	{
//		Homepage H=new Homepage(driver);
//		Assert.assertEquals(H.getCurrentUrl(), prop.getProperty("url"));
//	}
	
	@Test
	public void TC_FH_002()
	{
		String Location="Bangalore";
		String Search="Hospital";
		
		Homepage H=new Homepage(driver);
		H.addLocation(Location);
 		H.selectDropDownLocation();
		H.addSearch(Search);	
		H.selectDropDownSearch();
		
//		H.clickOpen_24X7_checkbox();
//		try {
//			Thread.sleep(10000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		H.clickAll_filters();
//		H.clickHas_Parking_checkbox();

		
		Assert.assertEquals(H.checkValidResult(Location), true);
	}
	
	

	
	
	
	@AfterSuite
	public void quitDriver()
	{
		setup.closeDriver();
	}
	
}