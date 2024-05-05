package org.ddf.test;

import java.lang.reflect.Method;
import java.util.HashMap;
import org.ddf.utils.DirectoryUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestContext;
import org.testng.annotations.*;
import org.ddf.configurations.DriverManager;
import org.ddf.configurations.GlobalVariables;
import org.ddf.listeners.TestListener;
import org.ddf.reports.ReportManager;
import org.ddf.utils.ExcelUtil;

@Listeners({ TestListener.class })
public class TestBase implements GlobalVariables {

	/**
	 * This field holds a WebDriverWait object for handling explicit waits.
	 */
	protected WebDriverWait wait;

	/**
	 * This method is called before each test method. It clears any test folders.
	 *
	 * @param context The test context.
	 */
	@BeforeTest
	public void setupTestEnvironment(ITestContext context) {
		// Clear the test folders before each test.
		clearTestFolders();
	}

	/**
	 * This method is called before each test method. It initializes the WebDriver and starts the test report.
	 *
	 * @param method The test method.
	 */
	@BeforeMethod(alwaysRun = true)
	public void initializeTest(Method method) {
		// Configure the WebDriver for the test.
		configureWebDriver();

		// Start the test report with the name of the test method.
		startTestReport(method.getName());
	}

	/**
	 * This method is called after each test method. It quits the WebDriver.
	 */
	@AfterMethod(alwaysRun = true)
	public void tearDownTest() {
		// Quit the WebDriver after each test.
		quitWebDriver();
	}

	/**
	 * This method clears the test folders.
	 */
	private void clearTestFolders() {
		// Use DirectoryUtil to clear the download and screenshot folders.
		DirectoryUtil directoryUtil = new DirectoryUtil();
		directoryUtil.clearFolder(GlobalVariables.DOWNLOAD_FOLDER);
		directoryUtil.clearFolder(GlobalVariables.SCREENSHOT_FOLDER);
	}

	/**
	 * This method configures the WebDriver for the test.
	 */
	private void configureWebDriver() {
		// Set up ChromeOptions for the WebDriver.
		ChromeOptions options = new ChromeOptions();
		HashMap<String, Object> chromeConfigs = new HashMap<>();
		chromeConfigs.put("download.default_directory", GlobalVariables.DOWNLOAD_FOLDER);
		options.setExperimentalOption("prefs", chromeConfigs);
		options.addArguments("--remote-allow-origins=*");
		options.setAcceptInsecureCerts(true);

		// Create a new ChromeDriver instance with the configured options.
		WebDriver driver = new ChromeDriver(options);

		// Set the window size to maximum.
		driver.manage().window().maximize();

		// Set the implicit wait time.
		driver.manage().timeouts().implicitlyWait(GlobalVariables.IMPLICIT_WAIT);

		// Create a WebDriverWait object for explicit waits.
		wait = new WebDriverWait(driver, GlobalVariables.EXPLICIT_WAIT);

		// Set the WebDriver instance to the DriverManager.
		DriverManager.getInstance().setDriver(driver);

		// Navigate to the base URL.
		navigateToBaseUrl();
	}

	/**
	 * This method starts the test report with the given test name.
	 *
	 * @param testName The name of the test.
	 */
	private void startTestReport(String testName) {
		// Start the test report with the given test name.
		ReportManager.startTest(testName);
	}

	/**
	 * This method quits the WebDriver.
	 */
	private void quitWebDriver() {
		// Quit the WebDriver.
		DriverManager.quitDriver();
	}

	/**
	 * This method navigates to the base URL.
	 */
	private void navigateToBaseUrl() {
		// Navigate to the base URL.
		DriverManager.getDriver().get(GlobalVariables.BASE_URL);
	}

	/**
	 * This method retrieves test data from an Excel file.
	 *
	 * @param className The name of the class for which the test data is to be retrieved.
	 * @return The test data as a 2D array.
	 */
	public Object[][] getTestData(String className) {
		// Create an ExcelUtil object to read the Excel file.
		ExcelUtil excelUtil = new ExcelUtil();

		// Set the Excel file path.
		excelUtil.setExcelFile(GlobalVariables.DATA_FOLDER + GlobalVariables.WORKBOOK);

		// Get the number of rows in the Excel sheet for the given class name.
		int testRowCount = excelUtil.getNumberOfRows(className);

		// Create a 2D array to hold the test data.
		Object[][] data = new Object[testRowCount - 1][1];

		// Iterate through each row in the Excel sheet.
		for (int i = 1; i < testRowCount; i++) {
			// Add the data from the Excel sheet to the array.
			data[i - 1][0] = excelUtil.getData(className, i);
		}

		// Return the test data array.
		return data;
	}
}