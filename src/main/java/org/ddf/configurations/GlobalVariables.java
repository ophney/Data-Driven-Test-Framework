package org.ddf.configurations;

import java.io.File;
import java.time.Duration;

/**
 * This interface defines global variables used in the test automation framework.
 */
public interface GlobalVariables {

	// Base URL for the application
	String BASE_URL = configManager.getProperty("url");

	// User email and password
	String EMAIL = configManager.getProperty("userEmail");
	String PASSWORD = configManager.getProperty("userPassword");

	// Default values
	String NOT_APPLICABLE = "NA";
	String PRODUCT_TYPE = "JEWELRY";
	String IN_STORE = "STORE";
	String CREDIT_CARD = "Credit Card";
	String PURCHASE_ORDER = "Purchase Order";
	String FILE_NAME = "order_%s.pdf";

	// Workbook and sheet names
	String WORKBOOK = configManager.getProperty("workbook");
	String SCENARIO_SHEET_NAME = System.getenv("sheet") != null ? System.getenv("sheet").trim() : configManager.getProperty("sheet");
	int RUN_MODE_COLUMN = 3;
	int TEST_CASE_COLUMN = 1;
	String RUN_MODE_YES = "YES";

	// Test execution status
	String START = "STARTED";
	String PASS = "PASSED";
	String FAIL = "FAILED";
	String SKIP = "SKIPPED";

	// File and folder paths
	String CONFIG_FILE = "config.properties";
	String DATA_FOLDER = new File(configManager.getProperty("dataPath")).getAbsolutePath() + File.separator;
	String SCREENSHOT_FOLDER = new File("screenshots").getAbsolutePath() + File.separator;
	String DOWNLOAD_FOLDER = new File("downloads").getAbsolutePath() + File.separator;
	String IMAGE_TYPE = ".png";
	String TEST_PACKAGE = "org.ddf.test.%s";

	// Timeouts and waits
	int TIME_OUT = Integer.parseInt(configManager.getProperty("explicitWait"));
	Duration EXPLICIT_WAIT = Duration.ofSeconds(Long.parseLong(configManager.getProperty("explicitWait")));
	Duration IMPLICIT_WAIT = Duration.ofSeconds(Long.parseLong(configManager.getProperty("implicitWait")));
	int DOWNLOAD_WAIT = Integer.parseInt(configManager.getProperty("downloadTime"));
	int MAX_TRY = configManager.getProperty("retry").equalsIgnoreCase("true") ? 1 : 0;

	// Report paths
	String EXTENT_REPORT = configManager.getProperty("reportPath");
	String EXTENT_CONFIG = configManager.getProperty("configPath");
}