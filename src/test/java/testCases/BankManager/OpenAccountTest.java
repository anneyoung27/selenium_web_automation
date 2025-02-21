package testCases.BankManager;

import base.TestBase;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.SkipException;
import org.testng.annotations.Test;
import utilities.TestUtil;

import java.util.Hashtable;

public class OpenAccountTest extends TestBase {

    @Test(dataProviderClass = TestUtil.class, dataProvider = "dp", description = "To validate if User can add an open account to customer")
    public void openAccountTest(Hashtable<String, String> data)  {
        log.info("Inside Open Account Test");

        if (!TestUtil.isTestRunnable(this.getClass().getSimpleName(), excel) || !"Y".equals(data.get("RunMode"))) {
            throw new SkipException("Skipping the test case as the Run mode for data set is N");
        }

        click("openAccount_CSS");
        select("customer_CSS", data.get("Customer"));
        select("currency_XPATH", data.get("Currency"));
        click("process_CSS");

        Alert alert = wait.until(ExpectedConditions.alertIsPresent());
        alert.accept();
        log.info("Alert clicked!");
        log.info("Open Account successfully!");
    }
}
