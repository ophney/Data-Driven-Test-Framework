package org.ddf.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This class provides utility methods for managing directories.
 */
public class DirectoryUtil {
	private static final Logger log = LogManager.getLogger(DirectoryUtil.class.getName());

	/**
	 * Create a directory if it doesn't already exist.
	 * @param folderPath The path of the directory to create.
	 */
	public void create(String folderPath) {
		Path path = Paths.get(folderPath);
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
				log.info("Directory was created: {}", folderPath);
			} catch (IOException exp) {
				log.error("Failed to create directory: {}", folderPath, exp);
			}
		} else {
			log.info("Directory already exists: {}", folderPath);
		}
	}

	/**
	 * Delete all files and subdirectories within a directory.
	 * @param folderPath The path of the directory to delete.
	 */
	public void delete(String folderPath) {
		File folder = new File(folderPath);
		if (folder.exists()) {
			try {
				FileUtils.cleanDirectory(folder);
				log.info("All files and subdirectories within the directory were deleted: {}", folderPath);
			} catch (IOException exp) {
				log.error("Failed to delete files and subdirectories within the directory: {}", folderPath, exp);
			}
		} else {
			log.info("Directory does not exist: {}", folderPath);
		}
	}

	/**
	 * Clear the content of a folder by deleting and recreating it.
	 * @param folderPath The path of the folder to clear.
	 */
	public void clearFolder(String folderPath) {
		log.info("Clearing content in the folder: {}", folderPath);
		delete(folderPath);
		create(folderPath);
	}
}