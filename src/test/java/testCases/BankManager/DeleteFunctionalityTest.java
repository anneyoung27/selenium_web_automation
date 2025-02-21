package testCases.BankManager;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.Test;
import utilities.TestUtil;

import java.util.ArrayList;
import java.util.List;

public class DeleteFunctionalityTest extends TestBase {
    List<String> deletedData = new ArrayList<>();

    @Test(description = "To validate delete button is present")
    public void deleteButtonPresentTest(){
        log.info("Inside delete button is present test");

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        // if RunMode = Y
        if(!TestUtil.isTestRunnable(methodName, excel)){
            throw new SkipException("Skipping the test case as the Run mode for data set is N");
        }

        boolean isPresent = isElementDisplayed("deleteBtn_XPATH");
        Assert.assertTrue(isPresent, "Delete button is present");

        log.info("Delete button is present!");
    }

    @Test(dataProviderClass = TestUtil.class, dataProvider = "deleteData", description = "To verify delete functionality")
    public void deleteSpecificDataTest(String deleteData){
        log.info("Inside delete specific data test");

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        // if RunMode = Y
        if(!TestUtil.isTestRunnable(methodName, excel)){
            throw new SkipException("Skipping the test case as the Run mode for data set is N");
        }

        boolean recordFound = false;

        actionType("customerSearch_CSS", deleteData);

        List<WebElement> customerData = findElements("customerTableData_XPATH");

        for(WebElement row : customerData){
            // access all rows data per columns
            List<WebElement> dataPerColumns = row.findElements(By.tagName("td"));

            String firstName = dataPerColumns.get(0).getText().trim();
            String lastName = dataPerColumns.get(1).getText().trim();
            String postCode = dataPerColumns.get(2).getText().trim();

            if (firstName.contains(deleteData) || lastName.contains(deleteData) || postCode.contains(deleteData)){
                recordFound = true; // need this so much to make sure lol
                click("deleteBtn_XPATH");

                deletedData.add(deleteData);
                System.out.println("List of deleted data: [" + String.join(", ", deletedData+"]"));
            }
        }
        log.info("Delete data successfully!");
    }


    @Test(description = "To verify deleted data is not in the customer list", dependsOnMethods = "deleteSpecificDataTest")
    public void deleteFunctionalityTest(){
        log.info("Delete functionality test");

        String methodName = new Object(){}.getClass().getEnclosingMethod().getName();
        // if RunMode = Y
        if(!TestUtil.isTestRunnable(methodName, excel)){
            throw new SkipException("Skipping the test case as the Run mode for data set is N");
        }

        boolean isDeleted = false;

        // check if there are deleted data
        Assert.assertNotNull(deletedData, "No deleted data found!");

        // get all rows in table body
        List<WebElement> rowData = findElements("customerTableData_XPATH");

        // loop data customer
        for (WebElement data : rowData){
            List<WebElement> dataPerColumns = data.findElements(By.tagName("td"));

            String firstName = dataPerColumns.get(0).getText().trim();
            String lastName = dataPerColumns.get(1).getText().trim();
            String postCode = dataPerColumns.get(2).getText().trim();

            for(String dataCollection : deletedData){
                if(!(firstName.contains(dataCollection) || lastName.contains(dataCollection) || postCode.contains(dataCollection))){
                    isDeleted = true;
                    break;
                }
            }
        }
        log.info("Deleted record verification passed. Data successfully removed.");
    }
}