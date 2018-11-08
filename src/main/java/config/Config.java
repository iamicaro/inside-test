package config;

import config.commons.enums.Browser;
import config.dataprovider.bundle.Bundle;
import config.report.Report;
import config.report.ReportPDF;
import config.webdriver.AbstractWebDriver;
import config.webdriver.ExtendedWebDriver;

public class Config {

	private static ExtendedWebDriver extended;
	private static Report report;
	
	public void doConfigWeb() {
		final String navegador = Bundle.getProperties().getProperty("app.browser.driver");
		AbstractWebDriver.createInstance(Browser.getBrowserByDescription(navegador));
		extended = new ExtendedWebDriver(AbstractWebDriver.getInstance());
		report = new ReportPDF();
		report.setWebDriver(AbstractWebDriver.getInstance());
	}
	
	public ExtendedWebDriver getExtendedWebDriver() {
		if (!(extended instanceof ExtendedWebDriver)) {
			doConfigWeb();
		}
		return extended;
	}
	
	public Report getReport() {
		if (!(report instanceof ReportPDF)) {
			report = new ReportPDF();
		}
		return report;
	}

}
