package eu.stqa.traning.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;


/**
 * Created by Natalia on 2017-11-03.
 */
public class LoginTest {

    private WebDriver webdriver;
    private WebDriverWait wait;

    @Before
    public void setup() {
        //webdriver = new ChromeDriver();
        //webdriver = new EdgeDriver();
        //webdriver = new FirefoxDriver(); //new schema enale wlaczona
          DesiredCapabilities caps = new DesiredCapabilities();
          caps.setCapability(FirefoxDriver.MARIONETTE, false);  // new schema disable
          webdriver = new FirefoxDriver(caps);
          wait = new WebDriverWait(webdriver, 10);

    }

    @Test
    public void loginTest() throws InterruptedException {
        webdriver.get("http://localhost:8012/litecart/admin/login.php");
        webdriver.findElement(By.name("username")).sendKeys("admin");
        webdriver.findElement(By.name("password")).sendKeys("admin");

        WebElement button = webdriver.findElement(By.xpath(".//*[@id='box-login']/form/div[2]/button"));
        if (!webdriver.findElement(By.name("remember_me")).isSelected()) {
        webdriver.findElement(By.name("remember_me")).click();

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