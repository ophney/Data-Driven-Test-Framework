package org.ddf.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * This class represents the Product Page of the application.
 * It contains methods to interact with elements on the product page.
 */
public class ProductPage extends BasePage {

	public ProductPage(WebDriver driver) {
		super(driver);
	}

	@FindBy(css = ".add-to-cart-button")
	private WebElement addToCartButton;

	@FindBy(css = ".qty-input")
	private WebElement quantityTextbox;

	@FindBy(className = "recipient-name")
	private WebElement recipientNameTextbox;

	@FindBy(className = "sender-name")
	private WebElement senderNameTextbox;

	@FindBy(className = "message")
	private WebElement messageTextarea;

	@FindBy(className = "recipient-email")
	private WebElement recipientEmailTextbox;

	@FindBy(className = "sender-email")
	private WebElement senderEmailTextbox;

	@FindBy(xpath = "//select[contains(@id,'product_attribute')]")
	private WebElement sizeDropdown;

	@FindBy(xpath = "//input[contains(@id,'product_attribute')][@type='text']")
	private WebElement lengthTextbox;

	@FindBy(xpath = "//label[contains(text(),'Processor')]/../following-sibling::dd[1]//select")
	private WebElement processorDropdown;

	@FindBy(xpath = "//label[contains(text(),'RAM')]/../following-sibling::dd[1]//select")
	private WebElement ramDropdown;

	private static final String COLOR_XPATH = "//*[@class='color-container'][@title='%s']";
	private static final String PENDANT_XPATH = "//label[contains(text(),'%s')]/../input";
	private static final String PROCESSOR_XPATH = "//label[contains(text(),'Processor')]/../following-sibling::dd[1]//label[contains(text(),'%s')]";
	private static final String RAM_XPATH = "//label[contains(text(),'RAM')]/../following-sibling::dd[1]//label[contains(text(),'%s')]";
	private static final String HDD_XPATH = "//label[contains(text(),'HDD')]/../following-sibling::dd[1]//label[contains(text(),'%s')]";
	private static final String SOFTWARE_XPATH = "//label[contains(text(),'%s')]/preceding-sibling::input[@type='checkbox']";
	private static final String OS_XPATH = "//label[contains(text(),'OS')]/../following-sibling::dd[1]//label[contains(text(),'%s')]";

	/**
	 * Method to enter the quantity.
	 * @param quantity The quantity to enter.
	 */
	public void enterQuantity(String quantity) {
		clearAndEnterText(quantityTextbox, quantity);
	}

	/**
	 * Method to enter the recipient name.
	 * @param name The recipient name to enter.
	 */
	public void enterRecipientName(String name) {
		clearAndEnterText(recipientNameTextbox, name);
	}

	/**
	 * Method to check if the recipient email is displayed.
	 * @return true if the recipient email is displayed, false otherwise.
	 */
	public boolean isRecipientEmailDisplayed() {
		return isElementDisplayed(recipientEmailTextbox);
	}

	/**
	 * Method to enter the recipient email.
	 * @param email The recipient email to enter.
	 */
	public void enterRecipientEmail(String email) {
		clearAndEnterText(recipientEmailTextbox, email);
	}

	/**
	 * Method to enter the sender name.
	 * @param name The sender name to enter.
	 */
	public void enterSenderName(String name) {
		clearAndEnterText(senderNameTextbox, name);
	}

	/**
	 * Method to enter the sender email.
	 * @param email The sender email to enter.
	 */
	public void enterSenderEmail(String email) {
		clearAndEnterText(senderEmailTextbox, email);
	}

	/**
	 * Method to enter a message.
	 * @param message The message to enter.
	 */
	public void enterMessage(String message) {
		clearAndEnterText(messageTextarea, message);
	}

	/**
	 * Method to click the "Add to Cart" button.
	 */
	public void clickAddToCartButton() {
		addToCartButton.click();
	}

	/**
	 * Method to select a size from the dropdown.
	 * @param size The size to select.
	 */
	public void selectSize(String size) {
		selectByText(sizeDropdown, size);
	}

	/**
	 * Method to enter the length.
	 * @param length The length to enter.
	 */
	public void enterLength(String length) {
		lengthTextbox.sendKeys(length);
	}

	/**
	 * Method to check if a color option is displayed.
	 * @param color The color to check.
	 * @return true if the color option is displayed, false otherwise.
	 */
	public boolean isColorOptionDisplayed(String color) {
		return isElementDisplayed(By.xpath(String.format(COLOR_XPATH, color)));
	}

	/**
	 * Method to choose a color.
	 * @param color The color to choose.
	 */
	public void chooseColor(String color) {
		click(String.format(COLOR_XPATH, color));
	}

	/**
	 * Method to choose a pendant.
	 * @param pendant The pendant to choose.
	 */
	public void choosePendant(String pendant) {
		click(String.format(PENDANT_XPATH, pendant));
	}

	/**
	 * Method to check if a processor radio button is displayed.
	 * @param processor The processor to check.
	 * @return true if the processor radio button is displayed, false otherwise.
	 */
	public boolean isProcessorRadioButtonDisplayed(String processor) {
		return isElementDisplayed(By.xpath(String.format(PROCESSOR_XPATH, processor)));
	}

	/**
	 * Method to check if a RAM radio button is displayed.
	 * @param ram The RAM to check.
	 * @return true if the RAM radio button is displayed, false otherwise.
	 */
	public boolean isRAMRadioButtonDisplayed(String ram) {
		return isElementDisplayed(By.xpath(String.format(RAM_XPATH, ram)));
	}

	/**
	 * Method to choose a processor.
	 * @param processor The processor to choose.
	 */
	public void chooseProcessor(String processor) {
		click(String.format(PROCESSOR_XPATH, processor));
	}

	/**
	 * Method to choose a RAM.
	 * @param ram The RAM to choose.
	 */
	public void chooseRAM(String ram) {
		click(String.format(RAM_XPATH, ram));
	}

	/**
	 * Method to choose a HDD.
	 * @param hdd The HDD to choose.
	 */
	public void chooseHDD(String hdd) {
		click(String.format(HDD_XPATH, hdd));
	}

	/**
	 * Method to choose a software option.
	 * @param software The software option to choose.
	 */
	public void chooseSoftware(String software) {
		checkAndClick(By.xpath(String.format(SOFTWARE_XPATH, software)));
	}

	/**
	 * Method to choose an OS option.
	 * @param os The OS option to choose.
	 */
	public void chooseOS(String os) {
		click(String.format(OS_XPATH, os));
	}

	/**
	 * Method to select a processor from the dropdown.
	 * @param processor The processor to select.
	 */
	public void selectProcessor(String processor) {
		selectByText(processorDropdown, processor);
	}

	/**
	 * Method to select a RAM from the dropdown.
	 * @param ram The RAM to select.
	 */
	public void selectRAM(String ram) {
		selectByText(ramDropdown, ram);
	}
}