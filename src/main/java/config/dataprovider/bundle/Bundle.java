package config.dataprovider.bundle;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;

public class Bundle {

	final Logger logger = Logger.getLogger(Bundle.class);
	
	/**
	 *	Método responsável por disponibilizar o Properties
	 * 
	 * @since 03/10/2018
	 * @author Ícaro Silva
	 * @throws IOException
	 */
	public static Properties getProperties(String filePath) {
		Properties props = null;
		try {
			props = getProps(filePath);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}
	
	/**
	 *	Método responsável por disponibilizar o Properties
	 * 
	 * @since 03/10/2018
	 * @author Ícaro Silva
	 * @throws IOException
	 */
	public static Properties getProperties() {
		Properties props = null;
		try {
			props = getProps("application.properties");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}
	
	/**
	 *	Método responsável por configurar aonde o properties deve procurar as informações.
	 * 
	 * @since 03/10/2018
	 * @author Ícaro Silva
	 * @throws IOException
	 */
	private static Properties getProps(String filePath) throws IOException {
		Properties props = new Properties();
		FileInputStream file = new FileInputStream("./src/main/resources/" + filePath);
		props.load(file);
		return props;
}
	
}
