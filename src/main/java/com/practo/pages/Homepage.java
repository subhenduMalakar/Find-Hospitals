package com.practo.pages;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.practo.utils.getProperties;

public class Homepage {

	public WebDriver driver;
	public Set<String> windows;
	public Iterator<String> it;
	public String mainpage;
	public String childpage;
	public static Properties prop;
	public static FileInputStream fileInput;

	// To initialize the driver by Constructor calling
	public Homepage(WebDriver d) {
		driver = d;
		driver.get("https://www.practo.com/");
		prop = getProperties.getPropertiesData();

	}

	// To get default ocation
	public String getDefaultLocation() {
		WebElement location = driver.findElement(By.xpath(prop.getProperty("locationXpath")));
		return location.getAttribute("value");

	}

	// To find WebElement location , clear and fill Location
	public void addLocation(String loc) {
		WebElement location = driver.findElement(By.xpath(prop.getProperty("locationXpath")));
		location.clear();
		location.sendKeys(loc);
	}

	// To select location from the dropdown
	public void selectDropDownLocation() {
		WebElement Bangalore = driver.findElement(By.xpath(prop.getProperty("BangaloreXpath")));
		Bangalore.click();
	}

	// To find WebElement search,clear and fill search
	public void addSearch(String sc) {
		WebElement search = driver.findElement(By.xpath(prop.getProperty("searchXpath")));
		search.clear();
		search.sendKeys(sc);
	}

	// To click enter when location and search are entered
	public void enterKeyAtSearch() {
		WebElement search = driver.findElement(By.xpath(prop.getProperty("searchXpath")));
		search.sendKeys(Keys.ENTER);
	}

	// To select hospitals from the dropdown
	public void selectDropDownSearch() {
		WebElement Hospital = driver.findElement(By.xpath(prop.getProperty("HospitalXpath")));
		Hospital.click();
	}

	// To select Open 24*7 checkbox
	public void clickOpen_24X7_checkbox() {
		WebElement Open_24X7_checkbox = driver.findElement(By.xpath(prop.getProperty("Open_24X7_checkboxXpath")));
		Open_24X7_checkbox.click();
	}

	// To count no of hospitals displayed when Open 24*7 is Selected
	public int countOpen_24X7() {
		List<WebElement> count = driver.findElements(By.xpath(prop.getProperty("countOpen_24X7Xpath")));

		int n = count.size();
		System.out.println("Number of hospitals in first page--> " + n);

		return n;
	}

	// To click on all filters dropdown
	public void clickAll_filters() {
		WebElement all_filters = driver.findElement(By.xpath(prop.getProperty("all_filtersXpath")));

		all_filters.click();
	}

	// To select has parking from all filters dropdown
	public void clickHas_Parking_checkbox() {

		WebElement Has_Parking_checkbox = driver.findElement(By.xpath(prop.getProperty("Has_Parking_checkboxXpath")));
		Has_Parking_checkbox.click();
	}

	// To check whether valid result is displayed when location and search are entered
	public boolean checkValidResult(String loc) {
		try {
			driver.findElement(By.xpath(prop.getProperty("validResult_TC_FH_001_xpath")));
		} catch (Exception e) {
			return false;
		}
		String validResult = driver.findElement(By.xpath(prop.getProperty("validResult_TC_FH_001_xpath"))).getText();

		String expected = "Hospital in " + loc;

		if (validResult.equalsIgnoreCase(expected)) {
			return true;
		} else {
			return false;
		}

	}

	// To click on for providers dropdown
	public void clickProviderDropDown() {
		WebElement providerDropDown = driver.findElement(By.xpath(prop.getProperty("providerDropDownXpath")));

		providerDropDown.click();
	}

	// To check whether Corporate Wellness is present in For providers dropdown
	public boolean isPresntCorporate_wellness() //
	{
		try {
			driver.findElement(By.xpath(prop.getProperty("Corporate_wellnessXpath")));
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	// To select Corporate Wellness from dropdown
	public void clickCorporate_wellness() {
		WebElement Corporate_wellness = driver.findElement(By.xpath(prop.getProperty("Corporate_wellnessXpath")));

		Corporate_wellness.click();
	}

	// To get current url
	public String getCurrentUrl() {

		return driver.getCurrentUrl();
	}

	// TO display hospital names which are open 24*7 , has parking and have rating
	// more than 3.5
	public List<String> getHospitals() {
		ArrayList<String> list = new ArrayList<String>();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//To scroll page by height
		for (int i = 0; i <= 20; i++) {
			JavascriptExecutor js = ((JavascriptExecutor) driver);
			js.executeScript("window.scrollTo(0,document.body.scrollHeight-1000)");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		// taking the entire card containing Hospital names and ratings
		List<WebElement> cards = driver.findElements(By.xpath(prop.getProperty("cardsXpath"))); 
		int n = cards.size();
		for (int i = 0; i < n; i++) {
			WebElement E = cards.get(i);
			String rating;
			// taking innerHTML of different cards and spliting at proper position to get rating
			try {
				rating = E.getAttribute("innerHTML").split("class=\"common__star-rating__value\">")[1]
						.split("</span><span class=\"\">")[0];  
			} catch (Exception e) {
				continue;
			}
			//converting string rating to double 
			double value = Double.parseDouble(rating);
			// compare rating  to get desired output above 3.5
			if (value > 3.5) {
				String hospital_t;
				// taking innerHTML of different cards and spliting at proper position to get Hospital names 
				hospital_t = E.getAttribute("innerHTML").split("class=\"u-title-font u-c-pointer u-bold\">")[1]
						.split("</h2></a><div")[0];
				list.add(hospital_t); // adding Hospital names to a list
			}
		}

		return list;
	}

	// To click on diagnostics page to display top cities
	public Diagnostic ClickDiagnostics() {
		WebElement diagnosticsPageLink = driver.findElement(By.xpath(prop.getProperty("diagnosticsPageLinkXpath")));

		diagnosticsPageLink.click();
		// To navigate to diagnostics class
		Diagnostic diagnostics = new Diagnostic(driver);
		PageFactory.initElements(driver, diagnostics);
		return diagnostics;
	}

	// To switch from parent window to child window
	public Corporate switchCorporate() {
		clickProviderDropDown();
		clickCorporate_wellness();

		windows = driver.getWindowHandles(); //to take all the window handles
		it = windows.iterator();

		mainpage = it.next(); // taking the main or parent window
		childpage = it.next(); //taking the second or child window
		// To close parent window
		driver.close(); 
		driver.switchTo().window(childpage);
		// To navigate to corporate class
		Corporate corporate = new Corporate(driver);
		PageFactory.initElements(driver, corporate);
		return corporate;
	}

}
