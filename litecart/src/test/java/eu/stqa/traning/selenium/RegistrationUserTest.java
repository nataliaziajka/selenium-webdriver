package eu.stqa.traning.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by Natalia on 2017-11-15.
 */
public class RegistrationUserTest {
    private WebDriver webdriver;
    private WebDriverWait wait;

    @Before
    public void setup() {
        webdriver = new ChromeDriver();
        webdriver.get("http://localhost:8012/litecart/en/");
        webdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

    }

    @Test
    public void testRegistrationUser() {
        webdriver.navigate().to("http://localhost:8012/litecart/en/create_account");

        String name = "Natalia";
        String surname = "Ziajka";
        String address = "ul. Seleniowa 5";
        String zip = "31-000";
        String city = "Cracow";
        Random random = new Random();
        int number = random.nextInt(100);
        String email = "nataliaz" + number + "@sab.pl";
        String phone = "+48111222444";
        String password = "password";

        new WebDriverWait(webdriver, 1000).until(ExpectedConditions.textToBePresentInElementLocated(By.tagName("h1"), "Create Account"));
        WebElement firstname = webdriver.findElement(By.name("firstname"));
        firstname.sendKeys(name);
        WebElement lastname = webdriver.findElement(By.name("lastname"));
        lastname.sendKeys(surname);
        WebElement add = webdriver.findElement(By.name("address1"));
        add.sendKeys(address);
        WebElement postcode = webdriver.findElement(By.name("postcode"));
        postcode.sendKeys(zip);
        WebElement city1 = webdriver.findElement(By.name("city"));
        city1.sendKeys(city);
        WebElement mail = webdriver.findElement(By.name("email"));
        mail.sendKeys(email);
        WebElement phoneNumber = webdriver.findElement(By.name("phone"));
        phoneNumber.sendKeys(phone);
        WebElement pass = webdriver.findElement(By.name("password"));
        pass.sendKeys(password);
        WebElement pass2 = webdriver.findElement(By.name("confirmed_password"));
        pass2.sendKeys(password);
        WebElement confirmButton = webdriver.findElement(By.name("create_account"));
        confirmButton.click();
        logout();
        loginAsCustomer(email,password);
        logout();
    }

    public void logout() {
        new WebDriverWait(webdriver, 1000).until(ExpectedConditions.visibilityOfAllElements(webdriver.findElements(By.xpath("//a[contains(text(),'Logout')]"))));
        webdriver.findElements(By.xpath("//a[contains(text(),'Logout')]")).get(0).click();
        new WebDriverWait(webdriver, 1000).until(ExpectedConditions.visibilityOf(webdriver.findElement(By.cssSelector("div.notice")))).getText().equals("You are now logged out.");
    }

    public void loginAsCustomer(String email, String password) {
        WebElement mail = webdriver.findElement(By.name("email"));
        mail.sendKeys(email);
        WebElement pass = webdriver.findElement(By.name("password"));
        pass.sendKeys(password);
        WebElement name = webdriver.findElement(By.name("login"));
        name.click();
        new WebDriverWait(webdriver, 1000).until(ExpectedConditions.visibilityOf(webdriver.findElement(By.cssSelector("div.notice")))).getText().equals("You are now logged in as");
    }

    @After
    public void teardown() {
        webdriver.quit();
        webdriver = null;
    }


}
