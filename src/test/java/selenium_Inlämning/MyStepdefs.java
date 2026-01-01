package selenium_Inlämning;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import org. openqa. selenium. support. ui. ExpectedConditions;
import org.openqa.selenium.By;
import org. openqa.selenium.Keys;
import org. openqa.selenium.WebElement;
import static org.junit.Assert.assertTrue;
import org.openqa.selenium.firefox.FirefoxDriver;
import io.cucumber.java.After;


public class MyStepdefs {

    private WebDriver driver;
    private WebDriverWait wait;

    /*@Given("I open the registration page")
    public void iOpenTheRegistrationPage() {
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("file:///Users/mjdator/Library/Mobile%20Documents/com~apple~CloudDocs/0,1%20Utbildning%20/02%20Del%203.%20Testautomatisering%20och%20programmering/Nästa%20InlämningsUppgift/Register/Register.html"); // din riktiga fil-url
    }*/
    //---------Dubbla Browser--------------
    @Given("I open the registration page")
    public void iOpenTheRegistrationPage() {

        // välj browser via -Dbrowser=chrome / -Dbrowser=firefox
        String browser = System.getProperty("browser", "chrome").toLowerCase();

        // istället för alltid ChromeDriver
        switch (browser) {
            case "firefox" -> driver = new FirefoxDriver();
            case "chrome" -> driver = new ChromeDriver();
            default -> throw new IllegalArgumentException(
                    "Unsupported browser: " + browser + ". Use -Dbrowser=chrome or -Dbrowser=firefox"
            );
        }

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // gör fönstret lite större en den lilla browserrutan så jag ser.
        driver.manage().window().maximize(); //

        driver.get("file:///Users/mjdator/Library/Mobile%20Documents/com~apple~CloudDocs/0,1%20Utbildning%20/02%20Del%203.%20Testautomatisering%20och%20programmering/Nästa%20InlämningsUppgift/Register/Register.html");
    }
    //-------------------------------------

    @When("I enter first name {string}")
    public void iEnterFirstName(String firstName) {
        WebDriverWait Name = new WebDriverWait(driver, Duration.ofSeconds(10));
        //Name.until(ExpectedConditions.elementToBeClickable(By.id("member_firstname"))).sendKeys("Klas");
        Name.until(ExpectedConditions.elementToBeClickable(By.id("member_firstname"))) //Gjorde om för att få mindre "Hårdkodat" och kan användas flera gånger
                .sendKeys(firstName);
    }

    @And("I enter last name {string}")
    public void iEnterLastName(String lastName) {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("member_lastname")))
                .sendKeys(lastName);
    }
        //Toppen att jag hittade dob date of birth och dob field Då letar den efter alla sånna fält och sparar massa kod och fel
    @And("I enter date of birth {string}")
    public void iEnterDateOfBirth(String dob) {
        var dobField = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("input[name='DateOfBirth'], input[id*='DateOfBirth'], input[placeholder*='DD']")));
        dobField.click();
        dobField.sendKeys(Keys.chord(Keys.COMMAND, "a"));
        dobField.sendKeys(dob);
        dobField.sendKeys(Keys.ENTER);
    }

    @And("I enter email {string}")
    public void iEnterEmail(String email) {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("member_emailaddress")))
                .sendKeys(email);
    }

    @And("I confirm email {string}")
    public void iConfirmEmail(String confirmEmail) {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.id("member_confirmemailaddress")
        )).sendKeys(confirmEmail);
    }

    @And("I enter password {string}")
    public void iEnterPassword(String password) {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("signupunlicenced_password")))
                .sendKeys(password);
    }

    @And("I confirm password {string}")
    public void iConfirmPassword(String confirmPassword) {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("signupunlicenced_confirmpassword")))
                .sendKeys(confirmPassword);
    }
//------------------------
    /*@And("I accept terms and conditions")
    public void iAcceptTermsAndConditions() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("sign_up_25")))
                .click();
    }

    @And("I confirm that I am over 18 years old")
    public void iConfirmThatIAmOver18YearsOld() {
        wait.until(ExpectedConditions.elementToBeClickable(By.id("sign_up_26")))
                .click();
    }*/
    //---------------------NY Kod fungerar
    @And("I accept terms and conditions")
    public void iAcceptTermsAndConditions() {
        wait.until(ExpectedConditions.elementToBeClickable(
                        By.cssSelector("label[for='sign_up_25']")))
                .click();
    }

    @And("I confirm that I am over 18 years old")
    public void iConfirmThatIAmOver18YearsOld() {
        wait.until(ExpectedConditions.elementToBeClickable(
                        By.cssSelector("label[for='sign_up_26']")))
                .click();
    }
//-----------
   /* @And("I accept code of ethics and conduct")
    public void iAcceptCodeOfEthicsAndConduct() {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.id("fanmembersignup_agreetocodeofethicsandconduct")
        )).click();
    }*/

    //---------NY fungerar-----------
    @And("I accept code of ethics and conduct") //Fick använda "Lable" då fungerade det
    public void iAcceptCodeOfEthicsAndConduct() {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("label[for='fanmembersignup_agreetocodeofethicsandconduct']")
        )).click();
    }

    @And("I submit the registration form")
    public void iSubmitTheRegistrationForm() {
        wait.until(ExpectedConditions.elementToBeClickable(By.name("join")))
                .click();
    }
    //-------------  OBS!! KLAR KOD OVAN!!! ---------

    @Then("I should see a success message") // tog så den hittade en header text för att veta att det gått igenom
    public void iShouldSeeASuccessMessage() {
        WebElement header = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("div.page-content-wrapper h2")
        ));

        String text = header.getText();
        assertTrue(text.contains("THANK YOU FOR CREATING AN ACCOUNT WITH"));
        assertTrue(text.contains("BASKETBALL ENGLAND"));
    }

    @And("I leave last name empty")
    public void iLeaveLastNameEmpty() {
        //Jag skriver inget här, och lämnar efternamn tomt.
    }

    @Then("I should see an error message for {string}")
    public void iShouldSeeAnErrorMessageFor(String field) {

        String valmsgFor = switch (field) {
            case "lastName" -> "Surname";
            case "email" -> "EmailAddress";
            default -> field;
        };

        WebElement msg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("span[data-valmsg-for='" + valmsgFor + "'] span")
                )
        );

        String text = msg.getText();

        assertTrue(
                "Unexpected message: " + text,
                text.toLowerCase().contains("required")
        );
    }

    @Then("I should see a password mismatch error")
    public void iShouldSeeAPasswordMismatchError() {

        WebElement msg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("span[data-valmsg-for='ConfirmPassword'] span")
                )
        );

        String text = msg.getText();

        assertTrue(
                "Unexpected password error message: " + text,
                text.toLowerCase().contains("did not match")
        );
    }

    @And("I do not accept terms and conditions")
    public void iDoNotAcceptTermsAndConditions() {

        WebElement termsCheckbox = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.name("TermsAccept")
                )
        );

        // Säkerställ att den INTE är ikryssad
        if (termsCheckbox.isSelected()) {
            termsCheckbox.click();
        }
    }

    @Then("I should see a terms not accepted error")
    public void iShouldSeeATermsNotAcceptedError() {

        WebElement msg = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("span[data-valmsg-for='TermsAccept'] span")
                )
        );

        String text = msg.getText();

        assertTrue(
                "Unexpected message: " + text,
                text.toLowerCase().contains("must confirm") // .contains är väldigt bra för att lösa och hitta rätt...
        );
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
