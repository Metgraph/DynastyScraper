package webscraper;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class WebScraper {
    private WebDriver driver;

    public WebScraper() {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
        driver = new ChromeDriver();
    }

    public void getDinasties(String urlDinasty){
        // Launch Website
        driver.navigate().to(urlDinasty);

        //Maximize the browser
        //driver.manage().window().maximize();
        List<WebElement> allDinasties = driver.findElements(By.tagName("h4"));
        for (WebElement el: allDinasties) {
            System.out.println(el.getText());
        }
        System.out.println();
        List<WebElement> allTable = driver.findElements(By.className("wikitable"));
        for (WebElement table : allTable) {

            List<WebElement> insideTable = table.findElements(By.tagName("td"));
            WebElement el = insideTable.get(1);
            String name = el.getText();
            String url;

            try {
                WebElement temp = el.findElement(By.tagName("a"));
                url = temp.getAttribute("href");
                System.out.println(name + " " + url + "\n");
            } catch (NoSuchElementException e) {
                url = "Nessun link";
            }

        }

        driver.close();

    }
}
