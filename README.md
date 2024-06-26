# DataDrivenFramework

## Overview:

The Data Driven framework is focused on separating the test scripts logic and the test data from each other. This allows us to create test automation scripts by passing different sets of test data. The test data set is kept in external files or resources such as MS Excel Sheets, MS Access Tables, SQL Database, XML files, etc. The test scripts connect to the external resources to get the test data. This framework significantly reduces the number of test scripts compared to a modular-based framework when we need to test for multiple sets of data for the same functionality.

For Demo purpose, all the test cases are created for [Demo Web Shop](http://demowebshop.tricentis.com/) site.

### Some of the key features of this framework:

1. Generates Extent reports with all the step details.
2. Support parallel execution of test cases.
3. Generates test execution log file.
4. Test execution can be triggered from the command line.
5. Easy integration to CI/CD pipeline like Jenkins.
6. Framework uses Page Object Design Pattern, hence there is clean separation between test code and page-specific code such as locators and layout.
7. Supports re-run of failed test cases.
8. Allows controlling the tests to be run using a sheet (Regression) in TestData.xlsx sheet.

## Required Setup:

- [Java v17](https://java.tutorials24x7.com/blog/how-to-install-java-17-on-windows) should be installed and configured.
- [Maven](https://mkyong.com/maven/how-to-install-maven-in-windows/) should be installed and configured.

## Running Test:

All the tests to be executed can be configured in TestData.xlsx sheet placed in the below path.

