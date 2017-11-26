package eu.stqa.traning.selenium;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

/**
 * Created by Natalia on 2017-11-21.
 */
public class AddProductToBasket {
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
    public void addProductToTheBasket() throws InterruptedException {

        int counter = 0;
        for(int i=0;i<3; i++) {

            WebElement duck1 = webdriver.findElement(By.xpath(".//*[@id='box-most-popular']/div/ul/li[1]/a[1]"));
            duck1.click();

             try{
                new Select(webdriver.findElement(By.cssSelector(".options>select"))).selectByValue("Small");

            }catch(Exception a){}

            webdriver.findElement(By.cssSelector(".quantity>button")).click();
            counter++;

            WebElement element = webdriver.findElement(By.cssSelector("#cart .quantity"));
            boolean result = new WebDriverWait(webdriver, 10).until(
                    ExpectedConditions.textToBePresentInElement(element, String.valueOf(counter)));

            assert(result == true);
            webdriver.navigate().back();

        }

        WebElement checkOutLink = webdriver.findElement(By.xpath(".//*[@id='cart']/a[3]"));
        checkOutLink.click();

        while(webdriver.findElements( By.id(".//*[@id='checkout-cart-wrapper']/p[1]/em") ).size() != 0){
            waitForJSandJQueryToLoad();
            webdriver.findElement(By.name("remove_cart_item")).click();

        }
    }
    public boolean waitForJSandJQueryToLoad() {

        WebDriverWait wait = new WebDriverWait(webdriver, 30);

        // wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                try {
                    return ((Long)((JavascriptExecutor)webdriver).executeScript("return jQuery.active") == 0);
                }
                catch (Exception e) {
                    // no jQuery present
                    return true;
                }
            }
        };

        // wait for Javascript to load
        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor)webdriver).executeScript("return document.readyState")
                        .toString().equals("complete");
            }
        };

        return wait.until(jQueryLoad) && wait.until(jsLoad);
    }



        @After
        public void teardown () {
            webdriver.quit();
            webdriver = null;
        }
    }
