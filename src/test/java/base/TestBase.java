package base;

import com.aventstack.extentreports.Status;
import io.github.bonigarcia.wdm.WebDriverManager;
import listeners.CustomListeners;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
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

import java.util.*;
import java.util.NoSuchElementException;
import java.util.logging.Logger;
import java.util.stream.Collectors;

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
            wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(config.getProperty("explicit.wait"))));
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

    public static void click(String locator){
        if (locator.endsWith("_CSS")){
            driver.findElement(By.cssSelector(OR.getProperty(locator))).click();
        }else if (locator.endsWith("_XPATH")){
            driver.findElement(By.xpath(OR.getProperty(locator))).click();
        }else if (locator.endsWith("_ID")){
            driver.findElement(By.id(OR.getProperty(locator))).click();
        }

        CustomListeners.extentTest.log(Status.INFO, "Clicking on: " + locator);
    }

    public static void type(String locator, String value){
        if (locator.endsWith("_CSS")){
            driver.findElement(By.cssSelector(OR.getProperty(locator))).sendKeys(value);
        }else if (locator.endsWith("_XPATH")){
            driver.findElement(By.xpath(OR.getProperty(locator))).sendKeys(value);
        }else if (locator.endsWith("_ID")){
            driver.findElement(By.id(OR.getProperty(locator))).sendKeys(value);
        }

        CustomListeners.extentTest.log(Status.INFO, "Typing in: " + locator +" entered value as: " + value);
    }

    // type + delete action
    public static Actions actionType(String locator, String value) {
        Actions actions = new Actions(driver);
        WebElement element = null;

        try {
            if (locator.endsWith("_CSS")) {
                element = driver.findElement(By.cssSelector(OR.getProperty(locator)));
            } else if (locator.endsWith("_XPATH")) {
                element = driver.findElement(By.xpath(OR.getProperty(locator)));
            } else if (locator.endsWith("_ID")) {
                element = driver.findElement(By.id(OR.getProperty(locator)));
            }

            if (element != null) {
                actions.sendKeys(element, value).perform();

                actions.keyDown(Keys.CONTROL)
                        .sendKeys("a")
                        .keyUp(Keys.CONTROL)
                        .sendKeys(Keys.DELETE)
                        .perform();
            } else {
                System.out.println("Element not found for locator: " + locator);
            }
        } catch (NoSuchElementException e) {
            System.out.println("Element not found: " + locator + " - " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error in action_type: " + e.getMessage());
        }
        return actions;
    }

    public static void select(String locator, String value){
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
            CustomListeners.extentTest.log(Status.INFO, "Element: " + locator +" is present");

            return true;
        }catch (NoSuchElementException e){
            return false;
        }

    }

    public static boolean isElementDisplayed(String locator){
        try{
            wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(config.getProperty("explicit.wait"))));

            if (locator.endsWith("_CSS")){
                wait.until(d -> driver.findElement(By.cssSelector(OR.getProperty(locator)))).isDisplayed();
            }else if(locator.endsWith("_XPATH")){
                wait.until(d -> driver.findElement(By.xpath(OR.getProperty(locator)))).isDisplayed();
            }else if(locator.endsWith("_ID")){
                wait.until(d -> driver.findElement(By.id(OR.getProperty(locator)))).isDisplayed();
            }
            CustomListeners.extentTest.log(Status.INFO, "Element: " + locator +" is displayed");
            return true;
        }catch (NoSuchElementException e){
            return false;
        }
    }

    public static List<WebElement> findElements(String locator) {
        isElementDisplayed(locator);
        List<WebElement> elements = new ArrayList<>();

        try {
            By by = null;
            if (locator.endsWith("_CSS")) {
                by = By.cssSelector(OR.getProperty(locator));
            } else if (locator.endsWith("_XPATH")) {
                by = By.xpath(OR.getProperty(locator));
            } else if (locator.endsWith("_ID")) {
                by = By.id(OR.getProperty(locator));
            }

            if (by != null) {
                elements = driver.findElements(by);
            }

            if (!elements.isEmpty()) {
                CustomListeners.extentTest.log(Status.INFO, "Element found: " + locator);
            } else {
                CustomListeners.extentTest.log(Status.WARNING, "Element not found: " + locator);
            }
        } catch (Exception e) {
            log.info("Error encountered in findElements method: " + e.getMessage());
        }
        return elements;
    }

    public static WebElement findElement(String locator){
        isElementDisplayed(locator);
        WebElement element = null;
        try {
            By by = null;
            if (locator.endsWith("_CSS")) {
                by = By.cssSelector(OR.getProperty(locator));
            } else if (locator.endsWith("_XPATH")) {
                by = By.xpath(OR.getProperty(locator));
            } else if (locator.endsWith("_ID")) {
                by = By.id(OR.getProperty(locator));
            }

            if (by != null) {
                element = driver.findElement(by);
            }

            if (element != null) {
                CustomListeners.extentTest.log(Status.INFO, "Element found: " + locator);
            } else {
                CustomListeners.extentTest.log(Status.WARNING, "Element not found: " + locator);
            }
        } catch (Exception e) {
            log.info("Error encountered in findElements method: " + e.getMessage());
        }
        return element;
    }

    public static void verifyEquals(String actualResult, String expectedResult) {
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName(); // get method name

        try {
            Assert.assertEquals(actualResult, expectedResult);
        } catch (Throwable t) {
            try {
                TestUtil.captureScreenshot(methodName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        CustomListeners.extentTest.log(Status.INFO, "Actual result: [" + actualResult + "] matches the expected result: [" + expectedResult + "]");
    }

    public static void verifyEquals(int actualResult, int expectedResult) {
        String methodName = Thread.currentThread().getStackTrace()[2].getMethodName(); // get method name

        try {
            Assert.assertEquals(actualResult, expectedResult);
        } catch (Throwable t) {
            try {
                TestUtil.captureScreenshot(methodName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        CustomListeners.extentTest.log(Status.INFO, "Actual result: [" + actualResult + "] matches the expected result: [" + expectedResult + "]");
    }

    public static String getElementText(String locator){
        isElementPresent(locator);
        WebElement element = null;

        try{
            if (locator.endsWith("_CSS")){
                element = driver.findElement(By.cssSelector(OR.getProperty(locator)));
            }else if(locator.endsWith("_XPATH")){
                element = driver.findElement(By.xpath(OR.getProperty(locator)));
            }else if(locator.endsWith("_ID")){
                element = driver.findElement(By.id(OR.getProperty(locator)));
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        assert element != null;

        String text = element.getText();
        CustomListeners.extentTest.log(Status.INFO, "Extracted text: [" + text + "] from element with locator: [" + locator + "]");

        return element.getText();
    }

    public static List<String> getAllSelectOptionsText(String locator) {
        WebElement selectElement = null;

        if (locator.endsWith("_CSS")){
            selectElement = driver.findElement(By.cssSelector(OR.getProperty(locator)));
        }else if(locator.endsWith("_XPATH")){
            selectElement = driver.findElement(By.xpath(OR.getProperty(locator)));
        }else if(locator.endsWith("_ID")){
            selectElement = driver.findElement(By.id(OR.getProperty(locator)));
        }

        assert selectElement != null;
        Select select = new Select(selectElement);

        List<WebElement> options = select.getOptions();

        CustomListeners.extentTest.log(Status.INFO, "Extracted text: ["+options.stream()
                .map(WebElement::getText)
                .toList() +"]");

        return options.stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());
    }

    public static String getElementValue(String locator) {
        WebElement element = null;

        try {
            if (locator.endsWith("_CSS")){
                element = driver.findElement(By.cssSelector(OR.getProperty(locator)));
            }else if(locator.endsWith("_XPATH")){
                element = driver.findElement(By.xpath(OR.getProperty(locator)));
            }else if(locator.endsWith("_ID")){
                element = driver.findElement(By.id(OR.getProperty(locator)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert element != null;

        CustomListeners.extentTest.log(Status.INFO, "Extracted attribute: [" + element.getAttribute("value") + "] from element with locator: [" + locator + "]");
        return element.getAttribute("value");
    }

    public static WebElement isClickable(String locator){
        WebElement element = null;
        wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(config.getProperty("explicit.wait"))));

        try{
            if (locator.endsWith("_CSS")){
                element = driver.findElement(By.cssSelector(OR.getProperty(locator)));
            }else if(locator.endsWith("_XPATH")){
                element = driver.findElement(By.xpath(OR.getProperty(locator)));
            }else if(locator.endsWith("_ID")){
                element = driver.findElement(By.id(OR.getProperty(locator)));
            }

            element = wait.until(ExpectedConditions.elementToBeClickable(element));
            log.info("Element found: " + locator + " is clickable");
            CustomListeners.extentTest.log(Status.INFO, "Element found: " + locator + " is clickable");
            return element;

        }catch (NoSuchElementException e){
            log.info("Element not found: " + locator);
            CustomListeners.extentTest.log(Status.WARNING, "Element not found: " + locator);
        }catch (TimeoutException e){
            log.info("Element not clickable within timeout: " + locator);
            CustomListeners.extentTest.log(Status.WARNING, "Element not clickable within timeout: " + locator);
        }catch (Exception e){
            log.info("Error in isClickable method: " + locator);
            CustomListeners.extentTest.log(Status.WARNING, "Error in isClickable method: " + locator);
        }
        return null;
    }

    public static WebElement visibilityOfElementLocated(String locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(config.getProperty("explicit.wait"))));
        WebElement element = null;

        try {
            By byLocator = null;
            if (locator.endsWith("_CSS")) {
                byLocator = By.cssSelector(OR.getProperty(locator));
            } else if (locator.endsWith("_XPATH")) {
                byLocator = By.xpath(OR.getProperty(locator));
            } else if (locator.endsWith("_ID")) {
                byLocator = By.id(OR.getProperty(locator));
            }

            if (byLocator != null) {
                element = wait.until(ExpectedConditions.visibilityOfElementLocated(byLocator));
                log.info("Element found: " + locator + " is visible.");
                if (CustomListeners.extentTest != null) {
                    CustomListeners.extentTest.log(Status.INFO, "Element found: " + locator + " is visible.");
                }
            } else {
                log.warning("Invalid locator format: " + locator);
            }

        } catch (NoSuchElementException e) {
            log.warning("Element not found: " + locator);
            if (CustomListeners.extentTest != null) {
                CustomListeners.extentTest.log(Status.WARNING, "Element not found: " + locator);
            }
        } catch (TimeoutException e) {
            log.warning("Element not visible within timeout: " + locator);
            if (CustomListeners.extentTest != null) {
                CustomListeners.extentTest.log(Status.WARNING, "Element not visible within timeout: " + locator);
            }
        } catch (Exception e) {
            log.warning("Error in visibilityOfElementLocated method: " + locator + " - " + e.getMessage());
            if (CustomListeners.extentTest != null) {
                CustomListeners.extentTest.log(Status.WARNING, "Error in visibilityOfElementLocated method: " + locator);
            }
        }

        return element;
    }

    public static ArrayList<String> getElementsText(String locator){
        List<WebElement> elements = null;
        ArrayList<String> text = new ArrayList<>();

        try{
            if (locator.endsWith("_CSS")){
                elements = driver.findElements(By.cssSelector(OR.getProperty(locator)));
            }else if(locator.endsWith("_XPATH")){
                elements = driver.findElements(By.xpath(OR.getProperty(locator)));
            }else if(locator.endsWith("_ID")){
                elements = driver.findElements(By.id(OR.getProperty(locator)));
            }

            assert elements != null;
            elements.forEach(webElement -> {
                String textValue = webElement.getText();
                text.add(textValue);
            });
        }catch (Exception e){
            log.info("Error in getElementText method: " + locator);
        }
        CustomListeners.extentTest.log(Status.INFO, "Extracted text: ["+text+"]");
        return text;
    }

    @AfterSuite
    public void tearDown(){
        if (driver != null){
            driver.quit();
        }
        log.info("Test execution completed!");
    }
}
