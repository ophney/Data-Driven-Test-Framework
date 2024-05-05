package org.ddf.utils;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.ddf.configurations.GlobalVariables;

/**
 * This class provides utility methods for reading data from Excel files.
 */
public class ExcelUtil implements GlobalVariables {

	private XSSFWorkbook excelWorkbook;
	private static final Logger log = LogManager.getLogger(ExcelUtil.class);

	/**
	 * Set the Excel file to work with.
	 * @param sheetPath The path of the Excel file.
	 */
	public synchronized void setExcelFile(String sheetPath) {
		try {
			FileInputStream excelFile = new FileInputStream(sheetPath);
			excelWorkbook = new XSSFWorkbook(excelFile);
		} catch (Exception exp) {
			log.info("Exception occurred in setExcelFile: {}", sheetPath, exp);
		}
	}

	/**
	 * Get the number of rows in a specific sheet.
	 * @param sheetName The name of the sheet.
	 * @return The number of rows in the sheet.
	 */
	public synchronized int getNumberOfRows(String sheetName) {
		XSSFSheet excelSheet = excelWorkbook.getSheet(sheetName);
		return excelSheet.getPhysicalNumberOfRows();
	}

	/**
	 * Get the data from a specific cell in a sheet.
	 * @param rowNumb The row number.
	 * @param colNumb The column number.
	 * @param sheetName The name of the sheet.
	 * @return The data in the cell as a string.
	 */
	public synchronized String getCellData(int rowNumb, int colNumb, String sheetName) {
		try {
			XSSFSheet excelSheet = excelWorkbook.getSheet(sheetName);
			XSSFCell cell = excelSheet.getRow(rowNumb).getCell(colNumb);
			if (cell.getCellType() == CellType.NUMERIC) {
				cell.setCellType(CellType.STRING);
			}
			return cell.getStringCellValue();
		} catch (Exception exp) {
			return "";
		}
	}

	/**
	 * Get a list of test scenarios to run based on the run mode column.
	 * @param sheetName The name of the sheet.
	 * @param runModeColumn The column number for the run mode.
	 * @param testCaseColumn The column number for the test case.
	 * @return A list of test scenarios to run.
	 */
	public synchronized List<String> getScenariosToRun(String sheetName, int runModeColumn, int testCaseColumn) {
		List<String> testList = new ArrayList<>();
		try {
			int rowCount = getNumberOfRows(sheetName);
			String testCase;
			for (int i = 1; i < rowCount; i++) {
				testCase = getTestScenario(i, runModeColumn, testCaseColumn, sheetName);
				if (testCase != null) {
					testList.add(testCase);
				}
			}
		} catch (Exception e) {
			log.error("Exception Occurred while adding data to List:\n", e);
		}
		return testList;
	}

	private String getTestScenario(int row, int runModeColumn, int testCaseColumn, String sheetName) {
		String testCaseName = null;
		try {
			if (getCellData(row, runModeColumn, sheetName).equalsIgnoreCase(RUN_MODE_YES)) {
				testCaseName = getCellData(row, testCaseColumn, sheetName).trim();
				log.debug("Test Case to Run: {}", testCaseName);
			}
		} catch (Exception exp) {
			log.error("Exception occurred in getTestCaseRow: ", exp);
		}
		return testCaseName;
	}

	/**
	 * Get data from a specific row in a sheet as a map.
	 * @param sheetName The name of the sheet.
	 * @param row The row number.
	 * @return A map of data with column headers as keys.
	 */
	public Map<String, String> getData(String sheetName, int row) {
		Map<String, String> dataMap = new HashMap<>();
		XSSFSheet excelSheet = excelWorkbook.getSheet(sheetName);
		short lastColumn = excelSheet.getRow(row).getLastCellNum();
		for (int i = 0; i < lastColumn; i++) {
			dataMap.put(getCellData(0, i, sheetName), getCellData(row, i, sheetName));
		}
		return dataMap;
	}

	/**
	 * Main method for testing the ExcelUtil class.
	 * @param args Command line arguments.
	 * @throws Exception If an exception occurs.
	 */
	public static void main(String[] args) throws Exception {
		ExcelUtil excel = new ExcelUtil();
		excel.setExcelFile(DATA_FOLDER + WORKBOOK);
		System.out.println(excel.getScenariosToRun(SCENARIO_SHEET_NAME, RUN_MODE_COLUMN, TEST_CASE_COLUMN));
		for (String scenario : excel.getScenariosToRun(SCENARIO_SHEET_NAME, RUN_MODE_COLUMN, TEST_CASE_COLUMN)) {
			int testRow = excel.getNumberOfRows(scenario);
			for (int i = 1; i < testRow; i++) {
				System.out.println(excel.getData(scenario, i));
			}
		}
	}
}