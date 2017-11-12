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

import java.util.concurrent.TimeUnit;

/**
 * Created by Natalia on 2017-11-11.
 */
public class AdminMenuNavigationTest {

    private WebDriver webdriver;
    private WebDriverWait wait;

    @Before
    public void setup() {
        webdriver = new ChromeDriver();
        webdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(webdriver, 10);

    }

    @Test
    public void walkThroughAllElementsInMenu() throws InterruptedException {
        webdriver.get("http://localhost:8012/litecart/admin/login.php");
        webdriver.findElement(By.name("username")).sendKeys("admin");
        webdriver.findElement(By.name("password")).sendKeys("admin");

        WebElement button = webdriver.findElement(By.xpath(".//*[@id='box-login']/form/div[2]/button"));
        if (!webdriver.findElement(By.name("remember_me")).isSelected()) {
            webdriver.findElement(By.name("remember_me")).click();

        }
        button.click();
        Assert.assertTrue(wait.until(ExpectedConditions.titleIs("My Store")));

        int numberSubmenuElement = 0;
        int numberElementsInMenu = webdriver.findElements(By.cssSelector("ul#box-apps-menu li")).size();
        for (int i = 0; i < numberElementsInMenu; i++) {
            WebElement menuElement = webdriver.findElements(By.cssSelector("ul#box-apps-menu li")).get(i + numberSubmenuElement);

            String menuText = menuElement.getText();
            menuElement.click();

            numberSubmenuElement = webdriver.findElements(By.cssSelector("ul#box-apps-menu ul li")).size();
            for (int j = 1; j <= numberSubmenuElement; j++) {
                WebElement submenuElement = webdriver.findElement(By.cssSelector("ul#box-apps-menu ul li:nth-child(" + j + ")"));

                String textFromSubmenu = submenuElement.getText();
                submenuElement.click();

                String h1Text = webdriver.findElement(By.cssSelector("h1")).getText();
                if (menuText.equals("Settings")) {
                    Assert.assertEquals("Settings", h1Text);
                } else if (textFromSubmenu.equals("Background Jobs")) {
                    Assert.assertEquals("Job Modules", h1Text);
                } else if (menuText.equals("Modules")) {
                    Assert.assertEquals(textFromSubmenu + " Modules", h1Text);
                } else if (textFromSubmenu.equals("Scan Files")) {
                    Assert.assertEquals(textFromSubmenu + " For Translations", h1Text);
                } else {
                    Assert.assertEquals(textFromSubmenu, h1Text);
                }
            }
        }

    }

    @After
    public void teardown() {
        webdriver.quit();
        webdriver = null;
    }

}
