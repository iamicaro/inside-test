package config.commons.utils;

import cucumber.api.DataTable;

public class DataTableUtils {

	private DataTableUtils() {
		
	}
	
	public static String get(DataTable value, String key) {
		return value.asMaps(String.class, String.class).get(0).get(key);
	}
	
}
