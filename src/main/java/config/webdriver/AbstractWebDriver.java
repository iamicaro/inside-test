package config.webdriver;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import config.commons.enums.Browser;
import config.webdriver.create.CreateChromeDriver;

public class AbstractWebDriver {

	final Logger logger = Logger.getLogger(AbstractWebDriver.class);
	
	private static WebDriver driver;
	
	/**
	 *	Método responsável por carregar o WebDriver.
	 * 
	 * @since 28/12/2017
	 * @author Ícaro Silva
	 * @throws IOException
	 */
	@SuppressWarnings("static-access")
	public static void createInstance(Browser browser) {
 		
		switch (browser) {

		case CHROME:
			driver = new CreateChromeDriver().get();
			break;
		default:
			driver = new CreateChromeDriver().get();
			break;

		}
	}
	
	/**
	 *	Método responsável por disponibilizar o webdriver carregado.
	 * 
	 * @since 28/12/2017
	 * @author Ícaro Silva
	 * @throws IOException
	 */
	public static WebDriver getInstance() {
		if (!(driver instanceof WebDriver)) {
			createInstance(Browser.CHROME);
		}
		return driver;
	}
	
	public static void quit() {
		if (driver instanceof WebDriver) {
			driver.quit();
		}
		
	}
	
	
}
