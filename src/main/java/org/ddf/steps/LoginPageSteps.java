package org.ddf.steps;

import org.ddf.utils.Log;
import org.openqa.selenium.WebDriver;
import static org.testng.Assert.*;
import org.ddf.pages.LoginPage;

/**
 * This class contains steps related to the login functionality of the application.
 */
public class LoginPageSteps {

	private final LoginPage loginPage;

	/**
	 * Constructor for LoginPageSteps class.
	 * @param driver The WebDriver instance to be used for interacting with the login page.
	 */
	public LoginPageSteps(WebDriver driver) {
		this.loginPage = new LoginPage(driver);
	}

	/**
	 * Method to perform login with provided credentials.
	 * @param email The email address of the user.
	 * @param password The password of the user.
	 */
	public void login(String email, String password) {
		Log.info("Logging into the shop with credentials: " + email + " and " + password);
		loginPage.enterEmail(email);
		loginPage.enterPassword(password);
		loginPage.clickLoginButton();
		assertTrue(loginPage.isUserEmailDisplayed(email), "User login failed");
	}
}