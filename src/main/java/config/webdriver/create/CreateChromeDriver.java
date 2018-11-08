package config.webdriver.create;

import org.openqa.selenium.Platform;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import config.commons.Constants;

public class CreateChromeDriver {

	public static WebDriver get() {
		System.setProperty(Constants.KEY, Constants.CHROME_DRIVER);
		Proxy proxy = new Proxy();
		proxy.setHttpProxy("http://172.20.202.245:8080");
		DesiredCapabilities caps = DesiredCapabilities.chrome();
		caps.setPlatform(Platform.WINDOWS);
		caps.setCapability(CapabilityType.PROXY, proxy);
		WebDriver webdriver = new ChromeDriver(caps);
		webdriver.manage().window().maximize();
		return webdriver;
	}
	
}
