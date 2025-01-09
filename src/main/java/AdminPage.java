import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AdminPage extends BasePage {

    @FindBy(xpath = "//a[@href='/admin']//*[name()='svg']")
    private WebElement adminPageIcon;

    @FindBy(xpath = "//div[@class='col d-flex align-content-center justify-content-center']//*[name()='svg']")
    private WebElement addNewGenreIcon;

    @FindBy(id = "formGenreName")
    private WebElement formGenreName;

    @FindBy(xpath = "//div[@id='genreFormModal']//button[@type='submit']")
    private WebElement newGenreFormSubmission;

    @FindBy(xpath = "//div[@id='genreFormModal']//form//button[@type='button']")
    private WebElement newGenreFormCloseButton;

    @FindBy (xpath = "//div[@id='genreFormModal']//div[@role='alert']")
    private WebElement emptyGenreFieldError;

    public AdminPage(WebDriver driver) {
        super(driver);
    }

    public void clickAdminPageIcon() {
        adminPageIcon.click();
    }

    public void clickAddNewGenreIcon() {
        addNewGenreIcon.click();
    }

    public void enterNewGenreName(String name) {
        formGenreName.sendKeys(name);
    }

    public void submitNewGenreForm() {
        newGenreFormSubmission.click();
    }

    public void clickCloseButton() {
        newGenreFormCloseButton.click();
    }

    public Boolean newGenreIsDisplayed(String newGenre) {
        WebElement addedGenre = driver.findElement(By.xpath("//div/span[text()='" + newGenre + "']"));
        return addedGenre.isDisplayed();
    }
    public String getTextEmptyGenreFieldError(){
         return emptyGenreFieldError.getText();

    }
}
