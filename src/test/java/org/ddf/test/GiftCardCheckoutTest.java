package org.ddf.test;

import java.util.Map;
import org.ddf.listeners.Retry;
import org.ddf.utils.Log;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.ddf.configurations.DriverManager;
import org.ddf.steps.*;
import org.ddf.utils.ScreenshotUtil;

/**
 * Test class for Gift Card Checkout functionality.
 */
public class GiftCardCheckoutTest extends TestBase {

    private WebDriver driver;
    private CartPageSteps cartPageSteps;
    private LandingPageSteps landingPageSteps;
    private CheckoutPageSteps checkoutPageSteps;
    private ProductPageSteps productPageSteps;
    private HomePageSteps homePageSteps;
    private OrderPageSteps orderPageSteps;
    private LoginPageSteps loginPageSteps;

    /**
     * Test method for Gift Card Checkout.
     *
     * @param testData the test data for the checkout process
     */
    @Test(dataProvider = "testData", testName = "Gift Card Checkout Test", description = "This test verifies the checkout process for purchasing a Gift Card", retryAnalyzer = Retry.class)
    public void giftCheckoutTest(Map<String, String> testData) {
        driver = DriverManager.getDriver();
        initializeSteps();

        try {
            loginAndNavigateToCart(testData);
            addGiftCardToCart(testData);
            proceedToCheckoutAndPlaceOrder(testData);
            verifyOrderDetails(testData);
            homePageSteps.logout();
        } catch (Exception e) {
            Log.error("Gift Card Checkout Test failed: " + e.getMessage());
            captureScreenshot("Gift_Card_Checkout_Test_Failure");
            throw new AssertionError("Gift Card Checkout Test failed", e);
        }
    }

    /**
     * Data provider for test data.
     *
     * @return the test data for the test method
     */
    @DataProvider(name = "testData")
    public Object[][] testData() {
        return getTestData(this.getClass().getSimpleName());
    }

    /**
     * Initialize all necessary steps for the test.
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
     * Login and navigate to the cart page.
     *
     * @param testData the test data containing login information
     */
    private void loginAndNavigateToCart(Map<String, String> testData) {
        homePageSteps.navigateToLoginPage();
        loginPageSteps.login(testData.get("email"), testData.get("password"));
        landingPageSteps.navigateToCart();
        cartPageSteps.removeAllProducts();
    }

    /**
     * Add a gift card to the cart.
     *
     * @param testData the test data containing gift card details
     */
    private void addGiftCardToCart(Map<String, String> testData) {
        landingPageSteps.searchProduct(testData.get("product"));
        landingPageSteps.addProductToCart();
        productPageSteps.enterGiftCardDetails(testData.get("recipientName"), testData.get("recipientEmail"), testData.get("senderName"), testData.get("senderEmail"), testData.get("message"));
        productPageSteps.updateQuantity(testData.get("quantity"));
        captureScreenshot(testData.get("Test_Case"));
        productPageSteps.addProductToCart();
        landingPageSteps.closeAddToCartSuccessBanner();
        landingPageSteps.navigateToCart();
    }

    /**
     * Proceed to checkout and place the order.
     *
     * @param testData the test data containing checkout details
     */
    private void proceedToCheckoutAndPlaceOrder(Map<String, String> testData) {
        cartPageSteps.acceptTermsAndCheckout();
        checkoutPageSteps.selectFirstBillingAddress();
        checkoutPageSteps.selectShippingAddressAndMethod(testData.get("shippingMethod"));
        checkoutPageSteps.selectPaymentMethod(testData.get("paymentMethod"), testData.get("cardType"), testData.get("holderName"), testData.get("cardNumber"), testData.get("expirationDate"), testData.get("code"), testData.get("poNumber"));
    }

    /**
     * Verify the order details after placing the order.
     *
     * @param testData the test data containing order details
     */
    private void verifyOrderDetails(Map<String, String> testData) {
        String orderNumber = checkoutPageSteps.confirmOrder();
        orderPageSteps.verifyOrderDetails(testData.get("quantity"), testData.get("shippingMethod"), testData.get("paymentMethod"), orderNumber);
        captureScreenshot(testData.get("Test_Case"));
    }

    /**
     * Capture a screenshot for the given test case.
     *
     * @param testCase the name of the test case for the screenshot
     */
    private void captureScreenshot(String testCase) {
        ScreenshotUtil.captureScreenshot(testCase);
    }
}