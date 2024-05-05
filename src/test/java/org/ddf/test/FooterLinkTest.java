package org.ddf.test;

import org.ddf.listeners.Retry;
import org.ddf.utils.Log;
import org.ddf.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;
import org.ddf.configurations.DriverManager;
import org.ddf.steps.HomePageSteps;

public class FooterLinkTest extends TestBase {

    /**
     * This test validates the footer links.
     */
    @Test(testName = "Footer link Test", description = "This test validates the footer links", retryAnalyzer = Retry.class)
    public void footerLinkTest() {
        // Get the WebDriver instance
        WebDriver driver = DriverManager.getDriver();

        // Initialize HomePageSteps
        HomePageSteps homePageSteps = new HomePageSteps(driver);

        try {
            // Validates the footer links
            homePageSteps.validateFooterLinks();
        } catch (Exception e) {
            // Log test failure, take screenshot, and throw assertion error
            Log.error("Footer links Test failed: " + e.getMessage());
            ScreenshotUtil.captureScreenshot("Footer_links_Test_Failure");
            throw new AssertionError("Footer links Test failed", e);
        }
    }
}