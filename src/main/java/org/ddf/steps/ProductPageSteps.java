package org.ddf.steps;

import org.ddf.utils.Log;
import org.openqa.selenium.WebDriver;
import org.ddf.pages.ProductPage;
import static org.ddf.configurations.GlobalVariables.PRODUCT_TYPE;

public class ProductPageSteps {

    private final ProductPage productPage;

    public ProductPageSteps(WebDriver driver) {
        this.productPage = new ProductPage(driver);
    }

    /**
     * Enter details for Gift Card.
     *
     * @param recipientName  The recipient's name.
     * @param recipientEmail The recipient's email.
     * @param senderName    The sender's name.
     * @param senderEmail   The sender's email.
     * @param message      The message to be sent with the gift card.
     */
    public void enterGiftCardDetails(String recipientName, String recipientEmail, String senderName, String senderEmail, String message) {
        Log.info("Entering details for Gift Card...");
        productPage.enterRecipientName(recipientName);
        productPage.enterSenderName(senderName);
        if (productPage.isRecipientEmailDisplayed()) {
            productPage.enterRecipientEmail(recipientEmail);
            productPage.enterSenderEmail(senderEmail);
        }
        productPage.enterMessage(message);
        Log.info("Gift Card details entered successfully.");
    }

    /**
     * Update the product quantity.
     *
     * @param quantity The new quantity to be set for the product.
     */
    public void updateQuantity(String quantity) {
        Log.info("Updating product quantity...");
        productPage.enterQuantity(quantity);
        Log.info("Product quantity updated successfully.");
    }

    /**
     * Add the product to the cart.
     */
    public void addProductToCart() {
        Log.info("Adding product to cart...");
        productPage.clickAddToCartButton();
        Log.info("Product added to cart successfully.");
    }

    /**
     * Enter product details.
     *
     * @param productType  The type of product.
     * @param size        The size of the product.
     * @param color       The color of the product.
     * @param length      The length of the product.
     * @param pendant     The pendant of the product.
     */
    public void enterProductDetails(String productType, String size, String color, String length, String pendant) {
        Log.info("Entering product details...");
        productPage.selectSize(size);
        boolean colorOptionDisplayed = productPage.isColorOptionDisplayed(color);
        if (productType.equalsIgnoreCase(PRODUCT_TYPE)) {
            productPage.enterLength(length);
            productPage.choosePendant(pendant);
        } else if (colorOptionDisplayed) {
            productPage.chooseColor(color);
        }
        Log.info("Product details entered successfully.");
    }

    /**
     * Enter computer specifications.
     *
     * @param processor  The processor of the computer.
     * @param ram        The RAM of the computer.
     * @param hdd        The HDD of the computer.
     * @param os         The operating system of the computer.
     * @param softwares  The software to be installed on the computer.
     */
    public void enterComputerSpecification(String processor, String ram, String hdd, String os, String softwares) {
        Log.info("Entering computer specifications...");
        productPage.chooseHDD(hdd);
        if (productPage.isProcessorRadioButtonDisplayed(processor)) {
            productPage.chooseProcessor(processor);
        } else {
            productPage.selectProcessor(processor);
        }
        if (productPage.isRAMRadioButtonDisplayed(ram)) {
            productPage.chooseRAM(ram);
        } else {
            productPage.selectRAM(ram);
        }
        if (os != null && !os.isEmpty()) {
            productPage.chooseOS(os);
        }
        for (String software : softwares.split("\\|")) {
            productPage.chooseSoftware(software);
        }
        Log.info("Computer specifications entered successfully.");
    }
}
