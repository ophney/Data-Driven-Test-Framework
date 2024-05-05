package org.ddf.steps;

import org.ddf.utils.Log;
import org.openqa.selenium.WebDriver;
import org.ddf.pages.CartPage;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CartPageSteps {

	private final CartPage cartPage;

	public CartPageSteps(WebDriver driver) {
		cartPage = new CartPage(driver);
	}

	/**
	 * Remove all products from the cart.
	 */

	public void removeAllProducts() {
		Log.info("Removing all products from the cart");
		if (!cartPage.isCartQuantityZero()) {
			cartPage.selectItemToRemove();
			cartPage.clickOnUpdateCartButton();
			cartPage.waitForPageLoad();
		}
		assertTrue(cartPage.isCartEmpty(), "Cart was not empty after removing all items");
		assertTrue(cartPage.isCartQuantityZero(), "Cart quantity was not zero after removing all items");
	}

	/**
	 * Update the product quantity in the cart.
	 *
	 * @param quantity The new quantity to be set for the product.
	 */
	public void updateQuantity(String quantity) {
		Log.info("Updating product quantity to: " + quantity);
		float subTotalBefore = cartPage.getSubTotal();
		cartPage.updateQuantity(quantity);
		cartPage.clickOnUpdateCartButton();
		cartPage.waitForPageLoad();
		float totalAfter = subTotalBefore * Integer.parseInt(quantity);
		float subTotalAfter = cartPage.getSubTotal();
		assertEquals(subTotalAfter, totalAfter, "Sub-Total was not updated correctly");
		assertEquals(cartPage.getTotal(), subTotalAfter, "Total did not match with Sub-Total");
	}

	/**
	 * Validate the empty cart message.
	 *
	 * @param message The expected empty cart message.
	 */

	public void validateEmptyCart(String message) {
		Log.info("Validating empty cart message: " + message);
		assertEquals(cartPage.getEmptyCartMessage(), message, "Cart empty message did not match");
	}

	/**
	 * Validate the Terms & Conditions.
	 *
	 * @param heading The expected heading of the Terms & Conditions.
	 * @param body    The expected body of the Terms & Conditions.
	 */

	public void validateTerms(String heading, String body) {
		Log.info("Validating Terms & Conditions");
		cartPage.clickReadTerms();
		String window = cartPage.switchToNewWindow();
		assertEquals(cartPage.getTermsHeading(), heading, "Terms Heading did not match");
		assertEquals(cartPage.getTermsBody(), body, "Terms body did not match");
		cartPage.closeAndSwitchToParentWindow(window);
	}

	/**
	 * Verify the shipping estimate details.
	 *
	 * @param country The country for the shipping estimate.
	 * @param state   The state for the shipping estimate.
	 * @param zip     The zip code for the shipping estimate.
	 */
	public void validateShippingEstimate(String country, String state, String zip) {
		Log.info("Verifying shipping estimate details for Country: " + country + "State: " + state + "Zip: " + zip);
		cartPage.selectCountry(country);
		cartPage.selectState(state);
		cartPage.enterZipCode(zip);
		cartPage.clickEstimateButton();
		assertTrue(cartPage.isEstimateDisplayed(), "Shipping Estimates was not displayed");
	}

	/**
	 * Validate the Terms & Conditions warning message.
	 *
	 * @param tcHeading  The expected heading of the Terms & Conditions warning.
	 * @param tcWarning  The expected warning message for the Terms & Conditions.
	 */
	public void validateTermsWarningMessage(String tcHeading, String tcWarning) {
		Log.info("Validating Terms & Conditions warning message");
		cartPage.clickCheckoutButton();
		assertEquals(cartPage.getTermsWarningHeading(), tcHeading, "T&C warning heading did not match");
		assertEquals(cartPage.getTermsWarning(), tcWarning, "T&C warning message did not match");
		cartPage.closeTermsWarning();
	}

	/**
	 * Accept the Terms & Conditions and proceed to checkout.
	 */
	public void acceptTermsAndCheckout() {
		Log.info("Accepting Terms & Conditions and checking out the product");
		cartPage.acceptTerms();
		cartPage.clickCheckoutButton();
	}
}
