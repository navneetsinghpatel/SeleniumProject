package utils;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import common.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.util.List;
import static constants.Constants.DEMO_QA_URL;
import static constants.Constants.SCREENSHOT_FOLDER_PATH;

public class LocatorUtils extends BaseTest {
    private static final Logger logger = LogManager.getLogger(LocatorUtils.class);
    public static boolean waitForElementToBeVisible(WebDriver driver, By by, long timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(by)).isDisplayed();
        }catch (TimeoutException e){
            return false;
        }
    }

    public static void setBaseURIsAndUserCredentials(String environment) {
        Config conf = ConfigFactory.load("application" + environment);
        appLoginURLMap.put(DEMO_QA_URL, conf.getString(DEMO_QA_URL));
    }

    public static boolean waitForElementToBeInVisible(WebDriver driver, By by, long timeoutInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public static List<WebElement> waitForElementsToBeVisible(WebDriver driver, By by, long timeoutInSeconds) {
        ExpectedCondition<List<WebElement>> atLeastOneElementPresent = new ExpectedCondition<List<WebElement>>() {
            @Override
            public List<WebElement> apply(WebDriver driver) {
                List<WebElement> elements = driver.findElements(by);
                return elements.size() > 0 ? elements : null;
            }
        };
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
        return wait.until(atLeastOneElementPresent);
    }


    public static WebElement getWebElement(WebDriver driver, By locator, long timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            logger.error("Timeout waiting for element to be visible: " + locator);
            throw e; // Rethrow to allow the test framework to manage the error
        }
    }

    public static List<WebElement> getMultipleElements(WebDriver driver, By locator) {
        return driver.findElements(locator);
    }

    public static void click(WebDriver driver, By locator, long timeoutInSeconds) {
        try {
            WebElement element = getWebElement(driver, locator, timeoutInSeconds);
            element.click();
        } catch (Exception e) {
            System.err.println("Failed to click on element: " + locator);
            throw e;
        }
        logger.info("Clicked on Locator " + locator);
    }

    public static void type(WebDriver driver, By locator, long timeoutInSeconds, String text) {
        try {
            WebElement element = getWebElement(driver, locator, timeoutInSeconds);
            element.clear();
            element.sendKeys(text);
        } catch (Exception e) {
            System.err.println("Failed to type in element: " + locator);
            throw e;
        }
        logger.info("Typed " + text + " into Locator " + locator);
    }


    public static boolean waitForElementToBeInvisible(WebDriver driver, By locator, long timeoutInSeconds) {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutInSeconds));
            return wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (TimeoutException e) {
            System.err.println("Timeout waiting for element to be invisible: " + locator);
            throw e;
        }
    }

    public static void uploadFile(WebDriver driver, By locator, String filePath) {
        try {
            WebElement element = driver.findElement(locator);
            element.sendKeys(filePath);
        } catch (NoSuchElementException e) {
            System.err.println("Failed to find the file upload element: " + locator);
            throw e;
        }
    }

    public static void selectItem(WebDriver driver, By locator, String visibleText, long timeoutInSeconds) {
        try {
            WebElement element = getWebElement(driver, locator, timeoutInSeconds);
            Select dropdown = new Select(element);
            dropdown.selectByVisibleText(visibleText);
        } catch (Exception e) {
            System.err.println("Failed to select item in dropdown: " + locator);
            throw e;
        }
    }

    public static void openURL(WebDriver driver, String url) {
        try {
            driver.get(url);
        } catch (Exception e) {
            System.err.println("Failed to open URL: " + url);
            throw e;
        }
    }

    public static void switchToAnotherTabWindow(WebDriver driver) {
        try {
            String originalHandle = driver.getWindowHandle();
            for (String handle : driver.getWindowHandles()) {
                if (!handle.equals(originalHandle)) {
                    driver.switchTo().window(handle);
                    break;
                }
            }
        } catch (Exception e) {
            System.err.println("Failed to switch to another tab or window.");
            throw e;
        }
    }

    public static void closeTab(WebDriver driver) {
        try {
            driver.close();
        } catch (Exception e) {
            System.err.println("Failed to close tab or window.");
            throw e;
        }
    }

    public static void switchToIframe(WebDriver driver, By locator, long timeoutInSeconds) {
        try {
            WebElement iframe = getWebElement(driver, locator, timeoutInSeconds);
            driver.switchTo().frame(iframe);
        } catch (Exception e) {
            System.err.println("Failed to switch to iframe: " + locator);
            throw e;
        }
    }

    public static void pressKey(WebDriver driver, Keys key) {
        Actions actions = new Actions(driver);
        try {
            actions.sendKeys(key).perform();
            logger.info("Pressed key: " + key.name());
        } catch (Exception e) {
            logger.error("Failed to press key: " + key.name(), e);
            throw e;
        }
    }

    // Method to press a combination of two keys
    public static void pressKeyCombo(WebDriver driver, Keys key1, Keys key2) {
        Actions actions = new Actions(driver);
        try {
            actions.keyDown(key1).sendKeys(key2).keyUp(key1).perform();
            logger.info("Pressed key combination: " + key1.name() + " + " + key2.name());
        } catch (Exception e) {
            logger.error("Failed to press key combination: " + key1.name() + " + " + key2.name(), e);
            throw e;
        }
    }

    public static void takeScreenshot(String name) {
        try {
            TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
            File srcFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
            File destFile = new File(SCREENSHOT_FOLDER_PATH + name);
            Files.copy(srcFile.toPath(), destFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Screenshot saved to: " + name);
        } catch (Exception e) {
            logger.error("Error while taking screenshot: " + e.getMessage());
        }
    }

}
