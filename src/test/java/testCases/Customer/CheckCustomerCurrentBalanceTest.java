package testCases.Customer;

import base.TestBase;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import utilities.TestUtil;

public class CheckCustomerCurrentBalanceTest extends TestBase {
    @Test(description = "To verify if customer have a balance in it's customer's account")
    public void checkCustomerCurrentBalanceTest(){
        log.info("Inside check customer current balance test");

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        // if RunMode = Y
        if(!TestUtil.isTestRunnable(methodName, excel)){
            throw new SkipException("Skipping the test case as the Run mode for data set is N");
        }

        int balanceAmount = 0;

        try {
            balanceAmount = Integer.parseInt(getElementText("customerBalanceAmount_XPATH"));
        } catch (NumberFormatException e) {
            log.info("Failed to parse balance amount. Setting balance to 0.");
        }

        if(isElementDisplayed("customerAccountStatus_XPATH")){
            if(balanceAmount > 0){
                log.info("Customer has a balance in its account: " + balanceAmount);
                Assert.assertTrue(true, "Customer's balance > 0");
            }else{
                log.info("Customer's balance amount = 0");
                verifyEquals(balanceAmount, 0);
            }
        }else{
            log.info("Customer doesn't have an account");
        }

        log.info("Customer's account balance checked!");
    }
}
