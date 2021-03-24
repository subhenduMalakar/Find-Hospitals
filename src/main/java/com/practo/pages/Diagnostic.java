package com.practo.pages;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
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
	
	
	public Diagnostic(WebDriver driver)
	{
		this.driver=driver;
		
	}
	
	public List<String> getTopCities()
	{	
			ArrayList<String> list=new ArrayList<String>();
			
			prop=getProperties.getPropertiesData();


			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			List<WebElement> topcitiesname = driver.findElements(By.xpath(prop.getProperty("TopCities"))); //
			for (WebElement cities : topcitiesname)
			{
				String city = cities.getText();
				list.add(city);
			}

		return list;
	}
	
	public Homepage navigateBack() throws InterruptedException
	{
		driver.navigate().back();
		Homepage homepage=new Homepage(driver);
		PageFactory.initElements(driver,homepage);
		return homepage;
	}
	

}
