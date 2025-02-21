package testCases.Customer;

import base.TestBase;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import utilities.TestUtil;

public class CustomerAccountCheckTest extends TestBase {

    @Test(description = "To verify if Customer have an account or not")
    public void customerAccountCheckTest(){
        log.info("Inside Customer account check test");

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        // if RunMode = Y
        if(!TestUtil.isTestRunnable(methodName, excel)){
            throw new SkipException("Skipping the test case as the Run mode for data set is N");
        }

        if(isElementDisplayed("customerAccountStatus_XPATH")){
            Assert.assertTrue(isElementDisplayed("customerAccountStatus_XPATH"));
            log.info("Customer have an account");
        }else{
            Assert.assertFalse(isElementDisplayed("customerAccountStatus_XPATH"));
            String actualText = getElementText("customerNoHaveAnAccountNumber_XPATH");

            verifyEquals(actualText, "Please open an account with us.");
            log.info("Customer doesn't have an account");
        }

        log.info("Customer's account checked!");
    }
}
