package pl.stqa.traning.selenium;

/**
 * Created by Natalia on 2017-11-01.
 */

import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;


public class TestBase {


    public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<>();
    public WebDriver driver;
    public WebDriverWait wait;


    @Before

    public void start() {

        if (tlDriver.get() != null) {

            driver = tlDriver.get();

            wait = new WebDriverWait(driver, 10);

            return;

        }

        driver = new ChromeDriver();

        tlDriver.set(driver);

        wait = new WebDriverWait(driver, 10);

    }

    @After

    public void stop() {
        driver.quit();

    }

}

