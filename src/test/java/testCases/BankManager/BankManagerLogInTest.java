package testCases.BankManager;

import base.TestBase;
import org.testng.SkipException;
import org.testng.annotations.Test;
import utilities.TestUtil;

public class BankManagerLogInTest extends TestBase {

    @Test(description = "To verify login as a Bank Manager")
    public void bankManagerLogInTest() {
        log.info("Inside Login Test");

        // if RunMode = Y
        if(!TestUtil.isTestRunnable(this.getClass().getSimpleName(), excel)){
            throw new SkipException("Skipping the test case as the Run mode for data set is N");
        }

        click("bmlBtn_CSS");
        isElementPresent("bmlBtn_CSS");
        log.info("Login successful!");
    }
}
