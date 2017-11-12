package eu.stqa.traning.selenium;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Natalia on 2017-11-11.
 */
public class StickersTest {
    private WebDriver webdriver;
    private WebDriverWait wait;

    @Before
    public void setup() {
        webdriver = new ChromeDriver();
        webdriver.get("http://localhost:8012/litecart/en/");
        wait = new WebDriverWait(webdriver, 10);
    }

    @Test
    public void checkNumberOfStickers() throws InterruptedException {

        List<WebElement> setOfArticles = webdriver.findElements(By.cssSelector("ul.listing-wrapper.products"));
        for (int i = 0; i < setOfArticles.size(); i++) {
            List<WebElement> articlesListOnSet = setOfArticles.get(i).findElements(By.cssSelector("li"));
            for (int j = 0; j < articlesListOnSet.size(); j++) {
                WebElement article = articlesListOnSet.get(j);
                int stickersNumberInArticle = article.findElements(By.cssSelector("div.image-wrapper > div.sticker")).size();
                Assert.assertTrue(stickersNumberInArticle == 1);
            }}}
                @After
                public void teardown (){
                    webdriver.quit();
                    webdriver = null;
                }
            }

