package org.ddf.listeners;

import org.ddf.reports.ReportManager;
import org.ddf.utils.Log;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.Status;
import org.ddf.configurations.GlobalVariables;
import org.ddf.utils.ScreenshotUtil;
import org.testng.annotations.Test;

/**
 * This class implements the TestNG ITestListener interface to handle test events and logging.
 */
public class TestListener implements ITestListener, GlobalVariables {

    /**
     * Retrieves the test name from the Test annotation or method name.
     * @param result The test result.
     * @return The test name.
     */
    private static String getTestName(ITestResult result) {
        String testName = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(Test.class).testName();
        if (testName != null && !testName.isEmpty()) {
            return testName;
        } else {
            return result.getMethod().getMethodName();
        }
    }

    @Override
    public void onFinish(ITestContext iTestContext) {
        ReportManager.endTest();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        String testName = getTestName(iTestResult);
        Log.messageLog(testName + " " + START);
    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        String testName = getTestName(iTestResult);
        Log.messageLog(testName + " " + PASS);
        ReportManager.getTest().log(Status.PASS, testName + " " + PASS);
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        String testName = getTestName(iTestResult);
        Log.error(testName + " " + FAIL);
        Log.messageLog(testName + " " + FAIL);
        ScreenshotUtil.attachScreenshotToReport(iTestResult, Status.FAIL);
        ScreenshotUtil.captureScreenshot(testName + "_" + FAIL);
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        String testName = getTestName(iTestResult);
        Log.messageLog(testName + " " + SKIP);
        ReportManager.getTest().log(Status.SKIP, testName + " " + SKIP);
    }
}