package gerador;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import config.Config;
import config.api.testrail.TestRailClient;
import config.api.testrail.imp.ITestRailClient;
import config.commons.utils.ScenarioCucumberUtils;
import config.webdriver.AbstractWebDriver;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;

public class Hooks {
	
	Config config = new Config();
	TestRailClient testRail = new ITestRailClient();
	
	@Before
	public void initialize(Scenario scenario) throws Exception {
		PropertyConfigurator.configure(Paths.get("").toAbsolutePath().toString() + "\\src\\main\\resources\\log4j.properties");
		Logger.getLogger("org.openqa.selenium.remote").setLevel(Level.OFF);
		
		Thread.currentThread().setName(ScenarioCucumberUtils.getReportName(scenario));
		
		if(ScenarioCucumberUtils.isWeb(scenario)) {
			config.doConfigWeb();
		}
		
		config.getReport().setCover(ScenarioCucumberUtils.getDescriptionFeature(scenario), ScenarioCucumberUtils.getReportName(scenario));
		
	}
	
	@After
	public void finalize(Scenario scenario) throws InterruptedException, IOException {
		
		final boolean status = !scenario.isFailed();
		//testRail.createInstance().setProject("nome-project").setRun("nome-run").setStatus(ScenarioCucumberUtils.getID(scenario), status);
		
		config.getReport().save(status);
		AbstractWebDriver.quit();
	}
	
}
