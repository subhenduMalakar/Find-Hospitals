package com.practo.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class Diagnostic {
	public WebDriver driver;
	public List<String> topCityList;
	private List<String> topCityList2;
	
	public Diagnostic(WebDriver driver)
	{
		this.driver=driver;
	}
	public List<String> getTopCities()
	{	
			topCityList2 = null;
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			List<WebElement> topcitiesname = driver.findElements(By.xpath("//div[@class='u-margint--standard o-f-color--primary']"));
			for (WebElement cities : topcitiesname)
			{
				String city = cities.getText();
				//topCityList2.add(city);
				System.out.println(city);
			}

		return null;
	}
	
	public Homepage navigateBack() throws InterruptedException
	{
		driver.navigate().back();
		Homepage homepage=new Homepage(driver);
		PageFactory.initElements(driver,homepage);
		return homepage;
	}
	

}
