package config;

import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import cucumber.api.SnippetType;

@RunWith(Cucumber.class)
@CucumberOptions(monochrome = true, snippets = SnippetType.CAMELCASE,
	plugin = {"json:target/surefire-reports/cucumber.json"},
	features = {".//src//main//resources//features//"},
	tags = "",
	glue = {""})
public class RunTest {
	
}
