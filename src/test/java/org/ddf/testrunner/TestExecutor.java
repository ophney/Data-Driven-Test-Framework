package org.ddf.testrunner;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.testng.annotations.Factory;
import org.ddf.configurations.GlobalVariables;
import org.ddf.utils.ExcelUtil;

public class TestExecutor implements GlobalVariables {

	// This method is annotated with @Factory, indicating that it produces test instances
	@Factory
	public Object[] testCasesToExecute() {
		ArrayList<Object> tests = new ArrayList<>();
		try {
			// Create an instance of ExcelUtil to read test data from an Excel file
			ExcelUtil excelUtil = new ExcelUtil();
			// Set the Excel file to be used by ExcelUtil
			excelUtil.setExcelFile(DATA_FOLDER + WORKBOOK);
			// Iterate through scenarios to run based on test data
			for (String scenario : excelUtil.getScenariosToRun(SCENARIO_SHEET_NAME, RUN_MODE_COLUMN, TEST_CASE_COLUMN)) {
				// Create a test object for each scenario and add it to the test list
				tests.add(createTestObject(scenario));
			}
		} catch (Exception e) {
			// Print stack trace if any exception occurs during test setup
			e.printStackTrace();
		}
		// Convert the list of test objects to an array and return
		return tests.toArray();
	}

	// This method creates a test object dynamically using reflection
	private Object createTestObject(String testCase) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
		// Get the class object corresponding to the test case
		Class<?> testClass = Class.forName(String.format(TEST_PACKAGE, testCase));
		// Get the constructor of the class
		Constructor<?> constructor = testClass.getConstructor();
		// Create a new instance of the class using the constructor
		return constructor.newInstance();
	}
}
