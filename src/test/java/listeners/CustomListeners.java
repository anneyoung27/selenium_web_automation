package listeners;

import base.TestBase;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import utilities.ExcelReader;
import utilities.ExtentManager;
import utilities.TestUtil;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomListeners extends TestBase implements ITestListener {

    static String fileName = "Extent_" + new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date()) + ".html";

    private static final ExtentReports extentReports = ExtentManager.createInstance("./reports/"+fileName);
    public  static ExtentTest extentTest;

    @Override
    public void onTestStart(ITestResult result) {
        String testCaseName = result.getMethod().getMethodName();
        String tcID = ExcelReader.getTestCaseID(testCaseName, excel);

        extentTest = extentReports.createTest(tcID + " - " + testCaseName);
        extentTest.info("Test started for " + testCaseName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String logText = "<b>"+"Test Case: "+methodName.toUpperCase()+" - PASSED"+"</b>";
        extentTest.log(Status.PASS, logText);
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String methodName = result.getMethod().getMethodName();
        String logText = "<b>"+"Test Case: "+methodName.toUpperCase()+" - SKIPPED"+"</b>";
        extentTest.log(Status.SKIP, logText);
    }

    @Override
    public void onTestFailure(ITestResult result) {
        String screenShootPath;

        Throwable throwable = result.getThrowable();
        String exceptionName = throwable.getClass().getSimpleName();
        String exceptionMessage = throwable.getMessage();

        String methodName = result.getMethod().getMethodName();
        String logText = "<b>Test Case: " + methodName.toUpperCase() + " - FAILED</b><br>" +
                "Verification failed with exception: " + exceptionName + "<br>" + "=> " +
                exceptionMessage;

        try {
            extentTest.log(Status.FAIL, logText);
            screenShootPath = TestUtil.captureScreenshot(methodName);

            MediaEntityBuilder.createScreenCaptureFromPath(screenShootPath).build();
            extentTest.fail("Screenshot of failure:",
                    MediaEntityBuilder.createScreenCaptureFromPath(screenShootPath).build());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onFinish(ITestContext context) {
        try {
            if(extentReports != null){
                extentReports.flush();
                log.info("Report flush successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
