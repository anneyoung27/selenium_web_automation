package rough;

import base.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TableHeader extends TestBase {
    public static void main(String[] args) {
        driver = new ChromeDriver();
        driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
        try {
            // Buka halaman target
            driver.get("https://www.globalsqa.com/angularJs-protractor/BankingProject/#/login");
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));


            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            WebElement bankManagerLogin = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("div > div:last-child > .btn.btn-primary.btn-lg")));
            bankManagerLogin.click();


            WebElement customersBtn = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Customers']")));
            customersBtn.click();


            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table")));


            List<WebElement> tableHeaders = findElements("customerTableHeader_XPATH");


            if (!tableHeaders.isEmpty()) {
                for (WebElement header : tableHeaders) {
                    System.out.println(header.getText().trim());
                }
            } else {
                System.out.println("Table headers not found!");
            }

        } catch (Exception e) {
            System.out.println("Error encountered: " + e.getMessage());
        } finally {
            // Tutup browser
            driver.quit();
        }
    }
}
