package testCases.BankManager;

import base.TestBase;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.SkipException;
import org.testng.annotations.Test;
import utilities.TestUtil;

import java.util.Hashtable;


public class AddCustomerTest extends TestBase {

    @Test(dataProviderClass = TestUtil.class, dataProvider = "dp", description = "To verify if User can register new customer as an Bank Manager")
    public void addCustomerTest(Hashtable<String, String> data)  {
        log.info("Inside Add New Customer Test");

        if (!TestUtil.isTestRunnable(this.getClass().getSimpleName(), excel) || !"Y".equals(data.get("RunMode"))) {
            throw new SkipException("Skipping the test case as the Run mode for data set is N");
        }

        click("addCustBtn_CSS");
        type("firstname_CSS", data.get("FirstName"));
        type("lastname_XPATH", data.get("LastName"));
        type("postcode_CSS", data.get("PostCode"));
        click("addbtn_CSS");

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
        log.info("Alert clicked!");
        log.info("Add New Customer successfully!");
    }


    /*
    @DataProvider
    public Object[][] getData(){
        String sheetName = "addNewCustomer";

        int rows = excel.getRowCount(sheetName);
        int cols = excel.getColumnCount(sheetName);

        Object[][] data = new Object[rows - 1][cols];

        for (int rowNum = 2; rowNum <= rows; rowNum++){
            for (int colNum = 0; colNum < cols; colNum++){
                data[rowNum - 2][colNum] = excel.getCellData(sheetName, colNum, rowNum);
            }
        }
        return data;
    }
     */
}


