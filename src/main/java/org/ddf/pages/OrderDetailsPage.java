package org.ddf.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * This class represents the Order Details Page of the application.
 * It contains methods to interact with elements on the order details page.
 */
public class OrderDetailsPage extends BasePage {

	public OrderDetailsPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(className = "order-number")
	private WebElement orderNumberTextbox;

	@FindBy(css = ".order-total>strong")
	private WebElement orderTotalText;

	@FindBy(css = ".order-details>span:nth-child(2)")
	private WebElement orderDetailsStatusText;

	@FindBy(css = ".cart-total-right strong")
	private WebElement cartOrderTotal;

	@FindBy(className = "payment-method")
	private WebElement paymentMethodText;

	@FindBy(className = "shipping-method")
	private WebElement shippingMethodText;

	@FindBy(css = "td.a-center.quantity")
	private WebElement quantityText;

	@FindBy(css = ".pdf-order-button")
	private WebElement pdfInvoiceButton;

	// My account - Orders
	@FindBy(css = ".order-item>.title>strong")
	private WebElement myOrderNumberTextbox;

	@FindBy(css = ".order-item>.info>li")
	private WebElement myOrderStatusTextbox;

	@FindBy(css = ".order-item>.info>li:nth-child(3)")
	private WebElement myOrderTotalTextbox;

	@FindBy(css = ".order-details-button")
	private WebElement myOrderDetailsButton;

	@FindBy(linkText = "Orders")
	private WebElement myOrdersLink;

	// My account - Downloadable products
	@FindBy(linkText = "Downloadable products")
	private WebElement downloadableProductsLink;

	@FindBy(css = "td.order>a")
	private WebElement downloadableProductOrderLink;

	/**
	 * Method to get the order number.
	 * @return The order number.
	 */
	public String getOrderNumber() {
		return getOrderNumber(orderNumberTextbox);
	}

	/**
	 * Method to get the payment method.
	 * @return The payment method.
	 */
	public String getPaymentMethod() {
		return paymentMethodText.getText().trim();
	}

	/**
	 * Method to click the PDF invoice button.
	 */
	public void clickPDFInvoiceButton() {
		pdfInvoiceButton.click();
	}

	/**
	 * Method to get the shipping method.
	 * @return The shipping method.
	 */
	public String getShippingMethod() {
		return shippingMethodText.getText();
	}

	/**
	 * Method to get the quantity.
	 * @return The quantity.
	 */
	public String getQuantity() {
		return quantityText.getText().trim();
	}

	/**
	 * Method to get the order total.
	 * @return The order total.
	 */
	public String getOrderTotal() {
		return orderTotalText.getText().trim();
	}

	/**
	 * Method to get the cart total.
	 * @return The cart total.
	 */
	public String getCartTotal() {
		return cartOrderTotal.getText().trim();
	}

	/**
	 * Method to click on the order details button.
	 */
	public void clickOnOrderDetailsButton() {
		myOrderDetailsButton.click();
	}

	/**
	 * Method to get the user's order number.
	 * @return The user's order number.
	 */
	public String getMyOrderNumber() {
		return getOrderNumber(myOrderNumberTextbox);
	}

	/**
	 * Method to get the user's order status.
	 * @return The user's order status.
	 */
	public String getMyOrderStatus() {
		return myOrderStatusTextbox.getText();
	}

	/**
	 * Method to get the user's order total.
	 * @return The user's order total.
	 */
	public String getMyOrderTotal() {
		return myOrderTotalTextbox.getText();
	}

	/**
	 * Method to get the order details status.
	 * @return The order details status.
	 */
	public String getOrderDetailsStatus() {
		return orderDetailsStatusText.getText();
	}

	/**
	 * Method to click on the "My Orders" link.
	 */
	public void clickMyOrdersLinks() {
		myOrdersLink.click();
	}

	/**
	 * Method to click on the "Downloadable Products" link.
	 */
	public void clickDownloadableProductsLinks() {
		downloadableProductsLink.click();
	}

	/**
	 * Method to click on the order number link for downloadable products.
	 */
	public void clickOrderNumberLink() {
		downloadableProductOrderLink.click();
	}

	/**
	 * Method to get the downloadable product order number.
	 * @return The downloadable product order number.
	 */
	public String getDownloadableProductOrderNumber() {
		return downloadableProductOrderLink.getText();
	}
}