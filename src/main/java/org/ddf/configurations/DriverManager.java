package org.ddf.configurations;

import org.openqa.selenium.WebDriver;

/**
 * This class manages the WebDriver instances for each thread in the test automation framework.
 */
public class DriverManager {
	private static volatile DriverManager DRIVER_MANAGER;
	private static final ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();

	private DriverManager() {
		// Private constructor to prevent external instantiation
	}

	/**
	 * Get the singleton instance of DriverManager.
	 * @return The instance of DriverManager.
	 */
	public static DriverManager getInstance() {
		if (DRIVER_MANAGER == null) {
			synchronized (DriverManager.class) {
				if (DRIVER_MANAGER == null) {
					DRIVER_MANAGER = new DriverManager();
				}
			}
		}
		return DRIVER_MANAGER;
	}

	/**
	 * Set the WebDriver instance for the current thread.
	 * @param driver The WebDriver instance to set.
	 */
	public synchronized void setDriver(WebDriver driver) {
		threadDriver.set(driver);
	}

	/**
	 * Get the WebDriver instance for the current thread.
	 * @return The WebDriver instance.
	 * @throws IllegalStateException if the driver is null.
	 */
	public static synchronized WebDriver getDriver() {
		WebDriver driver = threadDriver.get();
		if (driver == null) {
			throw new IllegalStateException("Driver should not be null!!");
		}
		return driver;
	}

	/**
	 * Remove the WebDriver instance for the current thread.
	 */
	public static synchronized void removeDriver() {
		threadDriver.remove();
	}

	/**
	 * Quit the WebDriver instance for the current thread.
	 */
	public static void quitDriver() {
		WebDriver driver = getDriver();
		driver.quit();
		removeDriver();
	}
}