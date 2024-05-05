package org.ddf.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.aventstack.extentreports.Status;
import org.ddf.reports.ReportManager;

/**
 * This class provides logging functionality and integration with Allure and ExtentReports.
 */
public class Log {
    private static final Logger Log =  LogManager.getLogger(org.ddf.utils.Log.class.getName());

    /**
     * Log an information message and report it to ExtentReports.
     * @param message The information message to log.
     */
    public static void info(String message) {
        Log.info(message);
        ReportManager.getTest().log(Status.INFO, message);
    }

    /**
     * Log an error message.
     * @param message The error message to log.
     */
    public static void error(String message) {
        Log.error(message);
    }

    /**
     * Log a message with a specific format.
     * @param message The message to log.
     */
    public static void messageLog(String message) {
        Log.info("============================================================================================================");
        Log.info("\t\t\t{}", message.toUpperCase());
        Log.info("============================================================================================================");
    }
}