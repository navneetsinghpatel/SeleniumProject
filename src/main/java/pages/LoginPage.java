package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.LocatorUtils;

import java.util.concurrent.TimeUnit;

public class LoginPage {
    private WebDriver driver;
    private static final Logger logger = LogManager.getLogger(LoginPage.class);

    // Web element locators
    private By usernameField = By.cssSelector("input#userName");
    private By passwordField = By.cssSelector("input#password");
    private By loginButton = By.cssSelector("button#login");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        System.out.println("Driver in Login Page with Thread " + Thread.currentThread().getName() + " : " + driver);
    }

    // Actions
    public void enterUsername(String username) {
        try {
            logger.info("Entering Username");
            LocatorUtils.type(driver, usernameField, 15, username);
            TimeUnit.SECONDS.sleep(3);
        }catch (Exception e){
            logger.error("Failed to Enter UserName", e);
            throw new RuntimeException("Failed to Enter UserName");
        }

    }

    public void enterPassword(String password) {
        try {
        logger.info("Entering Password");
        LocatorUtils.type(driver, passwordField, 15, password);
            TimeUnit.SECONDS.sleep(3);
    }catch (Exception e){
        logger.error("Failed to Enter Password", e);
            throw new RuntimeException("Failed to Enter Password");
    }
    }

    public void loginToBookStore(String username, String password) {
        try {
        enterUsername(username);
        enterPassword(password);
        logger.info("Clicking on Login Button");
        LocatorUtils.click(driver, loginButton, 15);
        TimeUnit.SECONDS.sleep(20);
        }catch (Exception e){
            logger.error("Failed to Login to Book Store", e);
            throw new RuntimeException("Failed to Login to Book Store");
        }
    }

    public void loginToPractice(String username, String password) {
        try {
            LocatorUtils.type(driver, By.cssSelector("input#username"), 15, username);
            TimeUnit.SECONDS.sleep(3);
            LocatorUtils.type(driver, By.cssSelector("input#password"), 15, password);
            TimeUnit.SECONDS.sleep(3);
            logger.info("Clicking on Login Button");
            LocatorUtils.click(driver, By.cssSelector("button#submit"), 15);
            TimeUnit.SECONDS.sleep(20);
        }catch (Exception e){
            logger.error("Failed to Login to Book Store", e);
            throw new RuntimeException("Failed to Login to Book Store");
        }
    }
    public boolean isLoginPageOpen() {
        logger.info("Checking if Login Page is Open or not");
        if(LocatorUtils.waitForElementToBeVisible(driver, usernameField, 10)){
            logger.info("Login Page is Open");
            return true;
        }else {
            logger.info("Login Page is not Open");
            return false;
        }
    }

}

