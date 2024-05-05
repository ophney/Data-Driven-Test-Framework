package org.ddf.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * This class represents the Checkout Page of the application.
 * It contains methods to interact with elements during the checkout process.
 */
public class CheckoutPage extends BasePage {

	public CheckoutPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(name = "billing_address_id")
	private WebElement billingAddressDropdown;

	@FindBy(xpath = "//input[contains(@onclick,'Billing.save')]")
	private WebElement continueBillingAddressButton;

	@FindBy(id="PickUpInStore")
	private WebElement pickUpInStoreCheckbox;

	@FindBy(xpath = "//input[contains(@onclick,'Shipping.save')]")
	private WebElement continueShippingAddressButton;

	@FindBy(xpath = "//input[contains(@onclick,'ShippingMethod.save')]")
	private WebElement continueShippingMethodButton;

	@FindBy(xpath = "//input[contains(@onclick,'PaymentMethod.save')]")
	private WebElement continuePaymentMethodButton;

	@FindBy(id="CardholderName")
	private WebElement cardholderNameTextbox;

	@FindBy(id="CreditCardType")
	private WebElement creditCardTypeDropdown;

	@FindBy(id="CardNumber")
	private WebElement cardNumberTextbox;

	@FindBy(id="ExpireMonth")
	private WebElement expireMonthDropdown;

	@FindBy(id="ExpireYear")
	private WebElement expireYearDropdown;

	@FindBy(id="CardCode")
	private WebElement cardCodeTextbox;

	@FindBy(id="PurchaseOrderNumber")
	private WebElement purchaseOrderNumberTextbox;

	@FindBy(xpath = "//input[contains(@onclick,'PaymentInfo.save')]")
	private WebElement continuePaymentInfoButton;

	@FindBy(css = ".confirm-order-next-step-button")
	private WebElement confirmOrderButton;

	@FindBy(css = ".details>li")
	private WebElement orderNumberText;

	@FindBy(linkText = "Click here for order details.")
	private WebElement orderDetailsLink;

	private static final String SHIPPING_METHOD_XPATH = "//input[contains(@value,'%s')]";
	private static final String PAYMENT_METHOD_XPATH = "//label[contains(text(),'%s')]/../input";

	// Methods

	/**
	 * Selects the first billing address by index.
	 *
	 * @param index The index of the address to select.
	 */
	public void selectFirstBillingAddress(int index) {
		selectByIndex(billingAddressDropdown, index);
	}

	/**
	 * Continues to the shipping address section.
	 */
	public void continueToShippingAddress() {
		continueBillingAddressButton.click();
	}

	/**
	 * Selects the shipping address same as billing address by index.
	 *
	 * @param index The index of the address to select.
	 */
	public void selectShippingAddressSameAsBillingAddress(int index) {
		selectByIndex(billingAddressDropdown, index);
	}

	/**
	 * Picks up the order from the store.
	 */
	public void pickFromStore() {
		pickUpInStoreCheckbox.click();
	}

	/**
	 * Continues to the shipping method section.
	 */
	public void continueToShippingMethod() {
		continueShippingAddressButton.click();
	}

	/**
	 * Chooses a shipping method.
	 * @param method The shipping method to choose.
	 */
	public void chooseShippingMethod(String method) {
		click(String.format(SHIPPING_METHOD_XPATH, method));
	}

	/**
	 * Continues to the payment method section.
	 */
	public void continueToPaymentMethod() {
		continueShippingMethodButton.click();
	}

	/**
	 * Chooses a payment method.
	 * @param method The payment method to choose.
	 */
	public void choosePaymentMethod(String method) {
		click(String.format(PAYMENT_METHOD_XPATH, method));
	}

	/**
	 * Selects a credit card type from the dropdown.
	 * @param cardType The type of credit card to select.
	 */
	public void selectCC(String cardType) {
		selectByText(creditCardTypeDropdown, cardType);
	}

	/**
	 * Enters the cardholder name.
	 * @param name The cardholder name to enter.
	 */
	public void enterCardHolderName(String name) {
		cardholderNameTextbox.sendKeys(name);
	}

	/**
	 * Enters the card number.
	 * @param number The card number to enter.
	 */
	public void enterCardNumber(String number) {
		cardNumberTextbox.sendKeys(number);
	}

	/**
	 * Selects the expiration month from the dropdown.
	 * @param month The expiration month to select.
	 */
	public void selectExpirationMonth(String month) {
		selectByText(expireMonthDropdown, month);
	}

	/**
	 * Selects the expiration year from the dropdown.
	 * @param year The expiration year to select.
	 */
	public void selectExpirationYear(String year) {
		selectByText(expireYearDropdown, year);
	}

	/**
	 * Enters the card code.
	 * @param code The card code to enter.
	 */
	public void enterCardCode(String code) {
		cardCodeTextbox.sendKeys(code);
	}

	/**
	 * Enters the purchase order number.
	 * @param code The purchase order number to enter.
	 */
	public void enterPONumber(String code) {
		purchaseOrderNumberTextbox.sendKeys(code);
	}

	/**
	 * Continues to the payment information section.
	 */
	public void continueToPaymentInfo() {
		continuePaymentMethodButton.click();
		pause(1);
	}

	/**
	 * Continues to the confirm order section.
	 */
	public void continueToConfirmOrder() {
		continuePaymentInfoButton.click();
	}

	/**
	 * Clicks the confirm order button.
	 */
	public void clickConfirmOrder() {
		confirmOrderButton.click();
	}

	/**
	 * Gets the order number displayed.
	 * @return The order number.
	 */
	public String getOrderNumber() {
		return getOrderNumber(orderNumberText);
	}

	/**
	 * Views the order details.
	 */
	public void viewOrderDetails() {
		orderDetailsLink.click();
	}
}