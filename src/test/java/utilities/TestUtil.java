package utilities;

import base.TestBase;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.Hashtable;

public class TestUtil extends TestBase {
    public static String screenshotPath;
    public static String screenshotName;


    public static String captureScreenshot(String methodName) throws IOException {
        File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

        Date d = new Date();
        screenshotName = d.toString().replace(":", "_").replace(" ", "_");
        screenshotPath = "screenshots/" + methodName +"_"+ screenshotName + ".png";

        try{
            Files.createDirectories(Paths.get("./reports/screenshots"));
            Files.copy(scrFile.toPath(), Paths.get("./reports/" + screenshotPath));
        }catch (IOException e){
            e.printStackTrace();
        }
        return screenshotPath;
    }

    @DataProvider(name="dp")
    public Object[][] generateData(Method m) {
        String sheetName = m.getName(); // karena menggunakan module Method, pastikan nama Sheet di Excel sesuai dengan nama method di class Test

        int rows = excel.getRowCount(sheetName);
        int cols = excel.getColumnCount(sheetName);

        Object[][] data = new Object[rows - 1][1];

        Hashtable<String,String> table = null;

        for (int rowNum = 2; rowNum <= rows; rowNum++) { // 2
            table = new Hashtable<>();
            for (int colNum = 0; colNum < cols; colNum++) {
                // data[0][0]
                table.put(excel.getCellData(sheetName, colNum, 1), excel.getCellData(sheetName, colNum, rowNum));
                data[rowNum - 2][0] = table;
            }
        }
        return data;
    }

    @DataProvider(name = "vd")
    public Object[][] searchValidData() {
        return new Object[][] {
                { "Harry" },
                { "Dumbledore" },
                { "E89898" }
        };
    }

    @DataProvider(name = "id")
    public Object[][] searchInvalidData() {
        return new Object[][] {
                { "$$" },
                { "AZ" },
                { "XX" },
                { "E999*?" }
        };
    }

    @DataProvider(name = "deleteData")
    public Object[][] deleteSpecificData() {
        return new Object[][] {
                { "Harry" },
                { "E55555" },
                { "Dumbledore" }
        };
    }

    public static boolean isTestRunnable(String testName, ExcelReader excel){
        String sheetName="TestSuite"; // sheet name
        int rows = excel.getRowCount(sheetName);

        for(int rNum=2; rNum<=rows; rNum++){
            String testCase = excel.getCellData(sheetName, "TestCaseName", rNum);
            if(testCase.equalsIgnoreCase(testName)){
                String runMode = excel.getCellData(sheetName, "RunMode", rNum);
                return runMode.equalsIgnoreCase("Y");
            }
        }
        return false;
    }
}