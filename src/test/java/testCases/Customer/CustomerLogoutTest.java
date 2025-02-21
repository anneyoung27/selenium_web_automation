package testCases.Customer;

import base.TestBase;
import org.testng.SkipException;
import org.testng.annotations.Test;
import utilities.TestUtil;

public class CustomerLogoutTest extends TestBase {

    @Test(description = "To verify if User can logout after login as an customer")
    public void customerLogoutTest(){
        log.info("Inside customer Logout test");

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        // if RunMode = Y
        if(!TestUtil.isTestRunnable(methodName, excel)){
            throw new SkipException("Skipping the test case as the Run mode for data set is N");
        }

        isElementDisplayed("logOutBtn_XPATH");
        click("logOutBtn_XPATH");

        log.info("Logout successful!");
    }
}
