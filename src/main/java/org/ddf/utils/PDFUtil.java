package org.ddf.utils;

import java.io.File;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.ddf.configurations.GlobalVariables;

/**
 * Utility class for reading text from PDF files.
 */
public class PDFUtil implements GlobalVariables {

	private static final Logger LOG = LogManager.getLogger(PDFUtil.class);

	/**
	 * Reads text from the specified PDF file.
	 *
	 * @param fileName The name of the PDF file to read.
	 * @return The text content of the PDF file.
	 * @throws InterruptedException If interrupted while waiting for download completion.
	 */
	public synchronized static String read(String fileName) throws InterruptedException {
		String text = null;
		try {
			// Create a File object for the PDF file
			File pdfFile = new File(DOWNLOAD_FOLDER.concat(fileName));
			// Wait until the download is completed
			waitUntilDownloadCompleted(pdfFile);
			// Load the PDF document
			PDDocument doc = PDDocument.load(pdfFile);
			// Extract text from the PDF document
			text = new PDFTextStripper().getText(doc);
			// Log the extracted text
			LOG.info(text);
		} catch (IOException e) {
			// Log an error if there's an exception while reading the file
			LOG.error("Error while reading {} file", fileName, e);
		}
		return text;
	}

	/**
	 * Waits until the download of the PDF file is completed.
	 *
	 * @param pdfFile The PDF file being downloaded.
	 * @throws InterruptedException If interrupted while waiting.
	 */
	private static void waitUntilDownloadCompleted(File pdfFile) throws InterruptedException {
		// Loop until the file exists or until the maximum wait time is reached
		for (int i = 0; i < DOWNLOAD_WAIT / 10; i++) {
			if (pdfFile.exists())
				break;
			Thread.sleep(5000); // Wait for 5 seconds
			System.out.println("Waiting for file download");
		}
	}
}
