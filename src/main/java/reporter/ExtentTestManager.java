package reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import java.util.HashMap;
import java.util.Map;
/**
 * Standard Extent Test Manager Class to handle Test creation and addition to the report. Handles parallel execution as well.
 * */
public class ExtentTestManager {
    static Map<Integer, ExtentTest> extentTestMap = new HashMap<>();
    static final ExtentReports extent = ExtentManager.getInstance();
    public static synchronized ExtentTest getTest() {
        return extentTestMap.get((int) Thread.currentThread().threadId());
    }

    public static synchronized void endTest() {
        extent.flush();
    }
    public static synchronized ExtentTest startTest(String testName) {
        ExtentTest test = extent.createTest(testName);
        extentTestMap.put((int) Thread.currentThread().threadId(), test);
        return test;
    }
}
