package org.ddf.steps;

import static org.ddf.configurations.GlobalVariables.FILE_NAME;
import static org.ddf.configurations.GlobalVariables.NOT_APPLICABLE;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import org.ddf.utils.PDFUtil;
import org.ddf.utils.Log;
import org.openqa.selenium.WebDriver;
import org.ddf.pages.OrderDetailsPage;

public class OrderPageSteps {

    private final OrderDetailsPage orderDetailsPage;

    public OrderPageSteps(WebDriver driver) {
        this.orderDetailsPage = new OrderDetailsPage(driver);
    }

    /**
     * Verify the details of the order.
     *
     * @param quantity        The expected quantity of the ordered product.
     * @param shippingMethod  The expected shipping method.
     * @param paymentMethod   The expected payment method.
     * @param orderNumber     The expected order number.
     */
    public void verifyOrderDetails(String quantity, String shippingMethod, String paymentMethod, String orderNumber) {
        Log.info("Verifying order details...");
        assertEquals(orderDetailsPage.getCartTotal(), orderDetailsPage.getOrderTotal(), "Order total on the Order Details page did not match");
        assertEquals(orderDetailsPage.getQuantity(), quantity, "Ordered quantity did not match");

        if (!shippingMethod.equalsIgnoreCase(NOT_APPLICABLE)) {
            assertTrue(orderDetailsPage.getShippingMethod().contains(shippingMethod), "Shipping method did not match");
        }

        assertTrue(orderDetailsPage.getPaymentMethod().contains(paymentMethod), "Payment method did not match");
        assertEquals(orderDetailsPage.getOrderNumber(), orderNumber, "Order number did not match");
        Log.info("Order details verified successfully.");
    }

    /**
     * Navigate to the Order Details page from the My Orders section.
     */
    public void navigateToDetailsFromMyOrder() {
        Log.info("Navigating to Order Details from My Orders...");
        orderDetailsPage.clickMyOrdersLinks();
        String orderNumber = orderDetailsPage.getMyOrderNumber();
        String orderStatus = orderDetailsPage.getMyOrderStatus();
        String orderTotal = orderDetailsPage.getMyOrderTotal();
        orderDetailsPage.clickOnOrderDetailsButton();

        assertEquals(orderDetailsPage.getOrderDetailsStatus().toLowerCase(), orderStatus.toLowerCase(), "Order status did not match");
        assertEquals(orderDetailsPage.getOrderNumber(), orderNumber, "Order number did not match");
        assertTrue(orderTotal.contains(orderDetailsPage.getOrderTotal()), "Order total did not match");
        Log.info("Navigated to Order Details from My Orders successfully.");
    }

    /**
     * Navigate to the Order Details page from the Downloadable Products section.
     */
    public void navigateToDetailsFromDownloadableProducts() {
        Log.info("Navigating to Order Details from Downloadable Products...");
        orderDetailsPage.clickDownloadableProductsLinks();
        String orderNumber = orderDetailsPage.getDownloadableProductOrderNumber();
        orderDetailsPage.clickOrderNumberLink();
        assertEquals(orderDetailsPage.getOrderNumber(), orderNumber, "Order number did not match");
        Log.info("Navigated to Order Details from Downloadable Products successfully.");
    }

    /**
     * Validate the PDF invoice generated for the order.
     *
     * @throws Exception If there is an issue reading the PDF content.
     */
    public void validatePDFInvoice() throws Exception {
        Log.info("Validating PDF invoice...");
        String orderNumber = orderDetailsPage.getOrderNumber();
        orderDetailsPage.clickPDFInvoiceButton();
        String pdfContent = PDFUtil.read(String.format(FILE_NAME, orderNumber));
        assertNotNull(pdfContent, "Did not get PDF content");
        assertTrue(pdfContent.contains(orderDetailsPage.getCartTotal()), "Order total on the PDF invoice did not match");
        assertTrue(pdfContent.contains(orderDetailsPage.getQuantity()), "Ordered quantity did not match");
        assertTrue(pdfContent.contains(orderDetailsPage.getPaymentMethod()), "Payment method did not match");
        assertTrue(pdfContent.contains(orderNumber), "Order number did not match");
        Log.info("PDF invoice validated successfully.");
    }
}
