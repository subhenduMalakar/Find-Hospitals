package com.practo.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.practo.pages.Corporate;
import com.practo.pages.Diagnostic;
import com.practo.pages.Homepage;
import com.practo.utils.DriverSetup;
import com.practo.utils.ExcelUtils;
import com.practo.utils.getProperties;

public class TestCases {

	//
	public static String baseUrl;
	public static WebDriver driver;
	public static String browserName;
	public DriverSetup setup;
	
	public static Properties prop;
	public static FileInputStream fileInput = null;

	//Annotation to invoke driver
	@BeforeSuite
	public void getDriver() throws InterruptedException, IOException
	{
		//driver setup 
		setup=new DriverSetup();
		driver=DriverSetup.getWebDriver();
		
		//setup and get system properties
		prop=getProperties.getPropertiesData();
	}
	
	//check if application opens in different browser 
	@Test
	public void TC_FH_001()
	{
		Homepage H=new Homepage(driver); //object creation of homepage 
		Assert.assertEquals(H.getCurrentUrl(), prop.getProperty("url")); //check current url with expected url
	}
	
	//check if application 
	@Test(priority=1)
	public void TC_FH_002()
	{
		String Location="Bangalor"; //intentional missing character in location for better suggestion
		String Search="Hospital";
		
		Homepage H=new Homepage(driver);
		H.addLocation(Location);
 		H.selectDropDownLocation();
		H.addSearch(Search);	
		H.selectDropDownSearch();

		Assert.assertEquals(H.checkValidResult("Bangalore"), true);
	}
	
	@Test(priority=2)
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
	
	@Test(priority=3)
	public void TC_FH_004()
	{
		String Location="Bangalor";
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
		
		Assert.assertEquals(H.checkValidResult("Bangalore"), false);	
	}
	
	@Test(priority=4)
	public void TC_FH_005()
	{
		String Location="xyz";
		String InvalidSearch="xyz";

		Homepage H=new Homepage(driver);
		
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
 
	@Test(priority=5)
	public void TC_FH_006()
	{
		String Location="Bangalor";
		String validSearch="Hospital";

		Homepage H=new Homepage(driver);
				
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
		if(H.countOpen_24X7()==10) {
			Assert.assertTrue(true);
		}
		else
			Assert.assertTrue(false);
	}
	
	@Test(priority=6)
	public void TC_FH_007()
	{
		String Location="Bangalor";
		String validSearch="Hospital";

		Homepage H=new Homepage(driver);
				
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
		H.clickAll_filters();
		H.clickHas_Parking_checkbox();
		
		Assert.assertEquals(H.checkValidResult("Bangalore"), true);	
	}
	@Test(priority=7)
	public void TC_FH_008()
		{		
		try {
			Thread.sleep(4000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		String Location="Bangalor";
		String validSearch="Hospital";

		Homepage H=new Homepage(driver);
		
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
		H.clickAll_filters();
		H.clickHas_Parking_checkbox();	
		
		System.out.println("===================Hospital names==================");
		List<String> hospitals=H.getHospitals();
		for(String str:hospitals)
		{
			System.out.println(str);
		}
		ExcelUtils excel=new ExcelUtils();
		try {
			excel.saveToExcel(hospitals,"Hospitals");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test(priority=8)
	public void TC_FH_009()
	{
		Homepage H=new Homepage(driver);
		Diagnostic dia=H.ClickDiagnostics();
		System.out.println("===================Top Cities==================");
		ArrayList<String> list=(ArrayList) dia.getTopCities();
		for(String str:list)
		{
			System.out.println(str);
		}
		
		ExcelUtils excel=new ExcelUtils();
		try {
			excel.saveToExcel(list,"TopCities");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	@Test(priority=9)
	public void TC_FH_010()
	{
		Homepage H=new Homepage(driver);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		H.clickProviderDropDown();
		Assert.assertEquals(H.isPresntCorporate_wellness(), true);
	}
	
	@Test(priority=10)
	public void TC_FH_011()
	{
		Homepage H=new Homepage(driver);
		H.switchCorporate();
		Assert.assertEquals(H.getCurrentUrl(), "https://www.practo.com/plus/corporate");
	}

	
	@Test(priority=11)
	public void TC_FH_012(){
		Homepage H=new Homepage(driver);
		Corporate C=H.switchCorporate();
		C.setName("");
		C.setOrganization_name("");
		C.setOfficial_email_id("");
		C.setOfficial_phone_no("");
		C.clickButton();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(C.isAlertPresent())
		{
			Assert.assertEquals(C.getAlert(),"Please Enter Name");
		}
		else
		{
			Assert.assertTrue(false);
		}
	}

	
	@Test(priority=12)
	public void TC_FH_013(){
		Homepage H=new Homepage(driver);
		Corporate C=H.switchCorporate();
		C.setName("");
		C.setOrganization_name("Cognizant");
		C.setOfficial_email_id("test12@gmail.com");
		C.setOfficial_phone_no("9876543210");
		C.clickButton();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(C.isAlertPresent())
		{
			Assert.assertEquals(C.getAlert(),"Please Enter Name");
		}
		else
		{
			Assert.assertTrue(false);
		}
	}
	
	
	@Test(priority=13)
	public void TC_FH_014(){
		Homepage H=new Homepage(driver);
		Corporate C=H.switchCorporate();
		C.setName("Demo Test");
		C.setOrganization_name("");
		C.setOfficial_email_id("test12@gmail.com");
		C.setOfficial_phone_no("9876543210");
		C.clickButton();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(C.isAlertPresent())
		{
			Assert.assertEquals(C.getAlert(),"Please Enter Organization name");
		}
		else
		{
			Assert.assertTrue(false);
		}
	}
	
	@Test(priority=14)
	public void TC_FH_015(){
		Homepage H=new Homepage(driver);
		Corporate C=H.switchCorporate();
		C.setName("Demo Test");
		C.setOrganization_name("Cognizant");
		C.setOfficial_email_id("");
		C.setOfficial_phone_no("9876543210");
		C.clickButton();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(C.isAlertPresent())
		{
			Assert.assertEquals(C.getAlert(),"Please Enter Official email id");
		}
		else
		{
			Assert.assertTrue(false);
		}
		
	}
	
	@Test(priority=15)
	public void TC_FH_016(){
		Homepage H=new Homepage(driver);
		Corporate C=H.switchCorporate();
		C.setName("Demo Test");
		C.setOrganization_name("Cognizant");
		C.setOfficial_email_id("test12@gmail.com");
		C.setOfficial_phone_no("");
		C.clickButton();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(C.isAlertPresent())
		{
			Assert.assertEquals(C.getAlert(),"Please Enter Official phone no");
		}
		else
		{
			Assert.assertTrue(false);
		}
		
	}
	
	@Test(priority=16)
	public void TC_FH_017(){
		Homepage H=new Homepage(driver);
		Corporate C=H.switchCorporate();
		C.setName("Demo Test");
		C.setOrganization_name("Cognizant");
		C.setOfficial_email_id("test12gmail.com");
		C.setOfficial_phone_no("9876543210");
		C.clickButton();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(C.isAlertPresent())
		{
			Assert.assertEquals(C.getAlert(),"Please enter valid email address");
		}
		else
		{
			Assert.assertTrue(false);
		}	
	}
	@Test(priority=17)
	public void TC_FH_018(){
		Homepage H=new Homepage(driver);
		Corporate C=H.switchCorporate();
		C.setName("Demo Test");
		C.setOrganization_name("Cognizant");
		C.setOfficial_email_id("test12@gmail.com");
		C.setOfficial_phone_no("qdfshkldf");
		C.clickButton();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(C.isAlertPresent())
		{
			Assert.assertEquals(C.getAlert(),"Please enter valid phone no");
		}
		else
		{
			Assert.assertTrue(false);
		}	
	}
	
	
	@Test(priority=18)
	public void TC_FH_019(){
		Homepage H=new Homepage(driver);
		Corporate C=H.switchCorporate();
		C.setName("Demo Test");
		C.setOrganization_name("Cognizant");
		C.setOfficial_email_id("test12@gmail.com");
		C.setOfficial_phone_no("9876543210");
		C.clickButton();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Assert.assertEquals(C.getThankYouMsg(),"Thank You. Our team will get in touch with you shortly.");
	}
	
	@Test(priority=19)
	public void TC_FH_020(){
		Homepage H=new Homepage(driver);
		Corporate C=H.switchCorporate();
		C.setName("@nsu1,");
		C.setOrganization_name("Cognizant");
		C.setOfficial_email_id("test12@gmail.com");
		C.setOfficial_phone_no("9876543210");
		C.clickButton();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(C.isAlertPresent())
		{
			Assert.assertEquals(C.getAlert(),"Please Enter valid name");
		}
		else
		{
			Assert.assertTrue(false);
		}
	}
	
	@Test(priority=20)
	public void TC_FH_021(){
		Homepage H=new Homepage(driver);
		Corporate C=H.switchCorporate();
		C.setName("Demo Test");
		C.setOrganization_name("#$");
		C.setOfficial_email_id("test12@gmail.com");
		C.setOfficial_phone_no("9876543210");
		C.clickButton();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		if(C.isAlertPresent())
		{
			Assert.assertEquals(C.getAlert(),"Please Enter valid organization name");
		}
		else
		{
			Assert.assertTrue(false);
		}
	}
	
	@AfterSuite
	public void quitDriver()
	{
		setup.closeDriver();
	}
	
}
