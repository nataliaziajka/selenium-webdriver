package pl.stqa.traning.selenium;


import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;


public class MyFirstTest extends TestBase {

    @Test

    public void myFirstTest() {

        driver.navigate().to("http://www.google.com");
        driver.findElement(By.name("q")).sendKeys("webdriver" + Keys.ENTER);
        wait.until(titleIs("webdriver - Szukaj w Google"));

    }
}