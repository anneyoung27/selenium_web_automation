package rough;

import base.TestBase;
import utilities.ExcelReader;

public class testExecute extends TestBase {

    public static void main(String[] args) {
        isTestRunnable("", excel);
    }

    public static boolean isTestRunnable(String testName, ExcelReader excel){
        String sheetName="TestSuite"; // sheet name
        int rows = excel.getRowCount(sheetName);

        System.out.println("Total rows: " + rows);
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

