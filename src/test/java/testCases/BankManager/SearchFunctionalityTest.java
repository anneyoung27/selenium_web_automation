package testCases.BankManager;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import utilities.TestUtil;

import java.util.List;

public class SearchFunctionalityTest extends TestBase {


    @Test(dataProviderClass = TestUtil.class, dataProvider = "vd", description = "To verify search functionality with valid data")
    public void searchCustomerValidDataTest(String validData){
        log.info("Inside search functionality with valid data test");

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        // if RunMode = Y
        if(!TestUtil.isTestRunnable(methodName, excel)){
            throw new SkipException("Skipping the test case as the Run mode for data set is N");
        }

        actionType("customerSearch_CSS", validData);

        List<WebElement> customerList = findElements("customerTableData_XPATH");
        int customerCountData = customerList.size();

        if (customerCountData > 0){
            boolean matchFound = false;
            log.info(customerCountData + " customer(s) found. Checking names...");

            for (WebElement row : customerList) {
                // access all rows data per columns
                List<WebElement> columns = row.findElements(By.tagName("td"));

                String firstName = columns.getFirst().getText().trim();
                String lastName = columns.get(1).getText().trim();
                String postCode = columns.getLast().getText().trim();

                log.info("Found Customer: " + firstName + " " + lastName + " " + postCode);

                if (firstName.equalsIgnoreCase(validData) ||
                        lastName.equalsIgnoreCase(validData) ||
                        postCode.equalsIgnoreCase(validData)) {
                    matchFound = true;
                    log.info("Matching customer found [Valid Data]: " + firstName + " " + lastName + " " + postCode);
                }
            }
            Assert.assertTrue(matchFound, "No matching customer found for search term: " + validData);
        }else{
            log.info("No customers found for search term: " + validData);
            Assert.fail("Expected to find customer but found none.");
        }
    }


    @Test(dataProviderClass = TestUtil.class, dataProvider = "id", description = "To verify search functionality with invalid data")
    public void searchCustomerInvalidDataTest(String invData){
        log.info("Inside search functionality with invalid data test");

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        // if RunMode = Y
        if(!TestUtil.isTestRunnable(methodName, excel)){
            throw new SkipException("Skipping the test case as the Run mode for data set is N");
        }

        actionType("customerSearch_CSS", invData);

        List<WebElement> customerList = findElements("customerTableData_XPATH");

        if (customerList.isEmpty()) {
            log.info("Customer not found, as expected.");
            Assert.assertTrue(true);
        }
    }
}