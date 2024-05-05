package org.ddf.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

/**
 * This class represents the Cart Page of the application.
 * It contains methods to interact with elements on the cart page.
 */
public class CartPage extends BasePage {

	public CartPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(name = "updatecart")
	private WebElement updateCartButton;

	@FindBy(name = "removefromcart")
	private List<WebElement> removeCheckbox;

	@FindBy(css = ".qty-input")
	private List<WebElement> quantityTextbox;

	@FindBy(css = "span.read")
	private WebElement readTermsLink;

	@FindBy(id = "termsofservice")
	private WebElement termsCheckbox;

	@FindBy(id = "checkout")
	private WebElement checkoutButton;

	@FindBy(css = ".page-title>h1")
	private WebElement conditionsHeadingText;

	@FindBy(css = ".page-body>p")
	private WebElement conditionsBodyText;

	@FindBy(css = "span.product-price")
	private WebElement subTotalText;

	@FindBy(css = ".order-total")
	private WebElement totalText;

	@FindBy(css = ".order-summary-content")
	private WebElement emptyCartMessage;

	@FindBy(css = ".active-step")
	private WebElement emptyCartInfo;

	@FindBy(id = "CountryId")
	private WebElement countryDropdown;

	@FindBy(id = "StateProvinceId")
	private WebElement stateDropdown;

	@FindBy(id = "ZipPostalCode")
	private WebElement zipTextbox;

	@FindBy(name = "estimateshipping")
	private WebElement estimateShippingButton;

	@FindBy(className = "shipping-results")
	private WebElement shippingEstimateText;

	@FindBy(id = "terms-of-service-warning-box")
	private WebElement termsWarningText;

	@FindBy(css = ".ui-dialog-titlebar-close")
	private WebElement closeTermsWarningButton;

	@FindBy(className = "ui-dialog-title")
	private WebElement termsWarningHeadingText;

	private final By CART_QUANTITY_BY_XPATH = By.xpath("//span[@class='cart-qty'][text()='(0)']");

	/**
	 * Click on the update cart button.
	 */
	public void clickOnUpdateCartButton() {
		updateCartButton.click();
	}

	/**
	 * Select items to remove from the cart.
	 */
	public void selectItemToRemove() {
		removeCheckbox.forEach(WebElement::click);
	}

	/**
	 * Update the quantity of items in the cart.
	 * @param quantity The quantity to update.
	 */
	public void updateQuantity(String quantity) {
		quantityTextbox.forEach(ele -> clearAndEnterText(ele, quantity));
	}

	/**
	 * Click on the link to read terms.
	 */
	public void clickReadTerms() {
		readTermsLink.click();
	}

	/**
	 * Get the heading text of terms and conditions.
	 * @return The heading text.
	 */
	public String getTermsHeading() {
		return conditionsHeadingText.getText();
	}

	/**
	 * Get the body text of terms and conditions.
	 * @return The body text.
	 */
	public String getTermsBody() {
		return conditionsBodyText.getText();
	}

	/**
	 * Get the total amount of the cart.
	 * @return The total amount.
	 */
	public float getTotal() {
		return parseToFloat(totalText.getText());
	}

	/**
	 * Get the subtotal amount of the cart.
	 * @return The subtotal amount.
	 */
	public float getSubTotal() {
		return parseToFloat(subTotalText.getText());
	}

	/**
	 * Check if the cart is empty.
	 * @return true if the cart is empty, false otherwise.
	 */
	public boolean isCartEmpty() {
		return isElementDisplayed(emptyCartInfo);
	}

	/**
	 * Check if the cart quantity is zero.
	 * @return true if the cart quantity is zero, false otherwise.
	 */
	public boolean isCartQuantityZero() {
		return isElementDisplayed(CART_QUANTITY_BY_XPATH);
	}

	/**
	 * Get the message displayed when the cart is empty.
	 * @return The empty cart message.
	 */
	public String getEmptyCartMessage() {
		return emptyCartMessage.getText().trim();
	}

	/**
	 * Select a country from the dropdown.
	 * @param country The country to select.
	 */
	public void selectCountry(String country) {
		selectByText(countryDropdown, country);
	}

	/**
	 * Select a state from the dropdown.
	 * @param state The state to select.
	 */
	public void selectState(String state) {
		selectByText(stateDropdown, state);
	}

	/**
	 * Enter zip code for shipping estimate.
	 * @param zip The zip code to enter.
	 */
	public void enterZipCode(String zip) {
		zipTextbox.sendKeys(zip);
	}

	/**
	 * Click on the button to estimate shipping.
	 */
	public void clickEstimateButton() {
		estimateShippingButton.click();
	}

	/**
	 * Check if shipping estimate is displayed.
	 * @return true if the shipping estimate is displayed, false otherwise.
	 */
	public boolean isEstimateDisplayed() {
		return isElementDisplayed(shippingEstimateText);
	}

	/**
	 * Accept terms and conditions.
	 */
	public void acceptTerms() {
		termsCheckbox.click();
	}

	/**
	 * Click on the checkout button.
	 */
	public void clickCheckoutButton() {
		checkoutButton.click();
	}

	/**
	 * Close the warning displayed for terms and conditions.
	 */
	public void closeTermsWarning() {
		closeTermsWarningButton.click();
	}

	/**
	 * Get the warning text displayed for terms and conditions.
	 * @return The terms warning text.
	 */
	public String getTermsWarning() {
		return termsWarningText.getText();
	}

	/**
	 * Get the heading text of the warning displayed for terms and conditions.
	 * @return The terms warning heading text.
	 */
	public String getTermsWarningHeading() {
		return termsWarningHeadingText.getText();
	}

	/**
	 * Utility method to parse string to float, with error handling.
	 * @param text The text to parse.
	 * @return The float value, or 0.0f if the parsing fails.
	 */
	private float parseToFloat(String text) {
		try {
			return Float.parseFloat(text.trim());
		} catch (NumberFormatException e) {
			return 0.0f;
		}
	}
}