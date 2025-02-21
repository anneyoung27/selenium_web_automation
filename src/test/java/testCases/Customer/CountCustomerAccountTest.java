package testCases.Customer;

import base.TestBase;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import utilities.TestUtil;

import java.util.List;

public class CountCustomerAccountTest extends TestBase {

    @Test(description = "To verify number of how many accounts the customer has")
    public void countCustomerAccountTest(){
        log.info("Inside Customer count account test");

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        // if RunMode = Y
        if(!TestUtil.isTestRunnable(methodName, excel)){
            throw new SkipException("Skipping the test case as the Run mode for data set is N");
        }

        List <String> optionText = getAllSelectOptionsText("customerAccounts_XPATH");

        int customerAccountsCount = optionText.size();
        String customerName = getElementText("verifyCustomerName_XPATH");

        Assert.assertTrue(customerAccountsCount > 0, "Customer : "+customerName+" has "+customerAccountsCount+" accounts");

        log.info("Number of customer account checked! [Customer name: " + customerName +" has "+ customerAccountsCount+ " accounts]");
    }
}
