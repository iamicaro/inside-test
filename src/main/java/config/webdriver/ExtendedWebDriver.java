package config.webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class ExtendedWebDriver {

	private static WebDriver driver;
	
	public ExtendedWebDriver(WebDriver web) {
		driver = web;
	}
	
	public void goTo(final String url) {
		driver.navigate().to(url);
	}
	
	public void send(final String name, final String value) {
		driver.findElement(By.name(name)).click();
		driver.findElement(By.name(name)).sendKeys(value);
	}
	
	public void keys(final String name, final Keys value) {
		driver.findElement(By.name(name)).click();
		driver.findElement(By.name(name)).sendKeys(value);
	}
	
	public void click(final String xpath) {
		driver.findElement(By.xpath(xpath)).click();
	}
	
	public String text(final String xpath) {
		return driver.findElement(By.xpath(xpath)).getText();
	}
	
	
}
