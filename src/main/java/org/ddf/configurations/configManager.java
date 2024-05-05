package org.ddf.configurations;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class manages configuration properties using a Properties file.
 */
public class configManager implements GlobalVariables {
	private static final Properties properties = new Properties();
	private static final Logger Log = LogManager.getLogger(configManager.class.getName());

	/**
	 * Retrieves a property value based on the provided key.
	 * @param key The key of the property to retrieve.
	 * @return The value of the property.
	 */
	public static String getProperty(String key) {
		try {
			InputStream stream = new FileInputStream(CONFIG_FILE);
			properties.load(stream);
			stream.close();
		} catch (FileNotFoundException e) {
			Log.error("File was Not Found: {}", e.getMessage());
		} catch (IOException e) {
			Log.error("There was a IO Exception: ", e);
		}
		return properties.getProperty(key).trim();
	}
}