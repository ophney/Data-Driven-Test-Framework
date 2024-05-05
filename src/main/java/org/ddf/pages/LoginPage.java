package org.ddf.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * This class represents the Login Page of the application.
 * It contains methods to interact with elements on the login page.
 */
public class LoginPage extends BasePage {

	public LoginPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(name = "Email")
	private WebElement emailTextbox;

	@FindBy(name = "Password")
	private WebElement passwordTextbox;

	@FindBy(css = "input[value='Log in']")
	private WebElement loginButton;

	private static final String USER_EMAIL_XPATH = "//*[text()='%s']";

	/**
	 * Method to enter the email in the email textbox.
	 * @param email The email to enter.
	 */
	public void enterEmail(String email) {
		clearAndEnterText(emailTextbox, email);
	}

	/**
	 * Method to enter the password in the password textbox.
	 * @param password The password to enter.
	 */
	public void enterPassword(String password) {
		clearAndEnterText(passwordTextbox, password);
	}

	/**
	 * Method to click the login button.
	 */
	public void clickLoginButton() {
		loginButton.click();
	}

	/**
	 * Method to check if a user's email is displayed.
	 * @param email The email to check.
	 * @return true if the email is displayed, false otherwise.
	 */
	public boolean isUserEmailDisplayed(String email) {
		return isElementDisplayed(By.xpath(String.format(USER_EMAIL_XPATH, email)));
	}
}