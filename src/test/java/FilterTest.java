import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilterTest extends BaseTest{

    @Test
    void mediaFilterByGenreTest() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.clickDropdownButton();

        List<WebElement> genres = driver.findElements(By.xpath("//a[@class='dropdown-item']"));
        int randomIndex = (int) (Math.random() * genres.size());
        String randomGenre = genres.get(randomIndex).getText();

        mainPage.selectGenre(randomGenre);
        boolean expectedOutcome = mainPage.identifyFilteredMedia(randomGenre);
        assertTrue(expectedOutcome,"Genres do not match");
    }
}
