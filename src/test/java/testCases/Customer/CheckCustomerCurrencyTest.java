package testCases.Customer;

import base.TestBase;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utilities.TestUtil;

import java.util.ArrayList;
import java.util.List;


public class CheckCustomerCurrencyTest extends TestBase {

    String getActualCurrency(){
        return getElementText("customerAccountCurrency_XPATH");
    }

    @DataProvider(name = "currency")
    public Object[][] getFilteredCurrencies() {
        String actualCurrentCurrency = getActualCurrency();
        String[] allCurrencies = { "Dollar", "Pound", "Rupee" };

        List<Object[]> filteredData = new ArrayList<>();

        for (String currency : allCurrencies) {
            if (actualCurrentCurrency.contains(currency)) {
                filteredData.add(new Object[]{currency});
            }
        }
        return filteredData.toArray(new Object[0][0]);
    }

    @Test(dataProvider = "currency", description = "To verify customer's account balance currency")
    public void checkCustomerCurrencyTest(String expectedCurrency){
        log.info("Inside check customer current currency test");

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        // if RunMode = Y
        if(!TestUtil.isTestRunnable(methodName, excel)){
            throw new SkipException("Skipping the test case as the Run mode for data set is N");
        }

        String actualCurrentCurrency = getActualCurrency();

        // condition if actual == expected -> assert it
        verifyEquals(actualCurrentCurrency, expectedCurrency);

        log.info("Customer currency is: " + actualCurrentCurrency);
    }
}
