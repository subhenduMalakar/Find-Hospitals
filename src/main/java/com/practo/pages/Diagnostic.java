package com.practo.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Diagnostic {
	public WebDriver driver;
	public List<String> topCityList;
	
	public Diagnostic(WebDriver driver)
	{
		this.driver=driver;
	}
	
	@FindBy(xpath = "//div[@class='u-margint--standard o-f-color--primary']")
	public List<WebElement> topcities;
	
	
	public List<String> getTopCities()
	{
		for (WebElement tc : topcities) 
		{
			String cityname = tc.getText();
			topCityList.add(cityname);
		}
		return topCityList;
	}
	
	public Homepage navigateBack()
	{
		driver.navigate().back();
		Homepage homepage=new Homepage(driver);
		PageFactory.initElements(driver,homepage);
		return homepage;
	}
	

}
