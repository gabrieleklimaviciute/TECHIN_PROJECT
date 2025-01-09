import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MainPage extends BasePage {

    @FindBy(xpath = "//*[name()='path' and contains(@d,'M224 256c7')]")
    private WebElement profileIcon;

    @FindBy(css = ".search-form > .form-control.search-input")
    private WebElement searchBarField;

    @FindBy(xpath = "//h3[@class='movie-card-title']")
    private WebElement movieCardTitle;

    @FindBy(xpath = "//*[contains(text(), 'No movies found')]")
    private WebElement moviesNotFoundError;

    @FindBy(xpath = "//div[@class='col-md-11 col-12 g-0 ']")
    private WebElement homePageIdentifier;

    // Filter
    @FindBy(xpath = "//button[@id='dropdown-basic']")
    private WebElement genreDropdown;

    @FindBy(css = ".dropdown-menu .dropdown-item")
    private List<WebElement> allGenres;

    @FindBy(xpath = "//div[@class='movie-card']")
    private List<WebElement> returnedMedia;

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public void clickProfileIcon() {
        profileIcon.click();
    }

    public void enterMediaName(String media) {
        searchBarField.sendKeys(media);
    }

    public String getTitleText() {
        return movieCardTitle.getText();
    }

    public String getNotFoundError() {
        return moviesNotFoundError.getText();
    }

    public boolean checkIfAllContentDisplayed() {
        return homePageIdentifier.isDisplayed();
    }

    public void clickDropdownButton() {
        genreDropdown.click();
    }

    public void selectGenre(String genre) throws InterruptedException {
        WebElement randomGenre = driver.findElement(By.xpath("//a[text()='" + genre + "']"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView(true);", randomGenre);

        wait.until(ExpectedConditions.visibilityOfAllElements(allGenres));
        randomGenre.click();
    }

    public boolean identifyFilteredMedia(String genre) throws InterruptedException {
        List<String> allGenresList = new ArrayList<>();
        List<String> genresOfMediaList = new ArrayList<>();

        if (Objects.equals(genre, "All Genres")) {
            clickDropdownButton();
            for (WebElement allGenre : allGenres) {
                allGenresList.add(allGenre.getText());
            }
            allGenresList.removeFirst();

            for (WebElement mediaCard : returnedMedia) {
                String genresOfMedia = mediaCard.findElement(By.cssSelector("p")).getText();
                String[] genresArray = genresOfMedia.split(",");
                for (String genreWithWhiteSpace : genresArray) {
                    genresOfMediaList.add(genreWithWhiteSpace.trim());
                }

                for (String mediaGenre : genresOfMediaList) {
                    if (!allGenresList.contains(mediaGenre)) {
                        throw new IllegalArgumentException("Genre '" + mediaGenre + "' from genresOfMediaList does not exist in allGenresList.");
                    }
                }
            }
        } else {
            for (WebElement mediaCard : returnedMedia) {
                String genresOfMedia = mediaCard.findElement(By.cssSelector("p")).getText();
                if (!genresOfMedia.contains(genre)) {
                    return false;
                }
            }
            return true;
        }
        return true;
    }
}
