package com.practo.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.practo.pages.Diagnostic;
import com.practo.pages.Homepage;
import com.practo.utils.DriverSetup;

public class TestDiagonsticPage {
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
	

	
	@Test
	public void TC_FH_009()
	{
		Homepage H=new Homepage(driver);
		Diagnostic dia=H.ClickDiagnostics();
		
		System.out.println("-----------------------------");

		System.out.println(dia.getTopCities());

	}
	
	@AfterSuite
	public void quitDriver()
	{
		setup.closeDriver();
	}
	
}
