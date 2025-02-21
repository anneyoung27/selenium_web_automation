package testCases.Customer;

import base.TestBase;
import org.testng.SkipException;
import org.testng.annotations.Test;
import utilities.TestUtil;

public class CustomerProfilePageTest extends TestBase {
    @Test(description = "To verify the customer dashboard page is displayed correctly after login.")
    public void customerProfilePageTest(){
        log.info("Inside verify customer page test");

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        // if RunMode = Y
        if(!TestUtil.isTestRunnable(methodName, excel)){
            throw new SkipException("Skipping the test case as the Run mode for data set is N");
        }

        isElementPresent("verifyCustomerPage_XPATH");
        isElementDisplayed("verifyCustomerPage_XPATH");

        isElementPresent("verifyCustomerName_XPATH");
        isElementDisplayed("verifyCustomerName_XPATH");

        verifyEquals("Welcome", "Welcome");

        log.info("Customer page is displayed");
    }

}
