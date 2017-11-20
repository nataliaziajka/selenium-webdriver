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
 * Created by Natalia on 2017-11-18.
 */
public class GeoZonesSortingTest {

    private WebDriver webdriver;
    private WebDriverWait wait;

    @Before
    public void setup() {
        webdriver = new ChromeDriver();
        webdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(webdriver, 10);
    }

    @Test
    public void checkIfZonesAreSortedInAlphabeticalOrder() throws InterruptedException {
        webdriver.get("http://localhost:8012/litecart/admin/login.php");
        webdriver.findElement(By.name("username")).sendKeys("admin");
        webdriver.findElement(By.name("password")).sendKeys("admin");

        WebElement button = webdriver.findElement(By.xpath(".//*[@id='box-login']/form/div[2]/button"));
        if (!webdriver.findElement(By.name("remember_me")).isSelected()) {
            webdriver.findElement(By.name("remember_me")).click();

        }
        button.click();
        Assert.assertTrue(wait.until(ExpectedConditions.titleIs("My Store")));

        webdriver.navigate().to("http://localhost:8012/litecart/admin/?app=geo_zones&doc=geo_zones");

        List<WebElement> list = webdriver.findElements(By.cssSelector("tr.row"));
        List<String> zonesNamesSorted = new ArrayList<>();

        int size = list.size();
        List<String> zoneNames = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            webdriver.findElements(By.cssSelector("tr.row")).get(i).findElement(By.xpath("//td[3]/a")).click();
            new WebDriverWait(webdriver, 1000).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("table.dataTable tr")));
            List<WebElement> zonesList = webdriver.findElements(By.cssSelector("table.dataTable tr"));
            for (int j = 1; j < zonesList.size() - 1; j++) {
                if (!zonesList.get(j).findElement(By.xpath("td[3]/select")).isEnabled()) {
                    zoneNames.add(zonesList.get(j).findElement(By.xpath("//td[3]/select")).getText());
                } else {
                    zoneNames.add(zonesList.get(j).findElement(By.xpath("//td[3]/select/option[@selected='selected']")).getText());
                }
            }
            Collections.sort(zoneNames);
            zonesNamesSorted = sort(zoneNames);
            Assert.assertEquals(zonesNamesSorted, zoneNames);
            webdriver.navigate().back();

        }
        zoneNames.clear();
        zonesNamesSorted.clear();

    }
    public List<String> sort(List<String> zones) {
        Collections.sort( zones );
        return zones;
    }


    @After
        public void teardown() {
        webdriver.quit();
        webdriver = null;
        }
}
