import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(id = "formUsername")
    private WebElement loginUsernameInput;

    @FindBy(id = "formPassword")
    private WebElement loginPasswordInput;

    @FindBy(xpath = "//button[text()='Login to your account']")
    private WebElement loginSubmissionButton;

    @FindBy(xpath = "//div[@class='col-md-11 col-12 g-0 ']")
    private WebElement homePageIdentifier;

    @FindBy(xpath = "//div[text()='Invalid username or password.']")
    private WebElement invalidCredentialsErrorMessage;

    @FindBy(xpath = "//div[text()='Username should contain at least 2 letters']")
    private WebElement invalidUsernameErrorMessage;

    @FindBy(xpath = "//div[text()='Should include password']")
    private WebElement emptyPasswordErrorMessage;


    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void enterUsername(String username) {
        loginUsernameInput.sendKeys(username);
    }

    public void enterPassword(String password) {
        loginPasswordInput.sendKeys(password);
    }

    public void clickSubmissionButton() {
        loginSubmissionButton.click();
    }

    public boolean checkIfLoggedIn() {
        return homePageIdentifier.isDisplayed();
    }

    public String getTextInvalidUsernameError() {
        return invalidCredentialsErrorMessage.getText();
    }

    public String getTextInvalidPasswordError() {
        return invalidCredentialsErrorMessage.getText();
    }
    public String getTextInvalidUsernameErrorMessage(){
        return invalidUsernameErrorMessage.getText();
    }

    public String getTextEmptyPasswordErrorMessage(){
        return emptyPasswordErrorMessage.getText();
    }
}
