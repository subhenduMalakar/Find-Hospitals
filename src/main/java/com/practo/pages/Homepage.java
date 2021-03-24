package com.practo.pages;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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


public class Homepage{
	
	public WebDriver driver;
	public Set<String> windows;
	public Iterator<String> it;
	public String mainpage;
	public String childpage;
	public static Properties prop;
	public static FileInputStream fileInput;

	
	public Homepage(WebDriver d){
		driver=d;
		driver.get("https://www.practo.com/");

		prop=getProperties.getPropertiesData();

	}
		
	public String getDefaultLocation()
	{
		WebElement location=driver.findElement(By.xpath(prop.getProperty("locationXpath")));
		return location.getAttribute("value");
		
	}
	public void addLocation(String loc)
	{
		WebElement location=driver.findElement(By.xpath(prop.getProperty("locationXpath")));
		location.clear();
		location.sendKeys(loc);
	}
	
	public void selectDropDownLocation()
	{
		WebElement Bangalore=driver.findElement(By.xpath(prop.getProperty("BangaloreXpath")));
		Bangalore.click();
	}
	
	
	public void addSearch(String sc)
	{
		WebElement search=driver.findElement(By.xpath(prop.getProperty("searchXpath")));
		search.clear();
		search.sendKeys(sc);
	}
	
	
	public void enterKeyAtSearch()
	{
		WebElement search=driver.findElement(By.xpath(prop.getProperty("searchXpath")));
		search.sendKeys(Keys.ENTER);
	}
	
	public void selectDropDownSearch()
	{
		WebElement Hospital=driver.findElement(By.xpath(prop.getProperty("HospitalXpath")));
		Hospital.click();
	}
	
	public void clickOpen_24X7_checkbox()
	{
		WebElement Open_24X7_checkbox=driver.findElement(By.xpath(prop.getProperty("Open_24X7_checkboxXpath")));
		Open_24X7_checkbox.click();
	}
	
	public int countOpen_24X7()
	{
		List<WebElement> count=driver.findElements(By.xpath(prop.getProperty("countOpen_24X7Xpath")));

		int n = count.size();
		System.out.println("Number of hospitals in first page--> "+n);
		
		return n;
	}
	
	public void clickAll_filters()
	{
		WebElement all_filters=driver.findElement(By.xpath(prop.getProperty("all_filtersXpath")));

		all_filters.click();
	}
	
	public void clickHas_Parking_checkbox()
	{
		
		WebElement Has_Parking_checkbox=driver.findElement(By.xpath(prop.getProperty("Has_Parking_checkboxXpath")));
		Has_Parking_checkbox.click();
	}
	
	
	public boolean checkValidResult(String loc)
	{
		WebElement validR;
		try{
			WebElement validR1=driver.findElement(By.xpath(prop.getProperty("validResult_TC_FH_001_xpath")));
		}
		catch(Exception e)
		{
			return false;
		}
		String validResult=driver.findElement(By.xpath(prop.getProperty("validResult_TC_FH_001_xpath"))).getText();

		String expected="Hospital in "+loc;
		
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
		WebElement providerDropDown=driver.findElement(By.xpath(prop.getProperty("providerDropDownXpath")));

		providerDropDown.click();
	}
	
	public boolean isPresntCorporate_wellness() //
	{
	try {
			driver.findElement(By.xpath(prop.getProperty("Corporate_wellnessXpath")));
			return true;
		}
	catch(Exception e)
		{
			return false;
		}

	}
	
	public void clickCorporate_wellness()
	{
		WebElement Corporate_wellness=driver.findElement(By.xpath(prop.getProperty("Corporate_wellnessXpath")));

		Corporate_wellness.click();
	}
	
	public String getCurrentUrl()
	{
		
		return driver.getCurrentUrl();
	}
	
	
	public List<String> getHospitals() 
	{
		ArrayList<String> list=new ArrayList<String>();

		for(int i=0;i<=20;i++)
		{
			JavascriptExecutor js	=((JavascriptExecutor) driver);
			js.executeScript("window.scrollTo(0,document.body.scrollHeight-1000)");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		List<WebElement> cards = driver.findElements(By.xpath(prop.getProperty("cardsXpath")));
		int n = cards.size();
		//System.out.println("***************Display Hospital Names*********************");
		int cc=0;
		for(int i=0;i<n;i++)
		{
			WebElement E = cards.get(i);
			//System.out.println("***************E"+"--->"+i);
			String rating;
			try {
				//System.out.println("***************Try");
				rating=E.getAttribute("innerHTML").split("class=\"common__star-rating__value\">")[1].split("</span><span class=\"\">")[0];
			}
			catch(Exception e)
			{
				//System.out.println("***************continue");
				continue;
			}
			double value = Double.parseDouble(rating);
			if (value > 3.5)
			{
				//System.out.println("***************If");
				String hospital_t;
				hospital_t=E.getAttribute("innerHTML").split("class=\"u-title-font u-c-pointer u-bold\">")[1].split("</h2></a><div")[0];
				//System.out.println(hospital_t+" - "+value);
				list.add(hospital_t);
				cc++;
			}
		}
		//System.out.println("***************Count"+"--->"+cc);
		
		return list;
}
	
	
	public Diagnostic ClickDiagnostics()
	{
		WebElement diagnosticsPageLink=driver.findElement(By.xpath(prop.getProperty("diagnosticsPageLinkXpath")));

		diagnosticsPageLink.click();
		Diagnostic diagnostics=new Diagnostic(driver);
		PageFactory.initElements(driver,diagnostics);
		return diagnostics;
	}
	
	
	public Corporate switchCorporate() ////Change name 23/03/2021
	{
		clickProviderDropDown(); 
		clickCorporate_wellness(); 
		
		windows = driver.getWindowHandles();
		it = windows.iterator();
		
		mainpage= it.next();
		childpage = it.next();
		
		driver.close();
		driver.switchTo().window(childpage);
		
		Corporate corporate=new Corporate(driver);
		PageFactory.initElements(driver,corporate);
		return corporate;
	}
	
}
