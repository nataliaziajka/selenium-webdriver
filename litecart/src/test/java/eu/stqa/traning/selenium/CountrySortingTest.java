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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Created by Natalia on 2017-11-12.
 */
public class CountrySortingTest {

    private WebDriver webdriver;
    private WebDriverWait wait;

    @Before
    public void setup() {
        webdriver = new ChromeDriver();
        webdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(webdriver, 10);
    }

    @Test
    public void checkIfCountriesAreSortedInAlphabeticalOrder() throws InterruptedException {
        webdriver.get("http://localhost:8012/litecart/admin/login.php");
        webdriver.findElement(By.name("username")).sendKeys("admin");
        webdriver.findElement(By.name("password")).sendKeys("admin");

        WebElement button = webdriver.findElement(By.xpath(".//*[@id='box-login']/form/div[2]/button"));
        if (!webdriver.findElement(By.name("remember_me")).isSelected()) {
            webdriver.findElement(By.name("remember_me")).click();

        }
        button.click();
        Assert.assertTrue(wait.until(ExpectedConditions.titleIs("My Store")));

        webdriver.navigate().to("http://localhost:8012/litecart/admin/?app=countries&doc=countries");

        List<String> countriesNames = new ArrayList<>();
        List<String> countriesNamesSorted = new ArrayList<>();
        WebElement country;
        List<WebElement> countriesList;
        int zones = 0;
        int size = webdriver.findElements(By.cssSelector("tr.row")).size();

        for (int i = 0; i < size; i++) {
            countriesList = webdriver.findElements(By.cssSelector("tr.row"));
            country = countriesList.get(i).findElement(By.xpath("td[6]"));
            zones = Integer.parseInt(country.getText());
            if (zones != 0) {
                countriesList.get(i).findElement(By.cssSelector(".row>td>a")).click();
                List<WebElement> countriesListWithZones = webdriver.findElements(By.cssSelector("table.dataTable tr"));
                for (int j = 0; j < countriesListWithZones.size(); j++) {
                    countriesNames.add(countriesListWithZones.get(j).findElement(By.xpath("td[3]")).getText());
                }

                Collections.sort(countriesNames);
                Assert.assertEquals(countriesNamesSorted,countriesNames);

            }
            countriesNames.clear();
            countriesNamesSorted.clear();
            webdriver.navigate().back();


        }


    }

    @After
    public void teardown() {
        webdriver.quit();
        webdriver = null;
    }
}