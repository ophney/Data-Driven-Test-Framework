package org.ddf.utils;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ddf.reports.ReportManager;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import com.aventstack.extentreports.Status;
import org.ddf.configurations.DriverManager;
import org.ddf.configurations.GlobalVariables;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

/**
 * Utility class for capturing and attaching screenshots to reports.
 */
public class ScreenshotUtil implements GlobalVariables {
	private static final Logger Logger = LogManager.getLogger(ScreenshotUtil.class);

	/**
	 * Captures a screenshot and saves it to the specified file path.
	 *
	 * @param testCaseName The name of the test case.
	 */
	public static void captureScreenshot(String testCaseName) {
		String screenShotFilePath = SCREENSHOT_FOLDER + testCaseName + DateUtil.getStringDate("_ddMMyyyy_HHMS") + IMAGE_TYPE;
		try {
			// Capture screenshot using AShot library
			Screenshot screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(DriverManager.getDriver());
			// Write the screenshot to file
			ImageIO.write(screenshot.getImage(), "PNG", new File(screenShotFilePath));
			Logger.info("Screenshot captured successfully: {}", screenShotFilePath);
		} catch (IOException exp) {
			Logger.error("Exception occurred while capturing screenshot: {}", exp.getMessage());
		}
	}

	/**
	 * Attaches the screenshot to the test report.
	 *
	 * @param result The test result.
	 * @param status The status of the test.
	 */
	public static void attachScreenshotToReport(ITestResult result, Status status) {
		try {
			// Capture screenshot as base64 string
			String base64Screenshot = "data:image/png;base64," + ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BASE64);
			// Attach screenshot to a test report
			ReportManager.getTest().log(status, result.getMethod().getMethodName() + " " + FAIL, result.getThrowable(), ReportManager.getTest().addScreenCaptureFromBase64String(base64Screenshot).getModel().getMedia().get(0));
		} catch (Exception e) {
			Logger.error("Failed to attach screenshot to report: {}", e.getMessage());
		}
	}

}
