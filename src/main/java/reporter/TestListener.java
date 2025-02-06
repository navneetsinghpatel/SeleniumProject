package reporter;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import org.apache.logging.log4j.ThreadContext;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static constants.Constants.SCREENSHOT_FOLDER_PATH;
import static utils.LocatorUtils.takeScreenshot;

public class TestListener implements ITestListener {
    private static final Logger logger = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("Starting test: " + result.getMethod().getMethodName());
        /*ExtentTestManager.startTest(result.getTestClass().getRealClass().getSimpleName()
                + " : " + result.getMethod().getMethodName());*/
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("Test passed: " + result.getMethod().getMethodName());
        /*ExtentTestManager.getTest().log(Status.PASS, MarkupHelper.createCodeBlock(getTestDescription(result)));*/
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String methodName = result.getMethod().getMethodName() + "_" + Thread.currentThread().getName();
        logger.error("Test failed: " + methodName, result.getThrowable());
        /*try {
            takeScreenshot(methodName + ".png");
            logger.info("****---- Test " + methodName + " Failed----****");
            if (null != result.getThrowable()) {
                ExtentTestManager.getTest().log(Status.FAIL, MarkupHelper.createCodeBlock(getTestDescription(result), result.getThrowable().getMessage()))
                        .addScreenCaptureFromPath("screenshots/" + methodName + ".png");
            }else {
                ExtentTestManager.getTest().log(Status.FAIL, MarkupHelper.createCodeBlock(getTestDescription(result)))
                        .addScreenCaptureFromPath("screenshots/" + methodName + ".png");
            }
        }catch (Exception e){
            e.printStackTrace();
        }*/
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.info("Test skipped: " + result.getMethod().getMethodName());
        /*ExtentTestManager.getTest().log(Status.SKIP, MarkupHelper.createCodeBlock(getTestDescription(result)));*/
    }

    @Override
    public void onStart(ITestContext context) {
        String testGroupName = context.getCurrentXmlTest().getName();
        ThreadContext.put("testGroupName", testGroupName); // This will dynamically change the log file name
        logger.info("Starting execution of test suite: " + context.getName());
    }

    @Override
    public void onFinish(ITestContext context) {
        logger.info("Completed execution of test suite: " + context.getName());
        /*ExtentTestManager.endTest();*/
    }

    private String getTestDescription(ITestResult result){
        String description = result.getMethod().getDescription();
        return !description.isEmpty() ? description : "No Test Description Provided";
    }
}

