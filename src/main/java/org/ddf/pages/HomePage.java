package org.ddf.pages;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * This class represents the Home Page of the application.
 * It contains methods to interact with elements on the home page.
 */
public class HomePage extends BasePage {

	public HomePage(WebDriver driver) {
		super(driver);
	}

	@FindBy(linkText = "Log in")
	private WebElement loginLink;

	@FindBy(linkText = "Log out")
	private WebElement logoutLink;

	@FindBy(className = "account")
	private WebElement userAccountLink;

	@FindBy(css = ".footer-menu-wrapper ul>li>a")
	private List<WebElement> footerLinks;

	/**
	 * Method to click the login link.
	 */
	public void clickLoginLink() {
		loginLink.click();
	}

	/**
	 * Method to click the logout link.
	 */
	public void clickLogoutLink() {
		logoutLink.click();
	}

	/**
	 * Method to check if the user has logged out.
	 * @return true if the user has logged out, false otherwise.
	 */
	public boolean hasUserLoggedOut() {
		return isElementDisplayed(loginLink);
	}

	/**
	 * Method to click the user account link.
	 */
	public void clickUserAccountLink() {
		userAccountLink.click();
	}

	/**
	 * Method to get the number of footer links.
	 * @return The number of footer links.
	 */
	public int getNumberOfFooterLinks() {
		return footerLinks.size();
	}

	/**
	 * Method to get the text of a footer link by index.
	 * @param index The index of the footer link.
	 * @return The text of the footer link.
	 */
	public String getFooterLinkText(int index) {
		return footerLinks.get(index).getText();
	}

	/**
	 * Method to get the URL of a footer link by index.
	 * @param index The index of the footer link.
	 * @return The URL of the footer link.
	 */
	public String getFooterLink(int index) {
		return footerLinks.get(index).getAttribute("href");
	}
}