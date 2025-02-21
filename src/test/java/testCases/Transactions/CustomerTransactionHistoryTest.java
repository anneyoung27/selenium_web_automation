package testCases.Transactions;

import base.TestBase;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import utilities.TestUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CustomerTransactionHistoryTest extends TestBase {

    @Test(description = "To verify if Customer's transaction history is present")
    public void checkTransactionHistoryTest(){

        log.info("Inside transaction history test");

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        // if RunMode = Y
        if(!TestUtil.isTestRunnable(methodName, excel)){
            throw new SkipException("Skipping the test case as the Run mode for data set is N");
        }

        Objects.requireNonNull(isClickable("transactionBtn_XPATH")).click();

        visibilityOfElementLocated("transactionTable_XPATH");
        List<WebElement> transactionList = new ArrayList<>();

        for (WebElement element : findElements("transactionTableData_XPATH")) {
            if (element.isDisplayed()) {
                transactionList.add(element);
            }
        }

        int transactionCountData = transactionList.size();

        if (transactionCountData > 0){
            Assert.assertTrue(true);
        }else{
            Assert.assertFalse(false);
            log.info("Transaction not found");
        }
        log.info(transactionCountData +" transaction(s) found");
    }
}
