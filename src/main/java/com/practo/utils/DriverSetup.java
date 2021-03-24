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

	
		File file = new File(System.getProperty("user.dir") + "\\config.properties");
		FileInputStream fileInput = null;
		fileInput = new FileInputStream(file);
	    Properties prop = new Properties();
		prop.load(fileInput);
		fileInput.close();
		String d=prop.getProperty("browser");
		
			// To verify through chrome browser
			if (d.equalsIgnoreCase("chrome")) {
				System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\drivers\\chromedriver.exe");
				driver = new ChromeDriver();

			}
			// To verify through firefox browser
			else if (d.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+"\\drivers\\geckodriver.exe");
				driver = new FirefoxDriver();

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
