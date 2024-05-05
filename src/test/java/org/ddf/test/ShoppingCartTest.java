package org.ddf.test;

import java.util.Map;
import org.ddf.listeners.Retry;
import org.ddf.steps.CartPageSteps;
import org.ddf.steps.LandingPageSteps;
import org.ddf.utils.Log;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.ddf.configurations.DriverManager;
import org.ddf.utils.ScreenshotUtil;

public class ShoppingCartTest extends TestBase {

    // WebDriver instance
    private WebDriver driver;

    // Page object instances
    private LandingPageSteps landingPageSteps;
    private CartPageSteps cartPageSteps;

    /**
     * This test verifies the shopping cart functionality.
     *
     * @param testData The test data for the current test case.
     */
    @Test(dataProvider = "testData", testName = "Shopping Cart Functionality Test", description = "This test verifies the features of shopping cart", retryAnalyzer = Retry.class)
    public void shoppingCartTest(Map<String, String> testData) {
        // Get the WebDriver instance
        driver = DriverManager.getDriver();

        // Initialize the page object instances
        initializePages();

        try {
            // Add products to the cart
            addProductsToCart(testData);

            // Navigate to the cart and verify the details
            navigateToCartAndVerifyDetails(testData);

            // Remove the products and verify the empty cart
            removeProductsAndVerifyEmptyCart(testData);
        } catch (Exception e) {
            // Handle test failure
            Log.error("Shopping Cart Functionality Test failed: " + e.getMessage());
            captureScreenshot("Shopping_Cart_Functionality_Test_Failure");
            throw new AssertionError("Shopping Cart Functionality Test failed", e);
        }
    }

    /**
     * This method provides the test data for the shopping cart test.
     *
     * @return The test data as a 2D array.
     */
    @DataProvider(name = "testData")
    public Object[][] testData() {
        return getTestData(this.getClass().getSimpleName());
    }

    /**
     * This method initializes the page object instances.
     */
    private void initializePages() {
        landingPageSteps = new LandingPageSteps(driver);
        cartPageSteps = new CartPageSteps(driver);
    }

    /**
     * This method adds the products to the cart.
     *
     * @param testData The test data for the current test case.
     */
    private void addProductsToCart(Map<String, String> testData) {
        for (String product : testData.get("product").split("\\|")) {
            landingPageSteps.searchProduct(product);
            landingPageSteps.validateSearchResults(product);
            landingPageSteps.addProductToCart();
            landingPageSteps.validateAddToCartBanner(testData.get("message"));
        }
        landingPageSteps.navigateToCart();
        captureScreenshot(testData.get("Test_Case"));
    }

    /**
     * This method navigates to the cart and verifies the details.
     *
     * @param testData The test data for the current test case.
     */
    private void navigateToCartAndVerifyDetails(Map<String, String> testData) {
        cartPageSteps.updateQuantity(testData.get("quantity"));
        cartPageSteps.validateTerms(testData.get("termsHeading"), testData.get("termsDetails"));
        cartPageSteps.validateShippingEstimate(testData.get("country"), testData.get("state"), testData.get("zipCode"));
        captureScreenshot(testData.get("Test_Case"));
        cartPageSteps.validateTermsWarningMessage(testData.get("tcWarningHeading"), testData.get("tcWarningContent"));
    }

    /**
     * This method removes the products from the cart and verifies the empty cart.
     *
     * @param testData The test data for the current test case.
     */
    private void removeProductsAndVerifyEmptyCart(Map<String, String> testData) {
        cartPageSteps.removeAllProducts();
        cartPageSteps.validateEmptyCart(testData.get("emptyMessage"));
        captureScreenshot(testData.get("Test_Case"));
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