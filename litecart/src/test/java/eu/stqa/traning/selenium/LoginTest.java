package eu.stqa.traning.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Natalia on 2017-11-03.
 */
public class LoginTest {

    private WebDriver webdriver;
    private WebDriverWait wait;

    @Before
    public void setup() {
        webdriver = new ChromeDriver();
        wait = new WebDriverWait(webdriver, 10);
    }

    @Test
    public void loginTest() throws InterruptedException {
        webdriver.get("http://localhost:8012/litecart/admin/login.php");
        WebElement id = webdriver.findElement(By.name("username"));

        WebElement pass = webdriver.findElement(By.name("password"));
        WebElement button = webdriver.findElement(By.xpath(".//*[@id='box-login']/form/div[2]/button"));
        id.sendKeys("admin");
        pass.sendKeys("admin");
        if (!webdriver.findElement(By.name("remember_me")).isSelected()) {

        }
        button.submit();
        Assert.assertTrue(wait.until(ExpectedConditions.titleIs("My Store")));

    }

    @After
    public void teardown() {
        webdriver.quit();
        webdriver = null;
    }

}