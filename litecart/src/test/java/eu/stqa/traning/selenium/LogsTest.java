package eu.stqa.traning.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

/**
 * Created by Natalia on 2017-11-29.
 */
public class LogsTest {
    private WebDriver webdriver;
    private WebDriverWait wait;

    @Before
    public void setup() {
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences log = new LoggingPreferences();
        log.enable("browser", Level.ALL);
        log.enable("performance", Level.INFO);
        log.enable("driver", Level.FINE);
        options.setCapability(CapabilityType.LOGGING_PREFS, log);
        webdriver = new ChromeDriver(options);
        webdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(webdriver, 10);
    }

    @Test

    public void testLog() {

        webdriver.get("http://localhost:8012/litecart/admin/login.php");
        webdriver.findElement(By.name("username")).sendKeys("admin");
        webdriver.findElement(By.name("password")).sendKeys("admin");

        WebElement button = webdriver.findElement(By.xpath(".//*[@id='box-login']/form/div[2]/button"));
        if (!webdriver.findElement(By.name("remember_me")).isSelected()) {
            webdriver.findElement(By.name("remember_me")).click();

        }
        button.click();
        Assert.assertTrue(wait.until(ExpectedConditions.titleIs("My Store")));

        webdriver.navigate().to("http://localhost:8012/litecart/admin/?app=catalog&doc=catalog&category_id=1");
        System.out.println(webdriver.manage().logs().getAvailableLogTypes());

        WebElement blueDuck = webdriver.findElement(By.xpath(".//*[@id='content']/form/table/tbody/tr[5]/td[3]/a"));
        blueDuck.click();

        System.out.println("Browser logs:");
        System.out.println(webdriver.manage().logs().get("browser").getAll());
        webdriver.manage().logs().get("browser").forEach(l -> System.out.println(l));
        webdriver.navigate().back();

        System.out.println("Performance logs:");
        WebElement greenDuck = webdriver.findElement(By.xpath(".//*[@id='content']/form/table/tbody/tr[6]/td[3]/a"));
        greenDuck.click();
        System.out.println(webdriver.manage().logs().get("performance").getAll());
        webdriver.manage().logs().get("performance").forEach(l -> System.out.println(l));
        webdriver.navigate().back();

        System.out.println("Driver logs:");
        WebElement purpleDuck = webdriver.findElement(By.xpath(".//*[@id='content']/form/table/tbody/tr[7]/td[3]/a"));
        purpleDuck.click();
        System.out.println(webdriver.manage().logs().get("driver").getAll());
        webdriver.manage().logs().get("driver").forEach(l -> System.out.println(l));

    }

    @After
    public void teardown() {
        webdriver.quit();
        webdriver = null;
    }
}
