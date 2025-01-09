import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.SQLOutput;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchBarTest extends BaseTest {
    @Test
    void existingMediaSearch() {
        MainPage mainPage = new MainPage(driver);
        mainPage.enterMediaName("Avatar");

        String expectedTitle = "Avatar";
        String actualTitle = mainPage.getTitleText();
        assertEquals(expectedTitle, actualTitle, "Title do not match");
    }

    @Test
    void notExistingMediaSearch() {
        MainPage mainPage = new MainPage(driver);
        mainPage.enterMediaName("Unknown Movie");

        String expectedOutcome = "No movies found matching your search criteria.";
        String actualOutcome = mainPage.getNotFoundError();
        assertEquals(expectedOutcome, actualOutcome, "Errors not match");
    }

    @Test
    void emptySearchBar() {
        MainPage mainPage = new MainPage(driver);
        mainPage.enterMediaName("");

        assertTrue(mainPage.checkIfAllContentDisplayed(), "Not Main Page Displayed");
    }

    @Test
    void mediaWithSpecialSymbols() {
        MainPage mainPage = new MainPage(driver);
        mainPage.enterMediaName("Av@tar");

        String expectedOutcome = "No movies found matching your search criteria.";
        String actualOutcome = mainPage.getNotFoundError();
        assertEquals(expectedOutcome, actualOutcome, "Errors not match");
    }
}
