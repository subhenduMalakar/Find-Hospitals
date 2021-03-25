package com.practo.pages;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.practo.utils.*;

public class Corporate {
	public WebDriver driver;
	public Alert alt;
	FileInputStream fileInput=null;
	Properties prop;
	//To initialize the driver by Constructor calling 
	public Corporate(WebDriver driver) {
		this.driver=driver;
		File file = new File(System.getProperty("user.dir") + "\\config.properties");
		try {
			fileInput = new FileInputStream(file);
			prop = new Properties();
			//To load properties File
			prop.load(fileInput);
			fileInput.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	//To find WebElement name
	public void setName(String nam)
	{
		WebElement name=driver.findElement(By.id("name"));
		name.sendKeys(nam);

	}
	//To find WebElement Organization name
	public void setOrganization_name(String org_name)
	{
		WebElement organization_name=driver.findElement(By.id("organization_name"));
		organization_name.sendKeys(org_name);

	}
	//To find WebElement Official Emailid 
	public void setOfficial_email_id(String email_id)
	{

		WebElement official_email_id=driver.findElement(By.id("official_email_id"));
		official_email_id.sendKeys(email_id);
	}

	//To find WebElement Contact Number
	public void setOfficial_phone_no(String phone_no)
	{
		WebElement official_phone_no=driver.findElement(By.id("official_phone_no"));
		official_phone_no.sendKeys(phone_no);
	}
	//To click on Schedule a demo
	public void clickButton()
	{

		WebElement button=driver.findElement(By.id("button-style"));
		button.click();

	}
	//To handle alerts and get alert message
	public String getAlert() 
	{

		Alert alt=driver.switchTo().alert();
		String msg=alt.getText();
		alt.accept();

		return msg;
	}
	//Capture alert message using BufferedImage
	public void captureAlert(String name) throws Exception {
		String screenshot = System.getProperty("user.dir") + ".\\ScreenShots\\" +name+" "+ DateUtil.getTimeStamp() + ".png";
		BufferedImage image = new Robot()
				.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
		ImageIO.write(image, "png", new File(screenshot));
		Thread.sleep(2000);
	}
	//To check whether alert is present
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
	//To schedule a demo by filling all fields with valid data and get Thankyou message
	public String getThankYouMsg()
	{
		WebElement thankYouMsg=driver.findElement(By.id("thankyou-section"));
		String tmsg=thankYouMsg.getText().replace("\n","").trim();
		return tmsg;
	}


}
