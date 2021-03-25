package com.practo.tests;

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

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.practo.pages.Corporate;
import com.practo.pages.Diagnostic;
import com.practo.pages.Homepage;
import com.practo.utils.DriverSetup;
import com.practo.utils.ExcelUtils;
import com.practo.utils.getProperties;
import com.practo.utils.ExtentReportManager;
import com.practo.utils.Screenshot;

public class TestCases {

	public static String baseUrl;
	public static WebDriver driver;
	public static String browserName;
	public DriverSetup setup;
	public static ExtentTest logger;
	public static Properties prop;
	public static FileInputStream fileInput = null;
	public ExtentReports report;
	// Annotation to invoke driver
	@BeforeSuite
	public void getDriver() throws InterruptedException, IOException {
		// driver setup
		setup = new DriverSetup();
		driver = DriverSetup.getWebDriver();
		// setup and get system properties
		prop = getProperties.getPropertiesData();
		report = ExtentReportManager.getReportInstance();
	}

	// check if application opens in different browser
	@Test
	public void TC_FH_001() {
		//create logger report
		logger = report.createTest("Launching the website");
		logger.log(Status.INFO, "Opening the Website");

		Homepage H = new Homepage(driver); // object creation of homepage
		Assert.assertEquals(H.getCurrentUrl(), prop.getProperty("url"));// check current url with expected url

		Screenshot screenshot = new Screenshot(H.driver); //Take screenshot of page

		screenshot.takeScreenshot("Open Application");

		logger.log(Status.PASS,prop.getProperty("url") + " launched succesfully"); //log report to check if testcase passed
	}
	// check for application functions with valid location and valid search
	@Test(priority = 1)
	public void TC_FH_002() {

		logger = report.createTest("To display Hospital Names with valid location and valid Search");
		String Location = "Bangalor"; // intentional missing character in location for better suggestion
		String Search = "Hospital";

		//passing inputs and check success status with logger report and taking screenshots
		Homepage H = new Homepage(driver);
		H.addLocation(Location);
		H.selectDropDownLocation();
		H.addSearch(Search);
		H.selectDropDownSearch();
		logger.log(Status.INFO, "Selecting Hospitals in Bangalore");
		try {		
			Screenshot screenshot = new Screenshot(H.driver);
			screenshot.takeScreenshot("Valid Details");
			logger.log(Status.INFO, "Screenshot captured");

			Assert.assertEquals(H.checkValidResult("Bangalore"), true);
			logger.log(Status.PASS, "Success: Selecting Hospitals in Bangalore");
		}catch(Exception e) {
			logger.log(Status.FAIL, "Failure: Not Selecting Hospitals in Bangalore");
			Assert.assertTrue(false);
		}
	}

	// check for application functions with invalid location and valid search
	@Test(priority = 2)
	public void TC_FH_003() {
		logger = report.createTest("To display Hospital Names with invalid location and valid Search");
		String InvalidLocation = "asdf";
		String Search = "Hospital";
		//passing inputs and check success status with logger report
		Homepage H = new Homepage(driver);
		String defaultLocation = H.getDefaultLocation();
		H.addLocation(InvalidLocation);
		H.addSearch(Search);
		H.selectDropDownSearch();
		try{
			logger.log(Status.PASS, "Hospitals in default location are selected");
			Assert.assertEquals(H.checkValidResult(defaultLocation), true);
		}
		catch(Exception e){
			logger.log(Status.FAIL, "Hospitals in default location are not selected");
			Assert.assertTrue(false);
		}
	}

	// check for application functions with valid location and invalid search
	@Test(priority = 3)
	public void TC_FH_004() throws IOException {
		logger = report.createTest("To display Hospital Names with valid location and invalid Search");
		String Location = "Bangalor";
		String InvalidSearch = "xyz";
		//passing inputs and check success status with logger report and taking screenshots
		Homepage H = new Homepage(driver);
		H.addLocation(Location);
		H.selectDropDownLocation();
		H.addSearch(InvalidSearch);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		H.enterKeyAtSearch();  //pressing enter key
		logger.log(Status.INFO, "xyz cannot be selected in Bangalore location");
		try{
			Screenshot screenshot = new Screenshot(H.driver);
			screenshot.takeScreenshot("Invalid Search");
			logger.log(Status.INFO, "Screenshot captured");

			logger.log(Status.PASS, "xyz is not selected in Bangalore location");
			Assert.assertEquals(H.checkValidResult("Bangalore"), false);
		}
		catch(Exception e){
			logger.log(Status.PASS, "xyz is selected in Bangalore location");
			Assert.assertEquals(H.checkValidResult("Bangalore"), true);
		}
	}


	// check for application functions with invalid location and invalid search
	@Test(priority = 4)
	public void TC_FH_005() {
		logger = report.createTest("To display Hospital Names with invalid location and invalid Search");
		String InvalidLocation = "xyz";
		String InvalidSearch = "xyz";
		//passing inputs and check success status with logger report
		Homepage H = new Homepage(driver);

		H.addLocation(InvalidLocation);
		H.addSearch(InvalidSearch);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		H.enterKeyAtSearch();
		try{
			logger.log(Status.PASS, "xyz cannot be selected in xyz location");
			Assert.assertEquals(H.checkValidResult(InvalidLocation), false);
		}
		catch(Exception e){
			logger.log(Status.FAIL, "xyz can be selected in xyz location");
			Assert.assertEquals(H.checkValidResult(InvalidLocation), true);
		}
	}

	// check for application displays hospital names depending on 24*7 checkbox selected
	@Test(priority = 5)
	public void TC_FH_006()

	{
		logger = report.createTest("To select open 24*7 checkbox");
		String Location = "Bangalor";
		String validSearch = "Hospital";
		//passing inputs and check success status with logger report
		Homepage H = new Homepage(driver);

		H.addLocation(Location);
		H.selectDropDownLocation();
		H.addSearch(validSearch);
		H.selectDropDownSearch();
		logger.log(Status.INFO, "Selecting Hospitals in Bangalore");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		H.clickOpen_24X7_checkbox();
		logger.log(Status.INFO, "Select open 24*7 checkbox");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.log(Status.INFO, "Counting Hospitals which are open 24*7");
		if (H.countOpen_24X7() == 10) {
			logger.log(Status.PASS, "Hospitals which are open 24*7 found");
			Assert.assertTrue(true);
		} else
		{
			logger.log(Status.PASS, "Hospitals which are open 24*7 not found");
			Assert.assertTrue(false);
		}
	}

	//checking the functionality of drop down "all filters" and "Has parking" checkbox
	@Test(priority = 6)
	public void TC_FH_007() {
		logger = report.createTest("To select hasparking from all filters dropdown");
		String Location = "Bangalor";
		String validSearch = "Hospital";
		//passing inputs and check success status with logger report
		Homepage H = new Homepage(driver);

		H.addLocation(Location);
		H.selectDropDownLocation();
		H.addSearch(validSearch);
		H.selectDropDownSearch();
		logger.log(Status.INFO, "Selecting Hospitals in Bangalore");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		H.clickOpen_24X7_checkbox();
		logger.log(Status.INFO, "Select open 24*7 checkbox");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		H.clickAll_filters();
		logger.log(Status.INFO, "Select all filters dropdown");
		H.clickHas_Parking_checkbox();
		try{
			logger.log(Status.PASS, "Has parking selected from all filters dropdown in Bangalore");
			Assert.assertEquals(H.checkValidResult("Bangalore"), true);
		}catch(Exception e){
			logger.log(Status.FAIL, "Has parking not selected from all filters dropdown in bangalore");
			Assert.assertEquals(H.checkValidResult("Bangalore"), true);
		}
	}
	//check for selecting hospitals having rating more than 3.5
	@Test(priority = 7)
	public void TC_FH_008() {

		logger = report.createTest("Display Hospital Names having rating more than 3.5");
		String Location = "Bangalor";
		String validSearch = "Hospital";
		//passing inputs and check success status with logger report and taking screenshots
		Homepage H = new Homepage(driver);

		H.addLocation(Location);
		H.selectDropDownLocation();
		H.addSearch(validSearch);
		H.selectDropDownSearch();
		logger.log(Status.INFO, "Selecting Hospitals in Bangalore");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		H.clickOpen_24X7_checkbox();
		logger.log(Status.INFO, "Select open 24*7 checkbox");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		H.clickAll_filters();
		logger.log(Status.INFO, "Select all filters dropdown");
		H.clickHas_Parking_checkbox();
		logger.log(Status.INFO, "Select has parking from all filters dropdown");
		System.out.println("===================Hospital names==================");
		List<String> hospitals = H.getHospitals();

		logger.log(Status.INFO, " Hospital names should be displayed with rating more than 3.5");

		try{
			logger.log(Status.PASS, "Displays all Hopitals name above 3.5 rating");
		}
		catch(Exception e){
			logger.log(Status.FAIL, "Not Displays Hopitals name above 3.5 rating");
		}

		for (String str : hospitals) {
			System.out.println(str);
		}
		//save the data into excel with class ExcelUtils using function saveToExcel
		ExcelUtils excel = new ExcelUtils();
		try {
			excel.saveToExcel(hospitals, "Hospitals");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	//check for the application if displaying all top cities in diagnostics page
	@Test(priority = 8)
	public void TC_FH_009()  {
		logger = report.createTest("Click on Diagnostics page to display top cities");
		//passing inputs and check success status with logger report and taking screenshots
		Homepage H = new Homepage(driver);
		logger.log(Status.INFO, "Select Diagnostics on homepage");
		Diagnostic dia = H.ClickDiagnostics();
		System.out.println("===================Top Cities==================");
		ArrayList<String> list = (ArrayList<String>) dia.getTopCities();
		logger.log(Status.INFO, "Display top cities name");
		Screenshot screenshot = new Screenshot(H.driver);
		screenshot.takeScreenshot("Display Topcities");
		logger.log(Status.INFO, "Screenshot captured");
		try{
			logger.log(Status.PASS, "Displays all TOPCITIES");
		}
		catch(Exception e){
			logger.log(Status.FAIL, "Not Displays all TOPCITIES");
		}
		for (String str : list) {
			System.out.println(str);
		}

		ExcelUtils excel = new ExcelUtils();
		try {
			excel.saveToExcel(list, "TopCities");
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	//check for application's functionality of drop down "For Providers"
	@Test(priority = 9)
	public void TC_FH_010() {
		logger = report.createTest("Click For Providers dropdown on homepage");
		//passing inputs and check success status with logger report
		Homepage H = new Homepage(driver);
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		H.clickProviderDropDown();
		try{
			logger.log(Status.PASS, "Clicked on for providers drop down");
			Assert.assertEquals(H.isPresntCorporate_wellness(), true);
		}
		catch(Exception e){
			logger.log(Status.FAIL, "When For providers drop down is not clicked");
			Assert.assertEquals(H.isPresntCorporate_wellness(), false);
		}
	}

	//verifying application if opens in separate window when Corporate Wellness is selected in homepage
	@Test(priority = 10)
	public void TC_FH_011() {
		logger = report
				.createTest("Select Corporate Wellness from For providers drop down it should navigate to new window");
		//passing inputs and check success status with logger report
		Homepage H = new Homepage(driver);
		H.switchCorporate();
		logger.log(Status.INFO, "Select Corporate Wellness from For providers drop down");
		try {
			Assert.assertEquals(H.getCurrentUrl(), "https://www.practo.com/plus/corporate");
			logger.log(Status.PASS, "URL is correct");
		}
		catch(Exception e)
		{
			logger.log(Status.FAIL, "URL is Not correct: Fail");
			Assert.assertTrue(false);
		}
	}

	//schedule a demo with all null field values in corporate wellness page
	@Test(priority = 11)
	public void TC_FH_012() throws Exception {
		logger = report.createTest("To schedule a demo with all null field values");
		//passing inputs and check success status with logger report
		Homepage H = new Homepage(driver);
		Corporate C = H.switchCorporate();
		C.setName("");
		C.setOrganization_name("");
		C.setOfficial_email_id("");
		C.setOfficial_phone_no("");
		C.clickButton();
		C.captureAlert("Null Fields");
		logger.log(Status.INFO, "Schedule a demo with all null fields");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.log(Status.INFO, "Checking  whether alert is present");
		if (C.isAlertPresent()) {
			logger.log(Status.INFO, "Alert is present");
			String s1=C.getAlert();
			try {
				Assert.assertEquals(s1, "Please Enter Name");
				logger.log(Status.PASS, "Alert Message: "+s1);
			}
			catch(Exception e)
			{
				logger.log(Status.FAIL, "Alert Message: "+s1+" : Not Correct");
				Assert.assertTrue(false);
			}
		} else {
			logger.log(Status.FAIL, "Alert is not present");
			Assert.assertTrue(false);
		}
	}
	//schedule a demo with name field left empty
	@Test(priority = 12)
	public void TC_FH_013() throws Exception {
		logger = report.createTest("To schedule a demo with Name field left empty");
		//passing inputs and check success status with logger report
		Homepage H = new Homepage(driver);
		Corporate C = H.switchCorporate();
		C.setName("");
		C.setOrganization_name("Cognizant");
		C.setOfficial_email_id("test12@gmail.com");
		C.setOfficial_phone_no("9876543210");
		C.clickButton();
		C.captureAlert("Name field empty");
		logger.log(Status.INFO, "Schedule a demo with Name field empty");

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		logger.log(Status.INFO, "Checking  whether alert is present");
		if (C.isAlertPresent()) {
			String s=C.getAlert();
			try {
				Assert.assertEquals(s, "Please Enter Name");
				logger.log(Status.PASS, "Alert Message: "+s);
			}
			catch(Exception e)
			{
				logger.log(Status.FAIL, "Alert Message: "+s+" : Not Correct");
				Assert.assertTrue(false);

			}
		} else {
			logger.log(Status.FAIL, "Alert Error");
			Assert.assertTrue(false);
		}
	}

	//schedule a demo with Organization Name field left empty
	@Test(priority = 13)
	public void TC_FH_014() throws Exception {
		logger = report.createTest("To schedule a demo with Organization Name field left empty");
		//passing inputs and check success status with logger report
		Homepage H = new Homepage(driver);
		Corporate C = H.switchCorporate();
		C.setName("Demo Test");
		C.setOrganization_name("");
		C.setOfficial_email_id("test12@gmail.com");
		C.setOfficial_phone_no("9876543210");
		C.clickButton();
		C.captureAlert("Organization Name field empty");
		logger.log(Status.INFO, "Schedule a demo with Organization Name field empty");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		logger.log(Status.INFO, "Checking  whether alert is present");
		if (C.isAlertPresent()) {
			logger.log(Status.INFO, "Alert is present");
			String s=C.getAlert();
			try {
				Assert.assertEquals(s, "Please Enter Organization name");
				logger.log(Status.PASS, "Alert Message: "+s);
			}
			catch(Exception e)
			{
				logger.log(Status.FAIL, "Alert Message: "+s+" : Not Correct");
				Assert.assertTrue(false);

			}

		} else {
			logger.log(Status.FAIL, "Alert Error");
			Assert.assertTrue(false);
		}

	}
	//schedule a demo with Official emailid field field left empty
	@Test(priority = 14)
	public void TC_FH_015() throws Exception {
		logger = report.createTest("To schedule a demo with Official emailid field left empty");
		//passing inputs and check success status with logger report
		Homepage H = new Homepage(driver);
		Corporate C = H.switchCorporate();
		C.setName("Demo Test");
		C.setOrganization_name("Cognizant");
		C.setOfficial_email_id("");
		C.setOfficial_phone_no("9876543210");
		C.clickButton();
		C.captureAlert("Official emailid empty");
		logger.log(Status.INFO, "Schedule a demo with Official emailid field empty");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		logger.log(Status.INFO, "Checking  whether alert is present");
		if (C.isAlertPresent()) {
			String s=C.getAlert();
			logger.log(Status.INFO, "Alert is present");
			try {
				Assert.assertEquals(s, "Please Enter Official email id");
				logger.log(Status.PASS, "Alert Message: "+s);
			}
			catch(Exception e)
			{
				logger.log(Status.FAIL, "Alert Message: "+s+" : Not Correct");
				Assert.assertTrue(false);

			}

		} else {
			logger.log(Status.FAIL, "Alert Error");
			Assert.assertTrue(false);
		}

	}
	//schedule a demo with Contact Number field left empty
	@Test(priority = 15)
	public void TC_FH_016() throws Exception {
		logger = report.createTest("To schedule a demo with Contact Number field left empty");
		//passing inputs and check success status with logger report
		Homepage H = new Homepage(driver);
		Corporate C = H.switchCorporate();
		C.setName("Demo Test");
		C.setOrganization_name("Cognizant");
		C.setOfficial_email_id("test12@gmail.com");
		C.setOfficial_phone_no("");
		C.clickButton();
		C.captureAlert("Contact Number field empty");
		logger.log(Status.INFO, "Schedule a demo with Contact number field empty");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		logger.log(Status.INFO, "Checking  whether alert is present");
		if (C.isAlertPresent()) {
			String s=C.getAlert();
			logger.log(Status.INFO, "Alert is present");
			try {
				Assert.assertEquals(s, "Please Enter Official phone no");
				logger.log(Status.PASS, "Alert Message: "+s);
			}
			catch(Exception e)
			{
				logger.log(Status.FAIL, "Alert Message: "+s+" : Not Correct");
				Assert.assertTrue(false);
			}

		} else {
			logger.log(Status.FAIL, "Alert Error");
			Assert.assertTrue(false);
		}
	}
	//schedule a demo by giving invalid Official emailid
	@Test(priority = 16)
	public void TC_FH_017() throws Exception {
		logger = report.createTest("To schedule a demo by giving invalid Official emailid");
		//passing inputs and check success status with logger report
		Homepage H = new Homepage(driver);
		Corporate C = H.switchCorporate();
		C.setName("Demo Test");
		C.setOrganization_name("Cognizant");
		C.setOfficial_email_id("test12gmail.com");
		C.setOfficial_phone_no("9876543210");
		C.clickButton();
		C.captureAlert("Invalid Official emailid");
		logger.log(Status.INFO, "Schedule a demo by giving invalid Official emailid");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.log(Status.INFO, "Checking whether alert is present");
		if (C.isAlertPresent()) {
			String s=C.getAlert();
			logger.log(Status.INFO, "Alert is present");
			try {
				Assert.assertEquals(s, "Please enter valid email address");
				logger.log(Status.PASS, "Alert Message: "+s);
			}
			catch(Exception e)
			{
				logger.log(Status.FAIL, "Alert Message: "+s+" : Not Correct");
				Assert.assertTrue(false);
			}

		} else {
			logger.log(Status.FAIL, "Alert Error");
			Assert.assertTrue(false);
		}
	}

	//schedule a demo by giving invalid Contact Number
	@Test(priority = 17)
	public void TC_FH_018() throws Exception {
		logger = report.createTest("To schedule a demo by giving invalid Contact Number");
		//passing inputs and check success status with logger report
		Homepage H = new Homepage(driver);
		Corporate C = H.switchCorporate();
		C.setName("Demo Test");
		C.setOrganization_name("Cognizant");
		C.setOfficial_email_id("test12@gmail.com");
		C.setOfficial_phone_no("qdfshkldf");
		C.clickButton();
		C.captureAlert("Invalid Contact Number");
		logger.log(Status.INFO, "Schedule a demo by giving invalid Contact Number");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		logger.log(Status.INFO, "Checking whether alert is present");
		if (C.isAlertPresent()) {
			String s=C.getAlert();
			logger.log(Status.INFO, "Alert is present");
			try {
				Assert.assertEquals(s, "Please enter valid phone no");
				logger.log(Status.PASS, "Alert Message: "+s);
			}
			catch(Exception e)
			{
				logger.log(Status.FAIL, "Alert Message: "+s+" : Not Correct");
				Assert.assertTrue(false);
			}

		} else {
			logger.log(Status.FAIL, "Alert Error");
			Assert.assertTrue(false);
		}

	}

	//schedule a demo by giving all fields with valid data
	@Test(priority = 18)
	public void TC_FH_019() throws IOException {
		logger = report.createTest("To schedule a demo by giving all fields with valid data");
		//passing inputs and check success status with logger report
		Homepage H = new Homepage(driver);
		Corporate C = H.switchCorporate();
		C.setName("Demo Test");
		C.setOrganization_name("Cognizant");
		C.setOfficial_email_id("test12@gmail.com");
		C.setOfficial_phone_no("9876543210");
		C.clickButton();
		logger.log(Status.INFO, "Schedule a demo by giving all fields with valid data");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		try {
			Screenshot screenshot = new Screenshot(H.driver);
			screenshot.takeScreenshot("Successful Login");
			logger.log(Status.INFO, "Screenshot captured");

			Assert.assertEquals(C.getThankYouMsg(), "Thank You. Our team will get in touch with you shortly.");

			logger.log(Status.PASS, "Succesfully scheduled a demo");
		}
		catch(Exception e)
		{
			logger.log(Status.FAIL, "Fail");
			Assert.assertTrue(false);	
		}
	}

	//To check  if Name field is accepting only alphanumeric characters
	@Test(priority = 19)
	public void TC_FH_020() throws Exception {
		logger = report.createTest("To check whether alert message is shown when name entered is not alphanumeric");
		//passing inputs and check success status with logger report
		Homepage H = new Homepage(driver);
		Corporate C = H.switchCorporate();
		C.setName("@nsu1,");
		C.setOrganization_name("Cognizant");
		C.setOfficial_email_id("test12@gmail.com");
		C.setOfficial_phone_no("9876543210");
		C.clickButton();
		C.captureAlert("Invalid Name");
		logger.log(Status.INFO, "To schedule a demo by giving invalid name");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.log(Status.INFO, "Checking whether alert is present");
		boolean alertPresent=C.isAlertPresent();

		if (alertPresent) {
			String s=C.getAlert();
			Assert.assertEquals(s, "Please Enter valid name");
			logger.log(Status.PASS,"Alert is Present");	
		} else {
			logger.log(Status.FAIL,"'Please Enter valid name' is not shown instead demo is scheduled and thankyou message is shown");
			Assert.assertTrue(false);
		}
	}
	//To check  if Organization Name field is accepting only alphanumeric characters
	@Test(priority = 20)
	public void TC_FH_021() throws Exception {
		logger = report.createTest("To check whether alert message is shown when Organization name entered is not alphanumeric");
		//passing inputs and check success status with logger report
		Homepage H = new Homepage(driver);
		Corporate C = H.switchCorporate();
		C.setName("Demo Test");
		C.setOrganization_name("#$");
		C.setOfficial_email_id("test12@gmail.com");
		C.setOfficial_phone_no("9876543210");
		C.clickButton();
		C.captureAlert("Invalid Organization Name");
		logger.log(Status.INFO, "To schedule a demo by giving invalid Organization name");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger.log(Status.INFO, "Checking whether alert is present");
		boolean alertPresent=C.isAlertPresent();
		if (alertPresent) {
			String s=C.getAlert();
			Assert.assertEquals(s, "Please Enter valid organization name");
			logger.log(Status.PASS,"Alert is Present");
		} else {
			logger.log(Status.FAIL,"'Please Enter valid Organization name' is not shown instead demo is scheduled and thankyou message is shown");
			Assert.assertTrue(false);
		}
	}
	//To close driver and flsuh reports
	@AfterSuite
	public void quitDriver() {
		report.flush();
		DriverSetup.closeDriver();
	}

}
