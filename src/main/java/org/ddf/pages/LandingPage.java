package org.ddf.pages;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * This class represents the Landing Page of the application.
 * It contains methods to interact with elements on the landing page.
 */
public class LandingPage extends BasePage {

	public LandingPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(name = "q")
	private WebElement searchTextbox;

	@FindBy(name = "Q")
	private WebElement advanceSearchTextbox;

	@FindBy(xpath = "//input[@value='Search']")
	private WebElement searchButton;

	@FindBy(css = "[class='product-title']>a")
	private List<WebElement> productList;

	@FindBy(css = ".product-box-add-to-cart-button")
	private WebElement addToCartButton;

	@FindBy(css = "#bar-notification p.content")
	private WebElement addToCartSuccessMessage;

	@FindBy(className = "ico-cart")
	private WebElement cartLink;

	@FindBy(css = ".close")
	private WebElement closeBannerLink;

	@FindBy(className = "header-logo")
	private WebElement headerLogo;

	/**
	 * Method to click the header logo.
	 */
	public void clickHeaderLogo() {
		headerLogo.click();
	}

	/**
	 * Method to enter a product to search for.
	 * @param product The product to search for.
	 */
	public void enterProductToSearch(String product) {
		clearAndEnterText(searchTextbox, product);
	}

	/**
	 * Method to click the search button.
	 */
	public void clickSearchButton() {
		clickAction(searchButton);
	}

	/**
	 * Method to get the list of product names from the search results.
	 * @return The list of product names.
	 */
	public List<String> getProductResult() {
		List<String> productName = new ArrayList<>();
		productList.forEach(ele -> productName.add(ele.getText()));
		return productName;
	}

	/**
	 * Method to check if the advanced search textbox is displayed.
	 * @return true if the advanced search textbox is displayed, false otherwise.
	 */
	public boolean isAdvanceSearchDisplayed() {
		return isElementDisplayed(advanceSearchTextbox);
	}

	/**
	 * Method to click the "Add to Cart" button.
	 */
	public void clickOnAddToCartButton() {
		addToCartButton.click();
	}

	/**
	 * Method to get the "Add to Cart" success message.
	 * @return The "Add to Cart" success message.
	 */
	public String getAddToCartSuccessMessage() {
		return addToCartSuccessMessage.getText();
	}

	/**
	 * Method to click the cart link.
	 */
	public void clickCartLink() {
		cartLink.click();
	}

	/**
	 * Method to close the success banner.
	 */
	public void closeSuccessBanner() {
		closeBannerLink.click();
		waitForInvisibilityElement(closeBannerLink);
	}
}