package com.practo.pages;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class Homepage {
	public WebDriver driver;
	public List<String> hospital_list_above;
	
	public Homepage(WebDriver driver) {
		this.driver=driver;
		
	}
	
	
	@FindBy(xpath = "//*[@id=\"c-omni-container\"]/div/div[1]/div[1]/input")
	public WebElement location;
	
	
	@FindBy(xpath = "//*[@id=\"c-omni-container\"]/div/div[2]/div/input")
	public WebElement search;
	
	
	@FindBy(xpath = "//*[@id=\"container\"]/div[3]/div/div[1]/div/div/header/div[1]/div/div[3]/label/div")
	public WebElement Open_24X7_checkbox;
	
	
	@FindBy(xpath = "//*[@id=\"container\"]/div[3]/div/div[1]/div/div/header/div[1]/div/div[4]/span")
	public WebElement all_filters;
	
	
	@FindBy(xpath = "//*[@id=\"container\"]/div[3]/div/div[1]/div/div/header/div[2]/div/div/div/label[1]/div")
	public WebElement Has_Parking_checkbox;
	
	
	@FindBy(xpath = "//span[@class='common__star-rating__value']")
	public List<WebElement> rating;
	
	
	@FindBy(xpath = "//h2[@data-qa-id='hospital_name']")
	public List<WebElement> hospital_names;
	
	
	@FindBy(xpath = "//*[@id=\"container\"]/div[2]/div[1]/div[1]/div[2]/div/div[2]/div[4]/a/div[1]]")
	public WebElement diagnosticsPageLink;
	
	
	@FindBy(xpath = "//*[@id=\"container\"]/div[2]/div[1]/div[1]/div[2]/div/div[3]/div[1]/span[2]")
	public WebElement providerDropDown;
	
	
	@FindBy(xpath = "//*[@id=\"container\"]/div[2]/div[1]/div[1]/div[2]/div/div[3]/div[1]/div/div[4]/a")
	public WebElement Corporate_wellness;
	
	public void addLocation(String loc)
	{
		location.clear();
		location.sendKeys(loc);
	}
	
	public void addSearch(String sc)
	{
		search.clear();
		search.sendKeys(sc);
	}
	
	public void clickOpen_24X7_checkbox()
	{
		Open_24X7_checkbox.click();
	}
	
	public void clickAll_filters()
	{
		all_filters.click();
	}
	
	public void clickHas_Parking_checkbox()
	{
		Has_Parking_checkbox.click();
	}
	
	
	public void clickProviderDropDown()
	{
		providerDropDown.click();
	}
	
	public void clickCorporate_wellness()
	{
		Corporate_wellness.click();
	}
	
	
	public List<String> getHospitals() throws InterruptedException
	{
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
		diagnosticsPageLink.click();
		Diagnostic diagnostics=new Diagnostic(driver);
		PageFactory.initElements(driver,diagnostics);
		return diagnostics;
	}
	
	
	public Corporate ClickCorporate()
	{
		clickProviderDropDown();
		clickCorporate_wellness();
		Corporate corporate=new Corporate(driver);
		PageFactory.initElements(driver,corporate);
		return corporate;
	}
	
}
