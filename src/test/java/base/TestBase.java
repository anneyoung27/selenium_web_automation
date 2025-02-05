package base;

import com.aventstack.extentreports.Status;
import io.github.bonigarcia.wdm.WebDriverManager;
import listeners.CustomListeners;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import utilities.ExcelReader;
import utilities.TestUtil;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.logging.Logger;

public class TestBase {

    public static WebDriver driver;
    public static Properties config = new Properties();
    public static Properties OR = new Properties();
    public static FileInputStream fis;
    public static Logger log = Logger.getLogger(String.valueOf(TestBase.class));
    public static WebElement dropDown;
    public static ExcelReader excel = new ExcelReader(System.getProperty("user.dir") + "\\src\\test\\resources\\excel\\DataTest.xlsx");
    public static WebDriverWait wait;

    public static String browser;

    @BeforeSuite
    public void setUp() {
        if (driver == null) {
            try {
                fis = new FileInputStream(
                        System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\Config.properties");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            try {
                config.load(fis);
                log.info("Config file loaded!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            try {
                fis = new FileInputStream(
                        System.getProperty("user.dir") + "\\src\\test\\resources\\properties\\OR.properties");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            try {
                OR.load(fis);
                log.info("OR file loaded!");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (System.getenv("browser") != null && !System.getenv("browser").isEmpty()) {
                browser = System.getenv("browser");
            } else {
                browser = config.getProperty("browser");
            }

            config.setProperty("browser", browser);
            initializeDriver();

            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Integer.parseInt(config.getProperty("implicit.wait"))));
            driver.manage().window().maximize();
            driver.get(config.getProperty("testSiteURL"));
            wait = new WebDriverWait(driver, Duration.ofSeconds(5));
            log.info("Navigated to : " + config.getProperty("testSiteURL"));
        }
    }

    private void initializeDriver() {
        switch (config.getProperty("browser").toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
                log.info("Firefox launched!");
                break;
            case "chrome":
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
                log.info("Chrome launched!");
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
                log.info("Edge launched!");
                break;
            default:
                throw new RuntimeException("Invalid browser specified in configuration: " + config.getProperty("browser"));
        }
    }

    public void click(String locator){
        if (locator.endsWith("_CSS")){
            driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
        }else if (locator.endsWith("_XPATH")){
            driver.findElement(By.xpath(OR.getProperty(locator))).click();
        }else if (locator.endsWith("_ID")){
            driver.findElement(By.id(OR.getProperty(locator))).click();
        }

        CustomListeners.extentTest.log(Status.INFO, "Clicking on: " + locator);
    }

    public void type(String locator, String value){
        if (locator.endsWith("_CSS")){
            driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
        }else if (locator.endsWith("_XPATH")){
            driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
        }else if (locator.endsWith("_ID")){
            driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
        }

        CustomListeners.extentTest.log(Status.INFO, "Typing in: " + locator +" entered value as: " + value);
    }

    public void select(String locator, String value){
        if (locator.endsWith("_CSS")){
            dropDown = driver.findElement(By.cssSelector(OR.getProperty(locator)));
        }else if (locator.endsWith("_XPATH")){
            dropDown = driver.findElement(By.xpath(OR.getProperty(locator)));
        }else if (locator.endsWith("_ID")){
            dropDown = driver.findElement(By.id(OR.getProperty(locator)));
        }

        Select select = new Select(dropDown);
        select.selectByVisibleText(value);

        CustomListeners.extentTest.log(Status.INFO, "Selecting from dropdown: " + locator + " value as "+ value);
    }

    public static boolean isElementPresent(String locator){
        try{
            if (locator.endsWith("_CSS")){
                driver.findElement(By.cssSelector(OR.getProperty(locator)));
            }else if(locator.endsWith("_XPATH")){
                driver.findElement(By.xpath(OR.getProperty(locator)));
            }else if(locator.endsWith("_ID")){
                driver.findElement(By.id(OR.getProperty(locator)));
            }
            return true;
        }catch (NoSuchElementException e){
            return false;
        }
    }

    public static void verifyEquals(String actualResult, String expectedResult) {
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName();

        try {
            Assert.assertEquals(actualResult, expectedResult);
        } catch (Throwable t) {
            try {
                TestUtil.captureScreenshot(methodName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @AfterSuite
    public void tearDown(){
        if (driver != null){
            driver.quit();
        }
        log.info("Test execution completed!");
    }
}
