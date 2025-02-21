package utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentManager {
    private static ExtentReports extentReports;

    public static ExtentReports createInstance(String fileName){
        if (extentReports == null){
            ExtentSparkReporter htmlReporter = new ExtentSparkReporter(fileName);

            htmlReporter.config().setTheme(Theme.STANDARD);
            htmlReporter.config().setTheme(Theme.DARK);
            htmlReporter.config().setDocumentTitle(fileName);
            htmlReporter.config().setEncoding("UTF-8");
            htmlReporter.config().setReportName(fileName);
            htmlReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");


            extentReports = new ExtentReports();
            extentReports.attachReporter(htmlReporter);
            extentReports.setSystemInfo("Automation Tester", "Injas Mahendra Berutu");
            extentReports.setSystemInfo("Environment", "Independent Project for Automation Testing");
            extentReports.setSystemInfo("OS", System.getProperty("os.name"));
            extentReports.setSystemInfo("Java Version", System.getProperty("java.version"));
        }

        return extentReports;
    }

}
