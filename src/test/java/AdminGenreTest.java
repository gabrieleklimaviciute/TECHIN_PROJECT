import org.junit.jupiter.api.Test;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdminGenreTest extends BaseTest {

    @Test
    void validGenreCreation() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileIcon();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername("admin");
        loginPage.enterPassword("admin");
        loginPage.clickSubmissionButton();

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        assertTrue(loginPage.checkIfLoggedIn());
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        AdminPage adminPage = new AdminPage(driver);
        adminPage.clickAdminPageIcon();
        adminPage.clickAddNewGenreIcon();
        adminPage.enterNewGenreName("Example");
        adminPage.submitNewGenreForm();
        adminPage.clickCloseButton();

        boolean expectedOutcome = adminPage.newGenreIsDisplayed("Example");
        assertTrue(expectedOutcome, "Genre is not created");

    }


    @Test
    void emptyGenreField() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileIcon();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername("admin");
        loginPage.enterPassword("admin");
        loginPage.clickSubmissionButton();

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        assertTrue(loginPage.checkIfLoggedIn());
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        AdminPage adminPage = new AdminPage(driver);
        adminPage.clickAdminPageIcon();
        adminPage.clickAddNewGenreIcon();
        adminPage.enterNewGenreName("");
        adminPage.submitNewGenreForm();
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
//        adminPage.clickCloseButton();

//        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        String expectedOutput = "Genre name is required.";
        String actualOutput = adminPage.getTextEmptyGenreFieldError();
        assertEquals(expectedOutput, actualOutput, "Genre name is required");
    }

    @Test
    void genreWithSpecialSymbols() {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickProfileIcon();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.enterUsername("admin");
        loginPage.enterPassword("admin");
        loginPage.clickSubmissionButton();

        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        assertTrue(loginPage.checkIfLoggedIn());
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);

        AdminPage adminPage = new AdminPage(driver);
        adminPage.clickAdminPageIcon();
        adminPage.clickAddNewGenreIcon();
        adminPage.enterNewGenreName("Ex@mp!3");
        adminPage.submitNewGenreForm();
        adminPage.clickCloseButton();

        boolean expectedOutcome = adminPage.newGenreIsDisplayed("Ex@mp!3");
        assertTrue(expectedOutcome, "Genre is not created");
    }
}
