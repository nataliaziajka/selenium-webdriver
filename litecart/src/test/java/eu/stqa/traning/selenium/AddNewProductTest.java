package eu.stqa.traning.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by Natalia on 2017-11-15.
 */
public class AddNewProductTest {

    private WebDriver webdriver;
    private WebDriverWait wait;

    @Before
    public void setup() {
        webdriver = new ChromeDriver();
        webdriver.get("http://localhost:8012/litecart/en/");
        webdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(webdriver, 10);
    }

    @Test

    public void addNewProduct() {

        webdriver.get("http://localhost:8012/litecart/admin/login.php");
        webdriver.findElement(By.name("username")).sendKeys("admin");
        webdriver.findElement(By.name("password")).sendKeys("admin");

        WebElement button = webdriver.findElement(By.xpath(".//*[@id='box-login']/form/div[2]/button"));
        if (!webdriver.findElement(By.name("remember_me")).isSelected()) {
            webdriver.findElement(By.name("remember_me")).click();

        }
        button.click();
        Assert.assertTrue(wait.until(ExpectedConditions.titleIs("My Store")));

        webdriver.navigate().to("http://localhost:8012/litecart/admin/?app=catalog&doc=catalog");

        webdriver.findElement(By.xpath(".//*[@id='content']/div[1]/a[2]")).click();   //button to Add New Product, css: .button:nth-child(2)

        int sizeBefore = webdriver.findElements(By.cssSelector("tr.row")).size();

        // GENERAL TAB
        WebElement namefield = webdriver.findElement(By.name("name[en]"));
        namefield.sendKeys("Name1");
        WebElement code = webdriver.findElement(By.name("code"));
        code.sendKeys("testcode");
        List<WebElement> tables = webdriver.findElements(By.cssSelector("div#tab-general tbody")); //xpath:.//*[@id='tab-general']/table/tbody/tr[4]/td/div
        List<WebElement> productGroups = tables.get(2).findElements(By.cssSelector("tr"));
        productGroups.get(2).findElement(By.cssSelector("td input")).click();
        WebElement quantity = webdriver.findElement(By.name("quantity"));
        quantity.sendKeys("20");
        new Select(webdriver.findElement(By.name("sold_out_status_id"))).selectByValue("2");

        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("picture.jpg").getFile());
        webdriver.findElement(By.name("new_images[]")).sendKeys(file.getAbsolutePath());

        WebElement dateFrom = webdriver.findElement(By.name("date_valid_from"));
        Actions dateFromActions = new Actions(webdriver);
        dateFromActions
                .moveToElement(dateFrom)
                .click()
                .sendKeys("2017")
                .sendKeys(Keys.TAB)
                .sendKeys("01")
                .sendKeys("01")
                .release().perform();
        WebElement dateTo = webdriver.findElement(By.name("date_valid_to"));
        Actions dateToActions = new Actions(webdriver);
        dateToActions
                .moveToElement(dateTo)
                .click()
                .sendKeys("2017")
                .sendKeys(Keys.TAB)
                .sendKeys("12")
                .sendKeys("31")
                .sendKeys(Keys.TAB)
                .release().perform();

        // INFORMATION TAB
        WebElement informationTab = webdriver.findElement(By.xpath(".//*[@id='content']/form/div/ul/li[2]/a"));
        informationTab.click();
        new Select(webdriver.findElement(By.name("manufacturer_id"))).selectByValue("1");
        WebElement keyword = webdriver.findElement(By.name("keywords"));
        keyword.sendKeys("testkeywords");
        WebElement sDesc = webdriver.findElement(By.name("short_description[en]"));
        sDesc.sendKeys("test short description");
        webdriver.findElement(By.cssSelector("div.trumbowyg-editor")).sendKeys("Test description ");
        WebElement title = webdriver.findElement(By.name("head_title[en]"));
        title.sendKeys("test head title");
        WebElement desc = webdriver.findElement(By.name("meta_description[en]"));
        desc.sendKeys("test meta description");

        // PRICES TAB
        WebElement priceTab = webdriver.findElement(By.xpath("//*[@id='content']/form/div/ul/li[4]/a"));
        priceTab.click();
        WebElement purPrice = webdriver.findElement(By.name("purchase_price"));
        purPrice.sendKeys("20");
        new Select(webdriver.findElement(By.name("purchase_price_currency_code"))).selectByValue("USD");
        WebElement price = webdriver.findElement(By.name("prices[USD]"));
        price.sendKeys("20");
        WebElement price2 = webdriver.findElement(By.name("prices[EUR]"));
        price2.sendKeys("30");
        webdriver.findElement(By.cssSelector("a#add-campaign")).click();

        WebElement startDate = webdriver.findElement(By.name("campaigns[new_1][start_date]"));
        Actions startDateActions = new Actions(webdriver);
        startDateActions
                .moveToElement(startDate, 9, 12)
                .click()
                .sendKeys("2017")
                .sendKeys(Keys.TAB)
                .sendKeys("01")
                .sendKeys("01")
                .sendKeys("12")
                .sendKeys("20")
                .release().perform();
        WebElement endDate = webdriver.findElement(By.name("campaigns[new_1][end_date]"));
        Actions endDateActions = new Actions(webdriver);
        endDateActions
                .moveToElement(endDate, 9, 12)
                .click()
                .sendKeys("2017")
                .sendKeys(Keys.TAB)
                .sendKeys("31")
                .sendKeys("12")
                .sendKeys("16")
                .sendKeys("30")
                .sendKeys(Keys.TAB)
                .release().perform();
        WebElement campaign = webdriver.findElement(By.name("campaigns[new_1][percentage]"));
        campaign.sendKeys("40");
        Assert.assertEquals("12.00", webdriver.findElement(By.name("campaigns[new_1][USD]")).getAttribute("placeholder"));
        String value = JavascriptExecutor.class.cast(webdriver).executeScript("return document.getElementsByName('campaigns[new_1][EUR]')[0].value;").toString();
        Assert.assertEquals("18.00", value);

        webdriver.findElement(By.name("save")).click();
        int sizeAfter = webdriver.findElements(By.cssSelector("tr.row")).size() - 3;
        Assert.assertEquals(sizeBefore, sizeAfter);
    }

    @After
    public void teardown() {
        webdriver.quit();
        webdriver = null;
    }


}

