package com.practo.tests;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
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
	
	@BeforeSuite
	public void getDriver() throws InterruptedException, IOException
	{
		setup=new DriverSetup();
		driver=DriverSetup.getWebDriver();
	}
	
	@Test
	public void demo() throws InterruptedException
	{
		
		Homepage H=new Homepage(driver);
		
		H.addLocation("Bangalore");
		H.addSearch("Hospital");
		
	}
	
	
	@AfterSuite
	public void quitDriver()
	{
//		setup.closeDriver();
	}
	
}
