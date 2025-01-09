import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class SignupTest extends BaseTest {
    Random random = new Random();
    Faker faker = new Faker();


    @Test
    void validCredentials() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileIcon();

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.openSignupForm();

        signUpPage.enterUsernameInput(faker.name().username());
        signUpPage.enterEmailInput(faker.internet().emailAddress());
        signUpPage.enterPasswordInput("P@ssword123");
        signUpPage.clickCreateAccountBtn();

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        assertTrue(signUpPage.checkIfRegistered());

    }

    @Test
    void usernameWithSpecialSymbols() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileIcon();

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.openSignupForm();

        signUpPage.enterUsernameInput(faker.name().username() + "@");
        signUpPage.enterEmailInput("test" + random.nextInt(1000) + "@example.com");
        signUpPage.enterPasswordInput("P@ssword123");
        signUpPage.clickCreateAccountBtn();

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        assertTrue(signUpPage.checkIfRegistered());
    }

    @Test
    void userExist() {

        String username = faker.name().username();
        String email = faker.internet().emailAddress();

        String jsonPayload = "{"
                + "\"username\":\"" + username + "\","
                + "\"email\":\"" + email + "\","
                + "\"password\":\"P@ssword123\""
                + "}";

        PostRequest.sendPostRequest("http://localhost:8080/auth/signup", jsonPayload);

        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileIcon();

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.openSignupForm();

        signUpPage.enterUsernameInput(username);
        signUpPage.enterEmailInput(email);
        signUpPage.enterPasswordInput("P@ssword123");
        signUpPage.clickCreateAccountBtn();

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        assertTrue(signUpPage.getTextExistingUsernameError(username), "Username already exists");
    }

    @Test
    void usernameTooShort() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileIcon();
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.openSignupForm();

        signUpPage.enterUsernameInput("A");
        signUpPage.enterEmailInput("test" + random.nextInt(1000) + "@example.com");
        signUpPage.enterPasswordInput("P@ssword123");
        signUpPage.clickCreateAccountBtn();

        String expectedText = "Username must be between 2 and 30 characters long and contain no unsupported characters.";
        String actualText = signUpPage.getTextTooShortUsernameValidation();
        assertEquals(expectedText, actualText, "Username must have at least 2 characters");
    }

    @Test
    void usernameTooLong() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileIcon();
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.openSignupForm();

        signUpPage.enterUsernameInput("EpicGalaxyVoyager_2024_Powerful");
        signUpPage.enterEmailInput("test" + random.nextInt(1000) + "@example.com");
        signUpPage.enterPasswordInput("P@ssword123");
        signUpPage.clickCreateAccountBtn();

        String expectedText = "Username must be between 2 and 30 characters long and contain no unsupported characters.";
        String actualText = signUpPage.getTextTooShortUsernameValidation();
        assertEquals(expectedText, actualText, "Username must have at least 2 characters");
    }

    @Test
    void usernameEmpty() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileIcon();
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.openSignupForm();

        signUpPage.enterUsernameInput("");
        signUpPage.enterEmailInput("test" + random.nextInt(1000) + "@example.com");
        signUpPage.enterPasswordInput("P@ssword123");
        signUpPage.clickCreateAccountBtn();

        // Need to create username validation message for empty username.
        // Message could be "This field is required" or something
        //CREATE ASSERT WHEN MESSAGE WILL BE CREATED
    }

    @Test
    void usernameWithUnsupportedChar() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileIcon();
        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.openSignupForm();

        signUpPage.enterUsernameInput("Джон_Доу");
        signUpPage.enterEmailInput("test" + random.nextInt(1000) + "@example.com");
        signUpPage.enterPasswordInput("P@ssword123");
        signUpPage.clickCreateAccountBtn();

        String expectedText = "Username must be between 2 and 30 characters long and contain no unsupported characters.";
        String actualText = signUpPage.getTextTooShortUsernameValidation();
        assertEquals(expectedText, actualText, "Username must have at least 2 characters");
    }

    @Test
    void usernameWithLTLetters() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileIcon();

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.openSignupForm();

        signUpPage.enterUsernameInput(faker.name().username() + "čė");
        signUpPage.enterEmailInput("test" + random.nextInt(1000) + "@example.com");
        signUpPage.enterPasswordInput("P@ssword123");
        signUpPage.clickCreateAccountBtn();

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        assertTrue(signUpPage.checkIfRegistered());

    }
//EMAIL

    @Test
    void invalidEmailFormat() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileIcon();

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.openSignupForm();

        signUpPage.enterUsernameInput("user" + random.nextInt(1000));
        signUpPage.enterEmailInput("test" + random.nextInt(1000) + "example.com");
        signUpPage.enterPasswordInput("P@ssword123");
        signUpPage.clickCreateAccountBtn();

        String expectedOutput = "Please enter a valid email address.";
        String actualOutput = signUpPage.getTextEmailValidationErrorMessage();
        assertEquals(expectedOutput, actualOutput, "Email format is not supported");

    }

    @Test
    void invalidEmailFormatMissingDot() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileIcon();

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.openSignupForm();

        signUpPage.enterUsernameInput("user" + random.nextInt(1000));
        signUpPage.enterEmailInput("test" + random.nextInt(1000) + "@examplecom");
        signUpPage.enterPasswordInput("P@ssword123");
        signUpPage.clickCreateAccountBtn();

        String expectedOutput = "Please enter a valid email address.";
        String actualOutput = signUpPage.getTextEmailValidationErrorMessage();
        assertEquals(expectedOutput, actualOutput, "Email format is not supported");

    }

    @Test
    void existingEmail() {

        Faker faker = new Faker();
        String username = faker.name().username();
        String email = faker.internet().emailAddress();

        String jsonPayload = "{"
                + "\"username\":\"" + username + "\","
                + "\"email\":\"" + email + "\","
                + "\"password\":\"P@ssword123\""
                + "}";

        PostRequest.sendPostRequest("http://localhost:8080/auth/signup", jsonPayload);

        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileIcon();

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.openSignupForm();

        signUpPage.enterUsernameInput(faker.name().username());
        signUpPage.enterEmailInput(email);
        signUpPage.enterPasswordInput("P@ssword123");
        signUpPage.clickCreateAccountBtn();

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        assertTrue(signUpPage.getTextExistingEmailError(email), "Email already exist");
    }

    //PASSWORD
    @Test
    void passwordTooShort() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileIcon();

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.openSignupForm();

        signUpPage.enterUsernameInput("user" + random.nextInt(1000));
        signUpPage.enterEmailInput("test" + random.nextInt(1000) + "@example.com");
        signUpPage.enterPasswordInput("P@ss1");
        signUpPage.clickCreateAccountBtn();

        String expectedAlert = "Password must be between 6 and 30 characters long, include one uppercase letter, and one special symbol.";
        String actualAlert = signUpPage.getTextPasswordValidation();
        assertEquals(expectedAlert, actualAlert, "Password contains invalid format");
    }

    @Test
    void passwordTooLong() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileIcon();

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.openSignupForm();

        signUpPage.enterUsernameInput("user" + random.nextInt(1000));
        signUpPage.enterEmailInput("test" + random.nextInt(1000) + "@example.com");
        signUpPage.enterPasswordInput("VeryL00ng_P@ssword_Fortesting12");
        signUpPage.clickCreateAccountBtn();

        String expectedAlert = "Password must be between 6 and 30 characters long, include one uppercase letter, and one special symbol.";
        String actualAlert = signUpPage.getTextPasswordValidation();
        assertEquals(expectedAlert, actualAlert, "Password contains invalid format");
    }

    @Test
    void passwordMissingUpperCase() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileIcon();

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.openSignupForm();

        signUpPage.enterUsernameInput("user" + random.nextInt(1000));
        signUpPage.enterEmailInput("test" + random.nextInt(1000) + "@example.com");
        signUpPage.enterPasswordInput("p@ssword123");
        signUpPage.clickCreateAccountBtn();

        String expectedAlert = "Password must be between 6 and 30 characters long, include one uppercase letter, and one special symbol.";
        String actualAlert = signUpPage.getTextPasswordValidation();
        assertEquals(expectedAlert, actualAlert, "Password contains invalid format");
    }

    @Test
    void passwordMissingUpperCaseIntSpecSymbol() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileIcon();

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.openSignupForm();

        signUpPage.enterUsernameInput("user" + random.nextInt(1000));
        signUpPage.enterEmailInput("test" + random.nextInt(1000) + "@example.com");
        signUpPage.enterPasswordInput("password");
        signUpPage.clickCreateAccountBtn();

        String expectedAlert = "Password must be between 6 and 30 characters long, include one uppercase letter, and one special symbol.";
        String actualAlert = signUpPage.getTextPasswordValidation();
        assertEquals(expectedAlert, actualAlert, "Password contains invalid format");
    }

    @Test
    void passwordMissingIntSpecSymbol() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileIcon();

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.openSignupForm();

        signUpPage.enterUsernameInput("user" + random.nextInt(1000));
        signUpPage.enterEmailInput("test" + random.nextInt(1000) + "@example.com");
        signUpPage.enterPasswordInput("Password");
        signUpPage.clickCreateAccountBtn();

        String expectedAlert = "Password must be between 6 and 30 characters long, include one uppercase letter, and one special symbol.";
        String actualAlert = signUpPage.getTextPasswordValidation();
        assertEquals(expectedAlert, actualAlert, "Password contains invalid format");
    }

    @Test
    void passwordMissingSpecSymbol() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileIcon();

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.openSignupForm();

        signUpPage.enterUsernameInput("user" + random.nextInt(1000));
        signUpPage.enterEmailInput("test" + random.nextInt(1000) + "@example.com");
        signUpPage.enterPasswordInput("Password123");
        signUpPage.clickCreateAccountBtn();

        String expectedAlert = "Password must be between 6 and 30 characters long, include one uppercase letter, and one special symbol.";
        String actualAlert = signUpPage.getTextPasswordValidation();
        assertEquals(expectedAlert, actualAlert, "Password contains invalid format");
    }

    @Test
    void passwordEmpty() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileIcon();

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.openSignupForm();

        signUpPage.enterUsernameInput("user" + random.nextInt(1000));
        signUpPage.enterEmailInput("test" + random.nextInt(1000) + "@example.com");
        signUpPage.enterPasswordInput("");
        signUpPage.clickCreateAccountBtn();

        // ADD password validation message when password field is empty.
        // Message should be "Password must be between 6 and 30 characters long, include one uppercase letter, and one special symbol."
        // OR "Password is required"
        //WRITE CORRECT ASSERT, DEPENDING ON CREATED ERROR MESSAGE
    }

    @Test
    void allInputFieldsEmpty() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileIcon();

        SignUpPage signUpPage = new SignUpPage(driver);
        signUpPage.openSignupForm();

        signUpPage.enterUsernameInput("");
        signUpPage.enterEmailInput("");
        signUpPage.enterPasswordInput("");
        signUpPage.clickCreateAccountBtn();

        // Validation Message when attempting to register with all empty fields.
        // Message should be something like " All fields are required"
        //CREATE ASSERT WHEN ERROR MESSAGE WILL BE CREATED


    }
}
