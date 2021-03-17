package com.practo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverSetup {
	private static WebDriver driver;
	private static Scanner s;
	static String browserName;
	
	public static WebDriver getWebDriver() throws InterruptedException, IOException {
	
	System.out.println("Enter the choice\n1.Chrome\n2.Firefox");//choose the browser
	s=new Scanner(System.in);
	int d=s.nextInt();
	
		File file = new File(System.getProperty("user.dir") + "\\config.properties");
		FileInputStream fileInput = null;
		fileInput = new FileInputStream(file);
	    Properties prop = new Properties();
		prop.load(fileInput);
		
			// To verify through chrome browser
			if (d==1) {
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\drivers\\chromedriver.exe");
				driver = new ChromeDriver();
				String baseUrl=prop.getProperty("url");//accessing the Url 
			    driver.get(baseUrl);
			}
			// To verify through firefox browser
			else if (d==2) {
				System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"\\drivers\\geckodriver.exe");
				driver = new FirefoxDriver();
				String baseUrl=prop.getProperty("url");//accessing the Url 
			    driver.get(baseUrl);
			}
			// Throws an exception when you enter any browser other than chrome or opera
			else {
				System.out.println("Check the browser name");
			}
			// maximize the window
			driver.manage().window().maximize();

			// Implicit wait for 3 minutes
			driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
	
			return driver;
    
		}
	
	public static void closeDriver()
	{
		driver.quit();
	}

}
