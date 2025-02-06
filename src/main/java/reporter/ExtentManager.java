package reporter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import java.io.IOException;
/**
 * Standard Extent Report Class where all the required attributes for the reporter needs to be set.
 * */
public class ExtentManager {
    private static ExtentReports extent;

    public static ExtentReports getInstance() {
        if (extent == null)
            createInstance();
        return extent;
    }

    public static ExtentReports createInstance() {
        try {
            ExtentSparkReporter htmlReporter = new ExtentSparkReporter("result/reportandlogs/ColUIAutoReport.html");
            htmlReporter.config().setTheme(Theme.STANDARD);
            extent = new ExtentReports();
            extent.attachReporter(htmlReporter);
            htmlReporter.loadXMLConfig("src/main/resources/extent-config.xml");
            htmlReporter.config().setDocumentTitle("Spocto");
            htmlReporter.config().setReportName("Collections UI Automation Tests");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return extent;
    }
}