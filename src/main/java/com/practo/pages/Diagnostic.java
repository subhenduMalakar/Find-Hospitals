package com.practo.pages;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import com.practo.utils.getProperties;

public class Diagnostic {
	public WebDriver driver;
	FileInputStream fileInput;
	Properties prop;
	
	//To initialize the driver by Constructor calling
	public Diagnostic(WebDriver driver)
	{
		this.driver=driver;
		
	}
	//Display all top city names
	public List<String> getTopCities()
	{	
		//To store city names in array list and return the list
			ArrayList<String> list=new ArrayList<String>();
			
			prop=getProperties.getPropertiesData();


			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//To find WebElement topcities
			List<WebElement> topcitiesname = driver.findElements(By.xpath(prop.getProperty("TopCities"))); //
			for (WebElement cities : topcitiesname)
			{
				String city = cities.getText();
				list.add(city);
			}

		return list;
	}
	//To navigate back to homepage
	public Homepage navigateBack() throws InterruptedException
	{
		//To navigate back
		driver.navigate().back();
		Homepage homepage=new Homepage(driver);
		PageFactory.initElements(driver,homepage);
		return homepage;
	}
	

}
