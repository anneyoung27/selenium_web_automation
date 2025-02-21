package testCases.BankManager;

import base.TestBase;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import utilities.TestUtil;

import java.util.ArrayList;
import java.util.List;

public class CustomerPageListTest extends TestBase {



    @Test(description = "To verify customer list is visible")
    public void customerPageListTest(){
        log.info("Inside customer list is visible test");

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        // if RunMode = Y
        if(!TestUtil.isTestRunnable(methodName, excel)){
            throw new SkipException("Skipping the test case as the Run mode for data set is N");
        }

        click("customerTab_CSS");

        visibilityOfElementLocated("customerTable_XPATH");
        List<WebElement> customerList = new ArrayList<>();

        for (WebElement element : findElements("customerTableData_XPATH")) {
            if (element.isDisplayed()) {
                customerList.add(element);
            }
        }
        int customerCountData = customerList.size();
        if (customerCountData > 0){
            Assert.assertTrue(true);

        }else{
            Assert.assertFalse(false);
            log.info("Customer not found");
        }
        log.info(customerCountData +" customer(s) found");
    }

    @Test(description = "To verify table headers should display \"First Name\", \"Last Name\", \"Post Code\", \"Account Number\"", dependsOnMethods = "customerPageListTest")
    public void customerHeaderListTest(){
        log.info("inside customer header list test");

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        // if RunMode = Y
        if(!TestUtil.isTestRunnable(methodName, excel)){
            throw new SkipException("Skipping the test case as the Run mode for data set is N");
        }

        List<WebElement> tableHeaders = findElements("customerTableHeader_XPATH");
        String [] expectedHeaders = {"First Name", "Last Name", "Post Code", "Account Number", "Delete Customer"};

        int currTableHeaderSize = tableHeaders.size();
        int expTableHeaderSize = expectedHeaders.length;

        verifyEquals(currTableHeaderSize, expTableHeaderSize);

        boolean headerMatch = true;
        for (int i = 0; i < expectedHeaders.length; i++){
            String actualHeaders = tableHeaders.get(i).getText().trim();
            if (!actualHeaders.equalsIgnoreCase(expectedHeaders[i])){
                headerMatch = false;
                log.warning("Header mismatch! Expected ["+expectedHeaders[i]+"] but found ["+actualHeaders+"]");
            }
            System.out.println("Actual headers are: ["+actualHeaders+"]");
        }

        if (headerMatch){
            log.info("All table headers are correct");
        }else{
            log.warning("One or more table headers are incorrect.");
        }
    }
}
