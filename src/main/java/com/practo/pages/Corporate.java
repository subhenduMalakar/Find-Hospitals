package com.practo.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class Corporate {
	public WebDriver driver;

	public Corporate(WebDriver driver) {
		this.driver=driver;
	}
	
	
//	@FindBy(id = "name")
//	public WebElement name;
//	
//	@FindBy(id = "organization_name")
//	public WebElement organization_name;
//
//	@FindBy(id = "official_email_id")
//	public WebElement official_email_id;
//	
//	@FindBy(id = "official_phone_no")
//	public WebElement official_phone_no;
//	
//	@FindBy(id = "button-style")
//	public WebElement button;
//
//	@FindBy(id = "thankyou-section")
//	public WebElement thankYouMsg;

	
	public void setName(String nam)
	{
		WebElement name=driver.findElement(By.id("name"));
		name.sendKeys(nam);
	
	}
	
	public void setOrganization_name(String org_name)
	{
		WebElement organization_name=driver.findElement(By.id("organization_name"));
		organization_name.sendKeys(org_name);
	
	}
	
	public void setOfficial_email_id(String email_id)
	{
		
		WebElement official_email_id=driver.findElement(By.id("official_email_id"));
		official_email_id.sendKeys(email_id);
	}
	
	
	public void setOfficial_phone_no(String phone_no)
	{
		WebElement official_phone_no=driver.findElement(By.id("official_phone_no"));
		official_phone_no.sendKeys(phone_no);
	}
	
	public void clickButton()
	{
		
		WebElement button=driver.findElement(By.id("button-style"));
		button.click();
	
	}
	
	public String getAlert()
	{
		
		Alert alt=driver.switchTo().alert();
		String msg=alt.getText();
		alt.accept();
		
		return msg;
	}
	
	public boolean isAlertPresent() 
	{ 
	    try 
	    { 
	        driver.switchTo().alert(); 
	        return true; 
	    } 
	    catch (NoAlertPresentException Ex) 
	    { 
	        return false; 
	    }
	}
	
	public String getThankYouMsg()
	{
		WebElement thankYouMsg=driver.findElement(By.id("thankyou-section"));
		String tmsg=thankYouMsg.getText().replace("\n","").trim();
		return tmsg;
	}


}
