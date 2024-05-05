package org.ddf.reports;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.ddf.configurations.GlobalVariables;
import org.ddf.utils.Log;

/**
 * This class manages ExtentReports for test reporting in the test automation framework.
 */
public class ReportManager implements GlobalVariables {

	private static ExtentReports extent;
	static Map<Integer, ExtentTest> extentTestMap = new HashMap<>();

	/**
	 * Create an instance of ExtentReports with system information and attach the reporter.
	 */
	private static void createInstance() {
		ExtentSparkReporter htmlReporter = new ExtentSparkReporter(EXTENT_REPORT);
		try {
			htmlReporter.loadXMLConfig(new File(EXTENT_CONFIG));
		} catch (IOException e) {
			Log.error("Issue while configuring extent report: " + e);
		}
		extent = new ExtentReports();
		extent.setSystemInfo("User: ", System.getProperty("user.name"));
		extent.setSystemInfo("URL: ", BASE_URL);
		extent.setSystemInfo("Browser: ", "Chrome");
		extent.setSystemInfo("OS: ", System.getProperty("os.name"));
		extent.setSystemInfo("Version: ", System.getProperty("os.version"));
		extent.attachReporter(htmlReporter);
	}

	/**
	 * Get the instance of ExtentReports, creating a new one if it doesn't exist.
	 * @return The ExtentReports instance.
	 */
	private static ExtentReports getInstance() {
		if (extent == null) {
			createInstance();
		}
		return extent;
	}

	/**
	 * Get the ExtentTest for the current thread.
	 * @return The ExtentTest for the current thread.
	 */
	public static synchronized ExtentTest getTest() {
		return extentTestMap.get((int) Thread.currentThread().getId());
	}

	/**
	 * End the current ExtentTest and flush the report.
	 */
	public static synchronized void endTest() {
		getInstance().flush();
	}

	/**
	 * Start a new ExtentTest with the given test name and associate it with the current thread.
	 * @param testName The name of the test.
	 */
	public static synchronized void startTest(String testName) {
		ExtentTest test = getInstance().createTest(testName);
		extentTestMap.put((int) Thread.currentThread().getId(), test);
	}
}