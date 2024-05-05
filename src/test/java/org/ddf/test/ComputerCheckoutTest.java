package org.ddf.test;

import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.ddf.configurations.DriverManager;
import org.ddf.steps.*;

import org.ddf.utils.ScreenshotUtil;
import org.ddf.utils.Log;
import org.ddf.listeners.Retry;

public class ComputerCheckoutTest extends TestBase {

    // WebDriver instance
    private WebDriver driver;

    // Page object instances
    private LoginPageSteps loginPageSteps;
    private CartPageSteps cartPageSteps;
    private LandingPageSteps landingPageSteps;
    private CheckoutPageSteps checkoutPageSteps;
    private ProductPageSteps productPageSteps;
    private HomePageSteps homePageSteps;
    private OrderPageSteps orderPageSteps;

    /**
     * This test verifies the checkout process for purchasing a computer.
     *
     * @param testData The test data for the current test case.
     */
    @Test(dataProvider = "testData", testName = "Computer Checkout Test", description = "This test verifies the checkout process for purchasing a computer", retryAnalyzer = Retry.class)
    public void computerCheckoutTest(Map<String, String> testData) {
        // Get the WebDriver instance
        driver = DriverManager.getDriver();

        // Initialize the page object instances
        initializeSteps();

        try {
            // Login to the application
            loginToApplication(testData);

            // Add the product to the cart
            addProductToCart(testData);

            // Complete the checkout process
            completeCheckoutProcess(testData);

            // Verify the order details and logout
            verifyOrderDetailsAndLogout(testData);
            homePageSteps.logout();

        } catch (Exception e) {
            // Log test failure, take screenshot, and throw assertion error
            Log.error("Computer Checkout Test failed: " + e.getMessage());
            captureScreenshot("Computer_Checkout_Test_Failure");
            throw new AssertionError("Computer Checkout Test failed", e);
        }
    }

    /**
     * This method provides the test data for the Computer Checkout Test.
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
    private void initializeSteps() {
        cartPageSteps = new CartPageSteps(driver);
        landingPageSteps = new LandingPageSteps(driver);
        checkoutPageSteps = new CheckoutPageSteps(driver);
        productPageSteps = new ProductPageSteps(driver);
        homePageSteps = new HomePageSteps(driver);
        orderPageSteps = new OrderPageSteps(driver);
        loginPageSteps = new LoginPageSteps(driver);
    }

    /**
     * This method logs in to the application.
     *
     * @param testData The test data for the current test case.
     */
    private void loginToApplication(Map<String, String> testData) {
        homePageSteps.navigateToLoginPage();
        loginPageSteps.login(testData.get("email"), testData.get("password"));
    }

    /**
     * This method adds the product to the cart.
     *
     * @param testData The test data for the current test case.
     */
    private void addProductToCart(Map<String, String> testData) {
        landingPageSteps.navigateToCart();
        cartPageSteps.removeAllProducts();
        landingPageSteps.searchProduct(testData.get("product"));
        landingPageSteps.addProductToCart();
        productPageSteps.enterComputerSpecification(testData.get("processor"), testData.get("RAM"), testData.get("HDD"), testData.get("OS"), testData.get("software"));
        productPageSteps.updateQuantity(testData.get("quantity"));
        captureScreenshot(testData.get("Test_Case"));
        productPageSteps.addProductToCart();
        landingPageSteps.closeAddToCartSuccessBanner();
        landingPageSteps.navigateToCart();
    }

    /**
     * This method completes the checkout process.
     *
     * @param testData The test data for the current test case.
     */
    private void completeCheckoutProcess(Map<String, String> testData) {
        cartPageSteps.acceptTermsAndCheckout();
        checkoutPageSteps.selectFirstBillingAddress();
        checkoutPageSteps.selectShippingAddressAndMethod(testData.get("shippingMethod"));
        checkoutPageSteps.selectPaymentMethod(testData.get("paymentMethod"), testData.get("cardType"), testData.get("holderName"), testData.get("cardNumber"), testData.get("expirationDate"), testData.get("code"), testData.get("poNumber"));
    }

    /**
     * This method verifies the order details and logs out.
     *
     * @param testData The test data for the current test case.
     */
    private void verifyOrderDetailsAndLogout(Map<String, String> testData) {
        String orderNumber = checkoutPageSteps.confirmOrder();
        orderPageSteps.verifyOrderDetails(testData.get("quantity"), testData.get("shippingMethod"), testData.get("paymentMethod"), orderNumber);
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