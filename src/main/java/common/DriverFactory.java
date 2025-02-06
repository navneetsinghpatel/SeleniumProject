package common;

import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.client.ClientUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import pages.LoginPage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class DriverFactory {
    private static ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();
    private static final Logger logger = LogManager.getLogger(LoginPage.class);

    public enum BrowserType {
        CHROME,
        FIREFOX,
        EDGE // Add more as needed
    }

    public static void initializeDriver(BrowserType browserType) throws InterruptedException {
        WebDriver driver;
        switch (browserType) {
            case CHROME:
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
                Map<String, Object> prefs = new HashMap<>();
                prefs.put("credentials_enable_service", false);
                prefs.put("profile.password_manager_enabled", false);
                chromeOptions.setExperimentalOption("prefs", prefs);
                driver = new ChromeDriver(chromeOptions);
                logger.info("Created Chrome Driver Instance: " + driver);
                //TimeUnit.SECONDS.sleep(10);
                break;
            case FIREFOX:
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                driver = new FirefoxDriver(firefoxOptions);
                logger.info("Created Firefox Driver Instance: " + driver);
                //TimeUnit.SECONDS.sleep(1);
                break;
            case EDGE:
                EdgeOptions edgeOptions = new EdgeOptions();
                driver = new EdgeDriver(edgeOptions);
                logger.info("Created Edge Instance: " + driver);
                //TimeUnit.SECONDS.sleep(30);
                break;
            default:
                throw new IllegalArgumentException("Unsupported browser type: " + browserType);
        }
        logger.info("Setting Driver Instance into Thread Local: " + driver);
        driverThreadLocal.set(driver);
    }

    public static WebDriver getDriver() {
        return driverThreadLocal.get();
    }

    public static void quitDriver() throws IOException {
        System.out.println("Quitting Driver: " + driverThreadLocal.get());
        logger.info("Quitting Driver: " + driverThreadLocal.get());
        driverThreadLocal.get().quit();
        driverThreadLocal.remove();;
    }
}
