import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LogoutTest extends BaseTest {
    @Test
    void logOutTest() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileIcon();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername("user2");
        loginPage.enterPassword("user2");
        loginPage.clickSubmissionButton();

       driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        assertTrue(loginPage.checkIfLoggedIn());

        mainPage.clickProfileIcon();
        LogoutPage logoutPage = new LogoutPage(driver);
        driver.manage().timeouts().implicitlyWait(2,TimeUnit.SECONDS);
        logoutPage.clickLogoutButton();

        Boolean expectedOutcome = logoutPage.loginFormIsDisplayed();
        Assertions.assertTrue(expectedOutcome, "User is not logged out");
    }
}
