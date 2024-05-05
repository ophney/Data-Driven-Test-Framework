package org.ddf.steps;

import org.ddf.utils.Log;
import org.openqa.selenium.WebDriver;
import org.ddf.pages.CheckoutPage;
import static org.ddf.configurations.GlobalVariables.*;

public class CheckoutPageSteps {

	private final CheckoutPage checkoutPage;

	public CheckoutPageSteps(WebDriver driver) {
		checkoutPage = new CheckoutPage(driver);
	}

	/**
	 * Select the first billing address and continue to the shipping address step.
	 */

	public void selectFirstBillingAddress() {
		Log.info("Selecting the first billing address and continuing to shipping address");
		checkoutPage.selectFirstBillingAddress(0);
		checkoutPage.continueToShippingAddress();
	}

	/**
	 * Select the shipping address and shipping method based on the provided shipping method.
	 * If the shipping method is "In Store", it will select the "Pick from Store" option.
	 * If the shipping method is not Applicable, it will select the shipping address as the same as the billing address,
	 * choose the shipping method, and continue to the payment method step.
	 *
	 * @param shippingMethod The shipping method to be selected.
	 */

	public void selectShippingAddressAndMethod(String shippingMethod) {
		Log.info("Selecting shipping address and method: " + shippingMethod);
		if (shippingMethod.equalsIgnoreCase(IN_STORE)) {
			checkoutPage.pickFromStore();
			checkoutPage.continueToShippingMethod();
		} else if (!shippingMethod.equalsIgnoreCase(NOT_APPLICABLE)) {
			checkoutPage.selectShippingAddressSameAsBillingAddress(0);
			checkoutPage.continueToShippingMethod();
			checkoutPage.chooseShippingMethod(shippingMethod);
			checkoutPage.continueToPaymentMethod();
		}
	}

	/**
	 * Select the payment method and fill in the required payment information.
	 * If the payment method is "Credit Card", it will select the card type, enter the cardholder name, card number,
	 * expiration date, and card code.
	 * If the payment method is "Purchase Order", it will enter the PO number.
	 * After filling in the payment information, it will continue to the confirm order step.
	 *
	 * @param paymentMethod   The payment method to be selected.
	 * @param cardType        The credit card type.
	 * @param holderName      The credit card holder name.
	 * @param cardNumber      The credit card number.
	 * @param expirationDate  The credit card expiration date in the format "MM-YYYY".
	 * @param code            The credit card security code.
	 * @param poNumber        The purchase order number.
	 */

	public void selectPaymentMethod(String paymentMethod, String cardType, String holderName, String cardNumber, String expirationDate, String code, String poNumber) {
		Log.info("Selecting payment method: " + paymentMethod);
		checkoutPage.choosePaymentMethod(paymentMethod);
		checkoutPage.continueToPaymentInfo();
		if (paymentMethod.equalsIgnoreCase(CREDIT_CARD)) {
			checkoutPage.selectCC(cardType);
			checkoutPage.enterCardHolderName(holderName);
			checkoutPage.enterCardNumber(cardNumber);
			String[] expDetails = expirationDate.split("-");
			checkoutPage.selectExpirationMonth(expDetails[0]);
			checkoutPage.selectExpirationYear(expDetails[1]);
			checkoutPage.enterCardCode(code);
		} else if (paymentMethod.equalsIgnoreCase(PURCHASE_ORDER)) {
			checkoutPage.enterPONumber(poNumber);
		}
		checkoutPage.continueToConfirmOrder();
	}

	/**
	 * Confirm the order and retrieve the order number.
	 * After confirming the order, it will view the order details.
	 *
	 * @return The order number.
	 */

	public String confirmOrder() {
		Log.info("Confirming the order");
		checkoutPage.clickConfirmOrder();
		String orderNumber = checkoutPage.getOrderNumber();
		checkoutPage.viewOrderDetails();
		return orderNumber;
	}
}
