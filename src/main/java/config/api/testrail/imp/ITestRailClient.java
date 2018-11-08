package config.api.testrail.imp;

import java.util.List;

import com.codepine.api.testrail.TestRail;
import com.codepine.api.testrail.model.Project;
import com.codepine.api.testrail.model.Result;
import com.codepine.api.testrail.model.ResultField;
import com.codepine.api.testrail.model.Run;

import config.api.testrail.TestRailClient;
import config.dataprovider.bundle.Bundle;

public class ITestRailClient implements TestRailClient {

	private static TestRail instance;
	private int projectId;
	private int runId;
	
	static {
		System.setProperty("http.proxyHost", "172.20.202.245");
        System.setProperty("http.proxyPort", "8080");
        System.setProperty("https.proxyHost", "172.20.202.245");
        System.setProperty("https.proxyPort", "8080");
	}
	
	@Override
	public TestRailClient createInstance() {
		final String url = Bundle.getProperties().getProperty("api.testrail.url");
		final String user = Bundle.getProperties().getProperty("api.testrail.user");
		final String password = Bundle.getProperties().getProperty("api.testrail.password");
		instance = TestRail.builder(url, user, password).applicationName("Test Automation").build();
		return this;
	}

	@Override
	public TestRailClient setProject(String project) {
		List<Project> projects = instance.projects().list().execute();
		projectId = projects.stream().filter(pro -> project.equals(pro.getName())).findFirst().get().getId();
		return this;
	}

	@Override
	public TestRailClient setRun(String run) {
		List<Run> runs = instance.runs().list(projectId).execute();
		runId = runs.stream().filter(r -> run.equals(r.getName())).findFirst().get().getId();
		return this;
	}

	@Override
	public TestRailClient setStatus(int id, boolean status) {
		List<ResultField> customResultFields = instance.resultFields().list().execute();
		instance.results().addForCase(runId, id,  new Result().setStatusId(getStatus(status)), customResultFields).execute();
		return this;
	}

	private int getStatus(boolean status) {
		if(status) {
			return 1;
		} else {
			return 2;
		}
	}
	
}
