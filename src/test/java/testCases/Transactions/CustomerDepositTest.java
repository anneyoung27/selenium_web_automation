package testCases.Transactions;

import base.TestBase;
import org.testng.SkipException;
import org.testng.annotations.Test;
import utilities.TestUtil;

public class CustomerDepositTest extends TestBase {
    String getActualCurrency(){
        return getElementText("customerAccountCurrency_XPATH");
    }

    @Test(description = "To verify customer can deposit balances into their accounts")
    public void customerDepositTest(){
        char currency = switch (getActualCurrency()) {
            case "Dollar" -> '$';
            case "Pound" -> '£';
            case "Rupee" -> '₹';
            default -> 0;
        };

        log.info("Inside customer deposit test");

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        // if RunMode = Y
        if(!TestUtil.isTestRunnable(methodName, excel)){
            throw new SkipException("Skipping the test case as the Run mode for data set is N");
        }

        click("depositMenuBtn_XPATH");
        log.info("Deposit button clicked!");

        type("depositAndWithdrawValue_XPATH", String.valueOf(Integer.parseInt("500")));
        log.info("Deposit value inputted!");

        click("depositAndWithdrawBtn_XPATH");
        log.info("Deposit clicked!");


        String depositMsg = getElementText("depositAndWithdrawMsg_XPATH");

        verifyEquals(depositMsg, "Deposit Successful");

        log.info("The customer successfully made a deposit to his account in the amount of: "+currency+"500");
    }
}