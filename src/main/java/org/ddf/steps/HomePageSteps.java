package org.ddf.steps;

import static org.testng.Assert.assertTrue;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.ddf.utils.Log;
import org.openqa.selenium.WebDriver;
import org.testng.asserts.SoftAssert;

import org.ddf.pages.HomePage;


/**
 * This class contains steps related to the actions on the home page of the application.
 */
public class HomePageSteps {
    private final HomePage home;

    /**
     * Constructor for HomePageSteps class.
     * @param driver The WebDriver instance to be used for interacting with the home page.
     */
    public HomePageSteps(WebDriver driver) {
        this.home = new HomePage(driver);
    }

    /**
     * Method to navigate to the login page.
     */
    public void navigateToLoginPage() {
        Log.info("Navigating to login page");
        home.clickLoginLink();
    }

    /**
     * Method to navigate to the user account page.
     */
    public void navigateToUserAccount() {
        Log.info("Navigating to user account page");
        home.clickUserAccountLink();
    }

    /**
     * Method to logout of the shop.
     */
    public void logout() {
        Log.info("Logging out of the shop");
        home.clickLogoutLink();
        assertTrue(home.hasUserLoggedOut(), "User was not logged out");
    }

    /**
     * Method to validate the footer links on the home page.
     */
    public void validateFooterLinks() {
        Log.info("Validating footer links");
        SoftAssert softAssert = new SoftAssert();
        for(int i=0; i<home.getNumberOfFooterLinks(); i++) {
            String footerLink = home.getFooterLink(i);
            String footerLinkText = home.getFooterLinkText(i);
            if(isLinkBroken(footerLink)) {
                Log.error("Broken link found: " + footerLinkText + "-" + footerLink);
                softAssert.fail(footerLinkText + " link - " + footerLink + " is broken");
            } else {
                Log.info("Footer link validated successfully: " + footerLinkText + "-" + footerLink);
            }
        }
        softAssert.assertAll();
    }

    /**
     * Method to check if a given URL is broken.
     * @param url The URL to check.
     * @return true if the link is broken, false otherwise.
     */
    private boolean isLinkBroken(String url) {
        HttpURLConnection connection;
        if(url != null){
            try {
                connection = (HttpURLConnection)(new URL(url).openConnection());
                // Use HEAD request to check a link without downloading content
                connection.setRequestMethod("HEAD");
                connection.connect();
                int respCode = connection.getResponseCode();

                // Link is broken
                return respCode < HttpURLConnection.HTTP_OK || respCode >= HttpURLConnection.HTTP_BAD_REQUEST; // The Link is not broken
            } catch (IOException e) {
                Log.error("Error checking link: "+ url + e);
                return true;
            }
        }
        return true; // Return true if URL is null
    }

}