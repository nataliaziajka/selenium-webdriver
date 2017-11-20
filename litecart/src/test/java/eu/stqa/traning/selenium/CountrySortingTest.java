package eu.stqa.traning.selenium;

import com.google.common.collect.Lists;
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

        List<WebElement> countriesList = webdriver.findElements(By.cssSelector("tr.row"));
        List<String> countriesTexts = Lists.newArrayList();
        for (WebElement webElement : countriesList) {
            countriesTexts.add(webElement.findElement(By.xpath("td[6]")).getText());
        }

        verifyTopLevel(countriesList);
        verifyInnerLevels(countriesTexts);
    }

    private void verifyInnerLevels(List<String> countriesTexts) {
        List<String> countriesNames = new ArrayList<>();
        List<String> countriesNamesSorted = new ArrayList<>();

        int zones = 0;
        for (int i = 0; i < countriesTexts.size() -1; i++) {
            zones = Integer.parseInt(countriesTexts.get(i));
            if (zones != 0) {
                webdriver.findElements(By.cssSelector("tr.row")).get(i).findElement(By.cssSelector(".row>td>a")).click();
                new WebDriverWait( webdriver, 1000).until( ExpectedConditions.presenceOfAllElementsLocatedBy( By.cssSelector( "table.dataTable tr" )));
                List<WebElement> countriesListWithZones = webdriver.findElements(By.cssSelector("table.dataTable tr"));
                for (int j = 1; j < countriesListWithZones.size()-1; j++) {
                    String text = countriesListWithZones.get(j).findElement(By.xpath("td[3]")).getText();
                    countriesNames.add(text);
                    countriesNamesSorted.add(text);
                }
                Collections.sort(countriesNamesSorted);
                Assert.assertEquals(countriesNamesSorted, countriesNames);
                webdriver.navigate().back();
            }
            countriesNames.clear();
            countriesNamesSorted.clear();
        }
    }

    private void verifyTopLevel(List<WebElement> countriesList) {
        List<String> countriesNames = new ArrayList<>();
        List<String> countriesNamesSorted = new ArrayList<>();

        for (int i = 0; i < countriesList.size() -1; i++) {
            String text = countriesList.get(i).findElement(By.xpath("td[5]")).getText();
            countriesNames.add(text);
            countriesNamesSorted.add(text);
        }

        Collections.sort(countriesNamesSorted);
        Assert.assertEquals(countriesNamesSorted, countriesNames);
    }
    
    @After
    public void teardown() {
        webdriver.quit();
        webdriver = null;
    }
}