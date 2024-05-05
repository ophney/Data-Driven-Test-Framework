package org.ddf.pages;

import org.ddf.configurations.GlobalVariables;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This is the base class for all page objects in the application.
 * It provides common methods and utilities for interacting with web elements.
 */
public class BasePage implements GlobalVariables {

	protected WebDriver driver;
	protected WebDriverWait wait;

	/**
	 * Constructor for the BasePage class.
	 * @param driver The WebDriver instance to be used for interacting with the page.
	 */
	public BasePage(WebDriver driver) {
		this.driver = driver;
		// Initialize WebDriver and WebDriverWait
		wait = new WebDriverWait(driver, EXPLICIT_WAIT);
		// Initialize page elements using AjaxElementLocatorFactory
		PageFactory.initElements(new AjaxElementLocatorFactory(driver, TIME_OUT), this);
	}

	/**
	 * Method to click on an element identified by XPath.
	 * @param xpathExpression The XPath expression to locate the element.
	 */
	protected void click(String xpathExpression) {
		waitForElement(By.xpath(xpathExpression)).click();
	}

	/**
	 * Method to clear the text field and enter new text.
	 * @param element The WebElement to clear and enter text into.
	 * @param text The text to enter.
	 */
	protected void clearAndEnterText(WebElement element, String text) {
		element.clear();
		element.sendKeys(text);
	}

	/**
	 * Method to wait for the visibility of an element identified by a locator.
	 * @param locator The locator to identify the element.
	 * @return The WebElement that is visible.
	 */
	protected WebElement waitForElement(By locator) {
		return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	/**
	 * Method to wait for the invisibility of an element.
	 * @param element The WebElement to wait for invisibility.
	 */
	protected void waitForInvisibilityElement(WebElement element) {
		wait.until(ExpectedConditions.invisibilityOf(element));
	}

	/**
	 * Method to pause the execution for a specified number of seconds.
	 * @param sec The number of seconds to pause.
	 */
	protected void pause(int sec) {
		try {
			Thread.sleep(sec * 1000L);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException("Interrupted while pausing", e);
		}
	}

	/**
	 * Method to accept an alert dialog and pause for 2 seconds.
	 */
	public void acceptAlert() {
		Alert alert = wait.until(ExpectedConditions.alertIsPresent());
		alert.accept();
		pause(2);
	}

	/**
	 * Method to check if an element is not selected, then click it.
	 * @param locator The locator to identify the element.
	 */
	protected void checkAndClick(By locator) {
		if (!isElementSelected(locator))
			waitForElement(locator).click();
	}

	/**
	 * Method to check if an element identified by a locator is selected.
	 * @param locator The locator to identify the element.
	 * @return true if the element is selected, false otherwise.
	 */
	protected boolean isElementSelected(By locator) {
		try {
			return waitForElement(locator).isSelected();
		} catch (NoSuchElementException | TimeoutException e) {
			return false;
		}
	}

	/**
	 * Method to check if an element identified by a locator is displayed.
	 * @param locator The locator to identify the element.
	 * @return true if the element is displayed, false otherwise.
	 */
	protected boolean isElementDisplayed(By locator) {
		try {
			return waitForElement(locator).isDisplayed();
		} catch (NoSuchElementException | TimeoutException e) {
			return false;
		}
	}

	/**
	 * Method to check if an element is displayed.
	 * @param element The WebElement to check.
	 * @return true if the element is displayed, false otherwise.
	 */
	protected boolean isElementDisplayed(WebElement element) {
		try {
			return element.isDisplayed();
		} catch (NoSuchElementException | StaleElementReferenceException e) {
			return false;
		}
	}

	/**
	 * Method to select an option by visible text and return the selected text.
	 * @param element The WebElement representing the dropdown.
	 * @param text The visible text of the option to select.
	 * @return The selected text.
	 */
	protected String selectByText(WebElement element, String text) {
		Select select = new Select(element);
		select.selectByVisibleText(text);
		return select.getFirstSelectedOption().getText();
	}

	/**
	 * Method to select an option by index and return the selected text.
	 * @param element The WebElement representing the dropdown.
	 * @param index The index of the option to select.
	 * @return The selected text.
	 */
	protected String selectByIndex(WebElement element, int index) {
		Select select = new Select(element);
		select.selectByIndex(index);
		return select.getFirstSelectedOption().getText();
	}

	/**
	 * Method to wait for the page to load completely.
	 */
	public void waitForPageLoad() {
		pause(1);
		wait.until(driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").equals("complete"));
	}

	/**
	 * Method to switch to a new window and return the parent window handle.
	 * @return The handle of the parent window.
	 */
	public String switchToNewWindow() {
		String currentWindow = driver.getWindowHandle();
		for (String handle : driver.getWindowHandles()) {
			if (!currentWindow.equals(handle)) {
				driver.switchTo().window(handle);
				return currentWindow;
			}
		}
		throw new NoSuchElementException("No new window found");
	}

	/**
	 * Method to close the current window and switch back to the parent window.
	 * @param parentWindow The handle of the parent window.
	 */
	public void closeAndSwitchToParentWindow(String parentWindow) {
		driver.close();
		driver.switchTo().window(parentWindow);
	}

	/**
	 * Method to perform a click action on an element using the Actions class.
	 * @param element The WebElement to click.
	 */
	protected void clickAction(WebElement element) {
		new Actions(driver).moveToElement(element).click().build().perform();
	}

	/**
	 * Method to get the order number from an element's text.
	 * @param element The WebElement containing the order number.
	 * @return The order number.
	 */
	protected String getOrderNumber(WebElement element) {
		return element.getText().trim().replaceAll("[^0-9]", "");
	}
}