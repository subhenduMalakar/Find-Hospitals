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
import org.openqa.selenium.support.PageFactory;

import com.practo.baseClass.BaseClass;
import com.practo.pages.Diagnostic;
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
	public void TC_FH_001()
	{
		Homepage H=new Homepage(driver);
		Assert.assertEquals(H.getCurrentUrl(), prop.getProperty("url"));
	}
	
//	@Test(priority=1)
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
	
//	@Test(priority=2)
	public void TC_FH_003()
	{
		String InvalidLocation="asdf";
		String Search="Hospital";

		Homepage H=new Homepage(driver);
		String defaultLocation=H.getDefaultLocation();
		
		H.addLocation(InvalidLocation);
		H.addSearch(Search);	
		H.selectDropDownSearch();
		
		Assert.assertEquals(H.checkValidResult(defaultLocation), true);	
	}
	
//	@Test(priority=3)
	public void TC_FH_004()
	{
		String Location="Bangalore";
		String InvalidSearch="xyz";

		Homepage H=new Homepage(driver);		
		H.addLocation(Location);
		H.selectDropDownLocation();
		H.addSearch(InvalidSearch);	
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		H.enterKeyAtSearch();
		
		Assert.assertEquals(H.checkValidResult(Location), false);	
	}
	
//	@Test(priority=4)
	public void TC_FH_005()
	{
		String Location="xyz";
		String InvalidSearch="xyz";

		Homepage H=new Homepage(driver);
		//System.out.println("Default Location================>"+DefaultLocation);
		
		H.addLocation(Location);
		H.addSearch(InvalidSearch);	
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		H.enterKeyAtSearch();
		
		Assert.assertEquals(H.checkValidResult(Location), false);	
	}

 
	//@Test(priority=5)
	public void TC_FH_006()
	{
		String Location="Bangalore";
		String validSearch="Hospital";

		Homepage H=new Homepage(driver);
		//System.out.println("Default Location================>"+DefaultLocation);
		
		H.addLocation(Location);
		H.selectDropDownLocation();
		H.addSearch(validSearch);	
		H.selectDropDownSearch();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		H.clickOpen_24X7_checkbox();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(H.countOpen_24X7()<10) {
			Assert.assertTrue(true);
		}
		else
			Assert.assertTrue(false);
	}
	
	
/*******************************/
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
