import dataprovider.DataProviders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import common.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Parameters;
import utils.LocatorUtils;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;


public class DemoTests extends BaseTest {
    private static final Logger logger = LogManager.getLogger(DemoTests.class);

    public DemoTests() {
        System.out.println("Inside Constructor");
    }
    @Test(enabled = false)
    public void LoginTest() throws InterruptedException {
        System.out.println("Driver in Demo Class with Thread " + Thread.currentThread().getName() + " : " + driver);
        LocatorUtils.openURL(driver, "https://demoqa.com/login");
        loginPageThreadLocal.get().loginToBookStore("navneetsinghpatel", "Admin@123");
        Assert.assertEquals(profilePageThreadLocal.get().isUserLoggedIn("navneetsinghpatel"), "Success", "User was Not Logged In");
        profilePageThreadLocal.get().logoutOfBookStore();
        Assert.assertTrue(loginPageThreadLocal.get().isLoginPageOpen(), "User was Not Logged Out");
    }

    @Test(enabled = true)
    public void LoginTest2() throws InterruptedException {
        System.out.println("Driver in Demo Class with Thread " + Thread.currentThread().getName() + " : " + driver);
        LocatorUtils.openURL(driver, "https://practicetestautomation.com/practice-test-login/");
        loginPageThreadLocal.get().loginToPractice("student", "Password123");
        TimeUnit.SECONDS.sleep(5);
        profilePageThreadLocal.get().logoutOfPractice();
        TimeUnit.SECONDS.sleep(5);
    }
    @Test(enabled = false, dataProvider = "loginDataProvider", dataProviderClass = DataProviders.class)
    public void dataProviderTest(String username, String password, String expectedResult) {
        logger.info("Testing Login for Username: " + username + " and Password: " + password +" with expected result: " + expectedResult);
        LocatorUtils.openURL(driver, "https://demoqa.com/login");
        loginPage.loginToBookStore(username, password);
        Assert.assertEquals(profilePage.isUserLoggedIn(username), expectedResult, "User was Not Logged In");
        if(expectedResult.equals("Success")) {
            profilePage.logoutOfBookStore();
        }
        Assert.assertTrue(loginPage.isLoginPageOpen(), "User was Not Logged Out");
    }
}
