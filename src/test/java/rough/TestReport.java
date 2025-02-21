package rough;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.File;

public class TestReport {
    public static void main(String[] args) {
        ExtentReports extent = new ExtentReports();
        ExtentSparkReporter spark = new ExtentSparkReporter("reports/index.html");
        extent.attachReporter(spark);

        extent.createTest("Test Example").pass("This is a sample test case.");
        extent.flush();

        File file = new File("reports/index.html");
        if (file.exists()) {
            System.out.println("Report successfully created at: " + file.getAbsolutePath());
        } else {
            System.out.println("Failed to create report file!");
        }
    }
}
