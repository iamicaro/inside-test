package config.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ExtendedWebDriver {

	private static WebDriver driver;
	
	public ExtendedWebDriver(WebDriver web) {
		driver = web;
	}
	
	public void goTo(final String url) {
		driver.navigate().to(url);
	}
	
	public void name(final String name, final String value) {
		driver.findElement(By.name(name)).click();
		driver.findElement(By.name(name)).sendKeys(value);
	}
	
	public void xpath(final String xpath, final String value) {
		driver.findElement(By.xpath(xpath)).click();
		driver.findElement(By.xpath(xpath)).sendKeys(value);
	}
	
	public void keys(final String name, final Keys value) {
		driver.findElement(By.name(name)).click();
		driver.findElement(By.name(name)).sendKeys(value);
	}
	
	public void click(final String xpath) {
		driver.findElement(By.xpath(xpath)).click();
	}
	
	public boolean exists(final String xpath) {
		
		try {
			WebDriverWait tempWebDriverWait = new WebDriverWait(driver, 20);
			tempWebDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
			return true;
		} catch (TimeoutException e) {
			return false;
		}

	}
	
	public String text(final String xpath) {
		return driver.findElement(By.xpath(xpath)).getText();
	}
	
	
}
