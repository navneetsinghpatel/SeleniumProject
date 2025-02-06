package pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.appender.rolling.action.IfAll;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utils.LocatorUtils;

import java.sql.Time;
import java.util.concurrent.TimeUnit;

public class ProfilePage {
    private WebDriver driver;
    private static final Logger logger = LogManager.getLogger(ProfilePage.class);

    // Web element locators
    private By logoutButton = By.cssSelector("button#submit");
    private By usernameLabel = By.cssSelector("label#userName-value");

    // Constructor
    public ProfilePage(WebDriver driver) {
        this.driver = driver;
        System.out.println("Driver in Profile Page with Thread " + Thread.currentThread().getName() + " : " + driver);
    }

    public String isUserLoggedIn(String username){
        if(LocatorUtils.waitForElementToBeVisible(driver, usernameLabel, 10)){
            logger.info("User Logged In. Now checking if Correct User Logged in or not");
            if(!LocatorUtils.getWebElement(driver, usernameLabel, 5).getText().equals(username)){
                logger.info("Correct User was not Logged In");
                return "Failure";
            }
            logger.info("User Logged In Successfully!");
            return "Success";
        }else {
            logger.info("User was not Logged In");
            return "Failure";
        }
    }

    public void logoutOfBookStore() {
        try {
            LocatorUtils.click(driver, logoutButton, 15);
            TimeUnit.SECONDS.sleep(3);
        }catch (Exception e){
            logger.error("Failed to Logout", e);
            throw new RuntimeException("Failed to Logout");
        }
    }

    public void logoutOfPractice() {
        try {
            LocatorUtils.click(driver, By.xpath("//a[text()='Log out']"), 15);
            TimeUnit.SECONDS.sleep(3);
        }catch (Exception e){
            logger.error("Failed to Logout", e);
            throw new RuntimeException("Failed to Logout");
        }
    }

}

