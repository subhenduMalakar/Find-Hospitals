package com.practo.pages;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class Homepage {
	
	public WebDriver driver;
	public List<String> hospital_list_above;
	public Set<String> windows;
	public Iterator<String> it;
	public String mainpage;
	public String childpage;
	
	
	public Homepage(WebDriver d){
		driver=d;
	}


	String locationXpath="//*[@id=\"c-omni-container\"]/div/div[1]/div[1]/input";
	String BangaloreXpath="//div[text()='Bangalore']";
	
	String searchXpath="//input[@data-qa-id='omni-searchbox-keyword']";
	String HospitalXpath="//div[text()='Hospital']";

	
	String Open_24X7_checkboxXpath="//*[@id=\"container\"]/div[3]/div/div[1]/div/div/header/div[1]/div/div[3]/label/div";

	String all_filtersXpath="//*[@id=\"container\"]/div[3]/div/div[1]/div/div/header/div[1]/div/div[4]/span";
	
	String Has_Parking_checkboxXpath="//*[@id=\"container\"]/div[3]/div/div[1]/div/div/header/div[2]/div/div/div/label[1]/div";

	String ratingXpath="//span[@class='common__star-rating__value']";

	String hospital_namesXpath="//h2[@data-qa-id='hospital_name']";

	String diagnosticsPageLinkXpath="//*[@id=\"container\"]/div[2]/div[1]/div[1]/div[2]/div/div[2]/div[4]/a/div[1]]";

	String providerDropDownXpath="//*[@id=\"container\"]/div[2]/div[1]/div[1]/div[2]/div/div[3]/div[1]/span[2]";

	String Corporate_wellnessXpath="//*[@id=\"container\"]/div[2]/div[1]/div[1]/div[2]/div/div[3]/div[1]/div/div[4]/a";
	
	String validResult_TC_FH_001_xpath="//*[@id=\"container\"]/div[3]/div/div[2]/div[1]/div/div[1]/div[1]/h1";
	
	
	
//	@FindBy(xpath = "//*[@id=\"c-omni-container\"]/div/div[1]/div[1]/input")
//	public WebElement location;
	
	
//	@FindBy(xpath = "//input[@data-qa-id='omni-searchbox-keyword']")
//	public WebElement search;
	
	
//	@FindBy(xpath = "//*[@id=\"container\"]/div[3]/div/div[1]/div/div/header/div[1]/div/div[3]/label/div")
//	public WebElement Open_24X7_checkbox;
	
	
//	@FindBy(xpath = "//*[@id=\"container\"]/div[3]/div/div[1]/div/div/header/div[1]/div/div[4]/span")
//	public WebElement all_filters;
	
	
//	@FindBy(xpath = "//*[@id=\"container\"]/div[3]/div/div[1]/div/div/header/div[2]/div/div/div/label[1]/div")
//	public WebElement Has_Parking_checkbox;
	
	
//	@FindBy(xpath = "//span[@class='common__star-rating__value']")
//	public List<WebElement> rating;
	
	
//	@FindBy(xpath = "//h2[@data-qa-id='hospital_name']")
//	public List<WebElement> hospital_names;
	
	
//	@FindBy(xpath = "//*[@id=\"container\"]/div[2]/div[1]/div[1]/div[2]/div/div[2]/div[4]/a/div[1]]")
//	public WebElement diagnosticsPageLink;
	
	
//	@FindBy(xpath = "//*[@id=\"container\"]/div[2]/div[1]/div[1]/div[2]/div/div[3]/div[1]/span[2]")
//	public WebElement providerDropDown;
	
	
//	@FindBy(xpath = "//*[@id=\"container\"]/div[2]/div[1]/div[1]/div[2]/div/div[3]/div[1]/div/div[4]/a")
//	public WebElement Corporate_wellness;
	
	
	
	public void addLocation(String loc)
	{
		WebElement location=driver.findElement(By.xpath(locationXpath));
		location.clear();
		location.sendKeys(loc);
	}
	
	public void selectDropDownLocation()
	{
		WebElement Bangalore=driver.findElement(By.xpath(BangaloreXpath));
		Bangalore.click();
	}
	
	
	public void addSearch(String sc)
	{
		WebElement search=driver.findElement(By.xpath(searchXpath));
		search.clear();
		search.sendKeys(sc);
	}
	
	public void selectDropDownSearch()
	{
		WebElement Hospital=driver.findElement(By.xpath(HospitalXpath));
		Hospital.click();
	}
	
	
	
	
	public void clickOpen_24X7_checkbox()
	{
		WebElement Open_24X7_checkbox=driver.findElement(By.xpath(Open_24X7_checkboxXpath));
		Open_24X7_checkbox.click();
	}
	
	public void clickAll_filters()
	{
		WebElement all_filters=driver.findElement(By.xpath(all_filtersXpath));

		all_filters.click();
	}
	
	public void clickHas_Parking_checkbox()
	{
		
		WebElement Has_Parking_checkbox=driver.findElement(By.xpath(Has_Parking_checkboxXpath));
		Has_Parking_checkbox.click();
	}
	
	
	public boolean checkValidResult(String loc)
	{
		String validResult=driver.findElement(By.xpath(validResult_TC_FH_001_xpath)).getText();
		String expected="Hospital in "+loc;
		
		System.out.println("valid---->"+validResult);
		System.out.println("expected---->"+expected);
		
		if(validResult.equalsIgnoreCase(expected))
		{
			return true;
		}
		else
		{
			return false;
		}

	}
	
	
	public void clickProviderDropDown()
	{
		WebElement providerDropDown=driver.findElement(By.xpath(providerDropDownXpath));

		providerDropDown.click();
	}
	
	public void clickCorporate_wellness()
	{
		WebElement Corporate_wellness=driver.findElement(By.xpath(Corporate_wellnessXpath));

		Corporate_wellness.click();
	}
	public String getCurrentUrl()
	{
		
		return driver.getCurrentUrl();
	}
	
	
	public List<String> getHospitals() throws InterruptedException
	{
		List<WebElement> rating=driver.findElements(By.xpath(ratingXpath));
		List<WebElement> hospital_names=driver.findElements(By.xpath(hospital_namesXpath));

		int n = rating.size();
		
		for (int i = 0; i < n; i++) 
		{
			String[] ratings = new String[n];
			ratings[i] = rating.get(i).getText();
			double value = Double.parseDouble(ratings[i].replaceAll("[^0-9]", ""));
			if (value > 3.5) 
			{
				Thread.sleep(1000); ////////////////Sleep
				String hospital = hospital_names.get(i).getText();
				hospital_list_above.add(hospital);
			}
		}

		return hospital_list_above;
	}
	
	
	public Diagnostic ClickDiagnostics()
	{
		WebElement diagnosticsPageLink=driver.findElement(By.xpath(diagnosticsPageLinkXpath));

		diagnosticsPageLink.click();
		Diagnostic diagnostics=new Diagnostic(driver);
		PageFactory.initElements(driver,diagnostics);
		return diagnostics;
	}
	
	
	public Corporate ClickCorporate()
	{
		clickProviderDropDown();
		clickCorporate_wellness();
		
		windows = driver.getWindowHandles();
		it = windows.iterator();
		
		mainpage= it.next();
		childpage = it.next();
		
		driver.switchTo().window(childpage);
		
		Corporate corporate=new Corporate(driver);
		PageFactory.initElements(driver,corporate);
		return corporate;
	}
	
}
