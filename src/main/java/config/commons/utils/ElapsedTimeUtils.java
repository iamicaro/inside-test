package config.commons.utils;

public class ElapsedTimeUtils {
	
	private ElapsedTimeUtils() {
		
	}
	
	public static void loading(int temp) {
		try {
			Thread.sleep(temp);
		} catch (InterruptedException e) {
			
		}
	}
	
	
}
