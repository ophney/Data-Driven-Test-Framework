package org.ddf.listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import com.aventstack.extentreports.Status;
import org.ddf.utils.ScreenshotUtil;
import static org.ddf.configurations.GlobalVariables.MAX_TRY;

/**
 * This class implements the IRetryAnalyzer interface to handle retrying of failed tests.
 */
public class Retry implements IRetryAnalyzer {
	// Counter to keep track of the number of retries
	private int count = 0;

	// Maximum number of retries allowed, defined in the GlobalVariables class
	private final int retryLimit = MAX_TRY;

	/**
	 * This method is called by TestNG whenever a test fails.
	 * It decides whether the test should be retried or not.
	 *
	 * @param iTestResult The result of the test that just ran.
	 * @return true if the test should be retried, false otherwise.
	 */
	@Override
	public boolean retry(ITestResult iTestResult) {
		// Check if the number of retries is less than the retry limit
		if (count < retryLimit) {
			// Increment the retry counter
			count++;

			// Attach a screenshot to the Extent report with the status as INFO
			ScreenshotUtil.attachScreenshotToReport(iTestResult, Status.INFO);

			// Return true to indicate that the test should be retried
			return true;
		} else {
			// Return false to indicate that the test should not be retried
			return false;
		}
	}
}