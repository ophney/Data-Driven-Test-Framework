package org.ddf.steps;

import static org.testng.Assert.*;

import org.ddf.utils.Log;
import org.openqa.selenium.WebDriver;
import org.ddf.pages.LandingPage;

/**
 * This class contains steps related to the landing page actions of the application.
 */
public class LandingPageSteps {

	private final LandingPage landingPage;

	/**
	 * Constructor for LandingPageSteps class.
	 * @param driver The WebDriver instance to be used for interacting with the landing page.
	 */
	public LandingPageSteps(WebDriver driver) {
		this.landingPage = new LandingPage(driver);
	}

	/**
	 * Method to search for a product on the landing page.
	 * @param product The product to search for.
	 */
	public void searchProduct(String product) {
		Log.info("Searching for product: " + product);
		landingPage.enterProductToSearch(product);
		landingPage.clickSearchButton();
		assertTrue(landingPage.isAdvanceSearchDisplayed(), "Advance Search option was not displayed");
	}

	/**
	 * Method to validate search results for a product.
	 * @param product The product to validate search results for.
	 */
	public void validateSearchResults(String product) {
		Log.info("Validating search results for product: " + product);
		landingPage.getProductResult().forEach(item -> assertTrue(item.toLowerCase().contains(product.toLowerCase()), "Search fetched a product '" + item + "' which did not contain '" + product + "'"));
	}

	/**
	 * Method to add a product to the cart.
	 */
	public void addProductToCart() {
		Log.info("Adding product to cart");
		landingPage.clickOnAddToCartButton();
	}

	/**
	 * Method to validate the 'Add to Cart' success message.
	 * @param message The success message to validate.
	 */
	public void validateAddToCartBanner(String message) {
		Log.info("Validating 'Add to Cart' success message: " + message);
		assertEquals(landingPage.getAddToCartSuccessMessage(), message, "Product was not added to cart");
		closeAddToCartSuccessBanner();
	}

	/**
	 * Method to navigate to the shopping cart.
	 */
	public void navigateToCart() {
		Log.info("Navigating to shopping cart");
		landingPage.clickCartLink();
	}

	/**
	 * Method to close the 'Add to Cart' success banner and return to the main page.
	 */
	public void closeAddToCartSuccessBanner() {
		Log.info("Closing 'Add to Cart' success banner");
		landingPage.closeSuccessBanner();
		landingPage.clickHeaderLogo();
	}
}