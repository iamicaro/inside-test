package config.api.testrail;

public interface TestRailClient {

	public TestRailClient createInstance();
	
	public TestRailClient setProject(String project);
	
	public TestRailClient setRun(String run);
	
	public TestRailClient setStatus(int id, boolean status);
}
