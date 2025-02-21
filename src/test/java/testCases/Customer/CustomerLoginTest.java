package testCases.Customer;

import base.TestBase;

import org.testng.SkipException;
import org.testng.annotations.Test;
import utilities.TestUtil;


public class CustomerLoginTest extends TestBase {

    @Test(description = "To verify login as a Bank Manager")
    public void customerLoginTest() {
        log.info("Inside Login Test");

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        // if RunMode = Y
        if(!TestUtil.isTestRunnable(methodName, excel)){
            throw new SkipException("Skipping the test case as the Run mode for data set is N");
        }

        isElementPresent("customerLoginBtn_XPATH");
        click("customerLoginBtn_XPATH");

        log.info("Login successful!");
    }

    @Test(description = "To verify if User can login with valid credential", dependsOnMethods = "customerLoginTest")
    public void verifyCustomerLoginTest(){
        log.info("Inside Customer Login Test");

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        // if RunMode = Y
        if(!TestUtil.isTestRunnable(methodName, excel)){
            throw new SkipException("Skipping the test case as the Run mode for data set is N");
        }

        select("selectUser_ID", "Hermoine Granger");

        isElementDisplayed("loginBtn_XPATH");
        click("loginBtn_XPATH");

        log.info("Customer login successful!");
    }
}
