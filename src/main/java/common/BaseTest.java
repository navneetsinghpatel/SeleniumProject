package common;

import common.DriverFactory.BrowserType;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;
import pages.LoginPage;
import pages.ProfilePage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import static constants.Constants.SCREENSHOT_FOLDER_PATH;
import static utils.LocatorUtils.setBaseURIsAndUserCredentials;

public class BaseTest {
    private static final Logger logger = LogManager.getLogger(BaseTest.class);
    protected static WebDriver driver;
    public static HashMap<String, String> appLoginURLMap = new HashMap<>();
    protected static ThreadLocal<LoginPage> loginPageThreadLocal = new ThreadLocal<>();
    protected static ThreadLocal<ProfilePage> profilePageThreadLocal = new ThreadLocal<>();

    protected static LoginPage loginPage;
    protected static ProfilePage profilePage;
    public static String environment = "";
    @Parameters("browser")
    @BeforeClass
    public void setup(String browser) throws IOException, InterruptedException {
        System.out.println("Start of Test Class");
        logger.info("Start of Test Class");
        environment = System.getProperty("environment") == null? "" : "-" + System.getProperty("environment");
        logger.info("Environment: " + environment);
        setBaseURIsAndUserCredentials(environment);
        DriverFactory.initializeDriver(BrowserType.valueOf(browser.toUpperCase()));
        driver = DriverFactory.getDriver();
        //creating screenshot folder to in case of test case failures
        final Path path = Paths.get(SCREENSHOT_FOLDER_PATH);
        if(!Files.exists(path)) {
            Files.createDirectory(path);
        }
        loginPageThreadLocal.set(new LoginPage(driver));
        profilePageThreadLocal.set(new ProfilePage(driver));
    }
   @AfterClass
   public void tearDown() {
       System.out.println("End of Test Class");
       logger.info("End of Test Class");
       try {
           if (driver != null) {
               System.out.println("Driver is not Null");
               logger.info("Quitting Driver by calling Driver Factory method quitDriver");
               DriverFactory.quitDriver();
           }
       } catch (Exception e) {
           logger.error("Error in closing the browser: " + e.getMessage());
       }


   }
}
