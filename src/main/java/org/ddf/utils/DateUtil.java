package org.ddf.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Utility class for date-related operations.
 */
public class DateUtil {

	/**
	 * Formats the current date to a string using the specified date format.
	 *
	 * @param dateFormat The format string to be used for formatting the date.
	 * @return A string representation of the current date formatted according to the specified format.
	 *         Returns null if an error occurs during formatting.
	 * @throws IllegalArgumentException If the provided date format is null or empty.
	 */
	public static String getStringDate(String dateFormat) {
		// Validate input
		if (dateFormat == null || dateFormat.isEmpty()) {
			throw new IllegalArgumentException("Date format must not be null or empty");
		}

		// Create a SimpleDateFormat object with the specified date format
		SimpleDateFormat dtf = new SimpleDateFormat(dateFormat);

		try {
			// Format the current date using the specified format
			return dtf.format(new Date());
		} catch (Exception e) {
			// Log or handle the exception appropriately
			System.err.println("Error occurred during date formatting: " + e.getMessage());
			return null; // Return null if an error occurs
		}
	}
}
