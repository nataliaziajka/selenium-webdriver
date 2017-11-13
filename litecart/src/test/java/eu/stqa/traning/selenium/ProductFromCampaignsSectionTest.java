package eu.stqa.traning.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

/**
 * Created by Natalia on 2017-11-13.
 */
public class ProductFromCampaignsSectionTest {

    private WebDriver webdriver;


    @Before
    public void setup() {
        webdriver = new ChromeDriver();
        webdriver.get("http://localhost:8012/litecart/en/");
        webdriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
}

    @Test
    public void checkArticleFromCampaignsSection() {

        WebElement yellow_duck = webdriver.findElement(By.xpath(".//*[@id='box-campaigns']/div/ul/li/a[1]"));
        WebElement firstPriceOnMainPage = yellow_duck.findElement(By.cssSelector("s.regular-price"));
        String actualStyleForFirstPriceOnMainPage = firstPriceOnMainPage.getCssValue("color");
        String actualStyle2ForFirstPriceOnMainPage = firstPriceOnMainPage.getCssValue("text-decoration");

        WebElement secondPriceOnMainPage = yellow_duck.findElement(By.cssSelector("strong.campaign-price"));

        String actualStyleForSecondPriceOnMainPage = secondPriceOnMainPage.getCssValue("color");
        String actualStyle2ForSecondPriceOnMainPage = secondPriceOnMainPage.getCssValue("font-weight");

        Assert.assertEquals("$20", firstPriceOnMainPage.getText());
        Assert.assertEquals("rgba(119, 119, 119, 1)", actualStyleForFirstPriceOnMainPage);
        Assert.assertEquals("line-through solid rgb(119, 119, 119)", actualStyle2ForFirstPriceOnMainPage);


        Assert.assertEquals("rgba(204, 0, 0, 1)", actualStyleForSecondPriceOnMainPage);
        Assert.assertEquals("700", actualStyle2ForSecondPriceOnMainPage);

        Assert.assertEquals("$18", secondPriceOnMainPage.getText());
        yellow_duck.click();

        Assert.assertEquals("Yellow Duck", webdriver.findElement(By.cssSelector("h1.title")).getText());
        WebElement firstPriceOnArticlePage = webdriver.findElement(By.cssSelector("s.regular-price"));
        WebElement secondPriceOnArticlePage = webdriver.findElement(By.cssSelector("strong.campaign-price"));
        String actualStyleForFirstPriceOnArticlePage = firstPriceOnArticlePage.getCssValue("color");
        String actualStyle2ForFirstPriceOnArticlePage = firstPriceOnArticlePage.getCssValue("text-decoration");

        String actualStyleForSecondPriceOnArticlePage = secondPriceOnArticlePage.getCssValue("color");
        String actualStyle2ForSecondPriceOnArticlePage = secondPriceOnArticlePage.getCssValue("font-weight");

        Assert.assertEquals("rgba(102, 102, 102, 1)", actualStyleForFirstPriceOnArticlePage);
        Assert.assertEquals("line-through solid rgb(102, 102, 102)", actualStyle2ForFirstPriceOnArticlePage);

        Assert.assertEquals("rgba(204, 0, 0, 1)", actualStyleForSecondPriceOnArticlePage);
        Assert.assertEquals("700", actualStyle2ForSecondPriceOnArticlePage);

        Assert.assertEquals("$20", firstPriceOnArticlePage.getText());
        Assert.assertEquals("$18", secondPriceOnArticlePage.getText());


    }
    @After
    public void teardown (){
        webdriver.quit();
        webdriver = null;
    }
}
