package testCases.Transactions;

import base.TestBase;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import utilities.TestUtil;

public class CustomerWithdrawTest extends TestBase {
    int currentBalance;
    char currency;
    String withdrawAmount = "250";

    String getActualCurrency(){
        return getElementText("customerAccountCurrency_XPATH");
    }

    @Test(description = "To verify if there are a balance in Customer's account")
    public void checkBalanceTest(){
        log.info("Inside check balance test before withdraw");

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        // if RunMode = Y
        if(!TestUtil.isTestRunnable(methodName, excel)){
            throw new SkipException("Skipping the test case as the Run mode for data set is N");
        }

        // check the customer's balance and amount
        currentBalance = Integer.parseInt(getElementText("customerBalanceAmount_XPATH"));

        currency = switch (getActualCurrency()) {
            case "Dollar" -> '$';
            case "Pound" -> '£';
            case "Rupee" -> '₹';
            default -> 0;
        };

        // check if there is a balance in the account?
        if (currentBalance <= 0){
            Assert.fail("Insufficient balance, your balance is: "+currency+currentBalance);
            log.info("Insufficient balance, your balance is: "+currency+currentBalance);
        }
        log.info("Customer current balance:"+currency+currentBalance);
    }


    @Test(description = "To verify if Customer can withdraw", dependsOnMethods = "checkBalanceTest")
    public void withdrawTest(){
        log.info("Inside Customer Withdraw Test");

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        // if RunMode = Y
        if(!TestUtil.isTestRunnable(methodName, excel)){
            throw new SkipException("Skipping the test case as the Run mode for data set is N");
        }

        isClickable("withdrawBtn_XPATH").click();

        type("depositAndWithdrawValue_XPATH", withdrawAmount);

        click("depositAndWithdrawBtn_XPATH");

        String msgWithdraw = getElementText("depositAndWithdrawMsg_XPATH");
        verifyEquals(msgWithdraw, "Transaction successful");

        int nowBalance = currentBalance - Integer.parseInt(withdrawAmount);

        verifyEquals(currentBalance, nowBalance);
        log.info("Withdraw successful, customer current balance before :"+ currency+currentBalance +" after withdraw became: " + currency+nowBalance);
    }
}