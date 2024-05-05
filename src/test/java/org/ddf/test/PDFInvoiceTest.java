package org.ddf.test;

import org.ddf.listeners.Retry;
import org.ddf.utils.Log;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import org.ddf.configurations.DriverManager;
import org.ddf.steps.HomePageSteps;
import org.ddf.steps.LoginPageSteps;
import org.ddf.steps.OrderPageSteps;
import org.ddf.utils.ScreenshotUtil;

public class PDFInvoiceTest extends TestBase {
    // Page object instances
    private HomePageSteps homePageSteps;
    private LoginPageSteps loginPageSteps;
    private OrderPageSteps orderPageSteps;

    /**
     * This method sets up the test environment by initializing the page object instances.
     */
    @BeforeMethod
    private void setUp() {
        WebDriver driver = DriverManager.getDriver();
        homePageSteps = new HomePageSteps(driver);
        loginPageSteps = new LoginPageSteps(driver);
        orderPageSteps = new OrderPageSteps(driver);
    }

    /**
     * This test verifies the details on the PDF Invoice of an Order.
     */
    @Test(testName = "PDF Invoice Test - Order Details", description = "This test verifies the details on the PDF Invoice of an Order", retryAnalyzer = Retry.class)
    public void pdfInvoiceTestOrderDetails() {
        executePDFInvoiceTest("Order Details", () -> {
            homePageSteps.navigateToLoginPage();
            loginPageSteps.login(EMAIL, PASSWORD);
            homePageSteps.navigateToUserAccount();
            orderPageSteps.navigateToDetailsFromMyOrder();
            try {
                orderPageSteps.validatePDFInvoice();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * This test verifies the details on the PDF Invoice of Downloadable products.
     */
    @Test(testName = "PDF Invoice Test - Downloadable Products", description = "This test verifies the details on the PDF Invoice of Downloadable products", retryAnalyzer = Retry.class)
    public void pdfInvoiceTestDownloadableProducts() {
        executePDFInvoiceTest("Downloadable Products", () -> {
            homePageSteps.navigateToLoginPage();
            loginPageSteps.login(EMAIL, PASSWORD);
            homePageSteps.navigateToUserAccount();
            orderPageSteps.navigateToDetailsFromDownloadableProducts();
            try {
                orderPageSteps.validatePDFInvoice();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * This method executes the PDF Invoice test.
     *
     * @param description The description of the test.
     * @param testSteps    The test steps to be executed.
     */
    private void executePDFInvoiceTest(String description, Runnable testSteps) {
        try {
            Log.info("Starting PDF Invoice Test: " + description);
            // Execute test steps
            testSteps.run();
            // Take screenshot
            captureScreenshot(description);
            Log.info("PDF Invoice Test passed: " + description);
        } catch (Exception e) {
            // Log test failure, take screenshot, and throw assertion error
            Log.error("PDF Invoice Test failed: " + e.getMessage());
            captureScreenshot("PDF_Invoice_Test_Failure");
            throw new AssertionError("PDF Invoice Test failed: " + description, e);
        }
    }

    /**
     * This method captures a screenshot.
     *
     * @param testCase The name of the test case.
     */
    private void captureScreenshot(String testCase) {
        ScreenshotUtil.captureScreenshot(testCase);
    }
}