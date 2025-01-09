import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignUpPage extends BasePage {
    @FindBy(xpath = "/html//div[@id='root']//main/div//a[@href='/register']")
    private WebElement signUpBtn;

    @FindBy(id = "formUsername")
    private WebElement usernameInput;

    @FindBy(id = "formEmail")
    private WebElement emailInput;

    @FindBy(id = "formPassword")
    private WebElement passwordInput;

    @FindBy(xpath = "//div[@id='root']//main/div/form/button[@type='submit']")
    private WebElement createAccountBtn;

    @FindBy(xpath = "//div[@class='col-md-11 col-12 g-0 ']")
    private WebElement homePageIdentifier;

    @FindBy(css = "form [class='mb-3']:nth-of-type(1) .invalid-feedback")
    private WebElement usernameValidationMessage;

    @FindBy(css = "form [class='mb-3']:nth-of-type(3) .invalid-feedback")
    private WebElement passwordValidationMessage;

    @FindBy (xpath = "//div[contains(text(),'Please enter a valid email address.')]")
    private WebElement invalidEmailErrorMessage;

    public SignUpPage(WebDriver driver) {
        super(driver);
    }

    public void openSignupForm() {
        signUpBtn.click();
    }

    public void enterUsernameInput(String username) {
        usernameInput.sendKeys(username);
    }

    public void enterEmailInput(String email) {
        emailInput.sendKeys(email);
    }

    public void enterPasswordInput(String password) {
        passwordInput.sendKeys(password);
    }

    public void clickCreateAccountBtn() {
        createAccountBtn.click();
    }

    public boolean checkIfRegistered() {
        return homePageIdentifier.isDisplayed();
    }

    public boolean getTextExistingEmailError(String email) {
        WebElement existingEmailError = driver.findElement(By.xpath("//div[text()='User with email: " + email + ", already exist.']"));
        return existingEmailError.isDisplayed();
    }

    public boolean getTextExistingUsernameError(String username) {
        WebElement existingUsernameError = driver.findElement(By.xpath("//div[text()='User with name: " + username + ", already exist.']"));
        return existingUsernameError.isDisplayed();
    }

    public String getTextTooShortUsernameValidation() {
        return usernameValidationMessage.getText();
    }

    public String getTextPasswordValidation() {
        return passwordValidationMessage.getText();
    }
    public String getTextEmailValidationErrorMessage(){
        return invalidEmailErrorMessage.getText();
    }
}
