import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest extends BaseTest {
    Faker faker = new Faker();

    @Test
    void validCredentials() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileIcon();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername("user2");
        loginPage.enterPassword("user2");
        loginPage.clickSubmissionButton();

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        assertTrue(loginPage.checkIfLoggedIn());
    }

    @Test
    void usernameNotRegistered() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileIcon();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername(faker.name().username());
        loginPage.enterPassword("user2");
        loginPage.clickSubmissionButton();

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        String expectedOutcome = "Invalid username or password.";
        String actualOutcome = loginPage.getTextInvalidUsernameError();
        assertEquals(expectedOutcome, actualOutcome, "Username is not registered");

    }

    @Test
    void emptyUsername() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileIcon();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername("");
        loginPage.enterPassword("user2");
        loginPage.clickSubmissionButton();

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        String expectedOutcome = "Username should contain at least 2 letters";
        String actualOutcome = loginPage.getTextInvalidUsernameErrorMessage();
        assertEquals(expectedOutcome, actualOutcome, "Username is too short");
    }

    @Test
    void invalidPassword() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileIcon();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername("user2");
        loginPage.enterPassword("uuuser2");
        loginPage.clickSubmissionButton();

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        String expectedOutcome = "Invalid username or password.";
        String actualOutcome = loginPage.getTextInvalidPasswordError();
        assertEquals(expectedOutcome, actualOutcome, "Password is not valid");

        //appropriate error message should appear, when logging in with invalid PASSWORD.
        //Message could look like "Invalid username or password"
        //CREATE ASSERT when issue is fixed
    }

    @Test
    void emptyPassword() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileIcon();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername("user2");
        loginPage.enterPassword("");
        loginPage.clickSubmissionButton();
    }
     @Test
    void allFieldsEmpty(){
         MainPage mainPage = new MainPage(driver);
         mainPage.clickProfileIcon();

         LoginPage loginPage = new LoginPage(driver);
         loginPage.enterUsername("");
         loginPage.enterPassword("");
         loginPage.clickSubmissionButton();

         driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
         String expectedOutcome = "Username should contain at least 2 letters";
         String actualOutcome = loginPage.getTextInvalidUsernameErrorMessage();
         assertEquals(expectedOutcome, actualOutcome, "Username is required");

         driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
         String expectedMessage = "Should include password";
         String actualMessage = loginPage.getTextEmptyPasswordErrorMessage();
         assertEquals(expectedMessage, actualMessage, "Password is required");


         // Validation Message when attempting to log in with all empty fields.
         // Message should be something like " All fields are required" OR " Invalid username or password"
         //CREATE ASSERT WHEN ERROR MESSAGE WILL BE CREATED
     }

}
