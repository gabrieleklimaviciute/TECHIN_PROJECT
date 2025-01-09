import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LogoutPage extends BasePage{
 @FindBy (xpath = "//main/div/button[@type='button']")
 private WebElement logOutButton;

 @FindBy (xpath = "//div[@class='form-container container']")
 private WebElement loginForm;
    public LogoutPage(WebDriver driver) {
        super(driver);
    }

    public void clickLogoutButton(){
        logOutButton.click();
    }
    public Boolean loginFormIsDisplayed(){
         return loginForm.isDisplayed();

    }
}
