package webscraper;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

public class WebScraperExample {
    public static void main(String[] args) {

        // System Property for Chrome Driver
        System.setProperty("webdriver.chrome.driver", "D:\\eclipse\\esercitazione\\chromedriver.exe");

        // Instantiate a ChromeDriver class.
        WebDriver driver = new ChromeDriver();

        // Launch Website
        driver.navigate().to("https://it.wikipedia.org/wiki/Imperatori_romani");

        //Maximize the browser
        //driver.manage().window().maximize();
        List<WebElement> allDinasties = driver.findElements(By.tagName("h4"));
        List<WebElement> allTable = driver.findElements(By.className("wikitable"));
        int b = -1;
        for (WebElement table : allTable) {
//            if (b > 1) {
//                break;
//            }

            List<WebElement> insideTable = table.findElements(By.tagName("td"));
            WebElement el = insideTable.get(1);
            String name = el.getText();
            String url;

            try {
                WebElement temp = el.findElement(By.tagName("a"));
                url = temp.getAttribute("href");
                b++;
                System.out.println(allDinasties.get(b).getText());
                System.out.println(name + " " + url + "\n");
            } catch (NoSuchElementException e) {
                url = "Nessun link";
            }

        }

        //WebDriver Edriver = new ChromeDriver();
//        for (Empereor emp : empereors) {
//            getEmpereorInfo(emp, driver);
//        }

        driver.close();

    }

    public static void getEmpereorInfo(String name, String url, WebDriver driver) {
        driver.navigate().to(url);
        WebElement synoptic = driver.findElement(By.className("sinottico"));
        List<WebElement> trList = synoptic.findElements(By.tagName("tr"));
        System.out.println(name);
        System.out.println("lunghezza " + trList.size());
        for (WebElement tr : trList) {

            try {
                String index = tr.findElement(By.tagName("th")).getText();
                switch (index) {
                    case "Figli":
                        System.out.println("figli " + tr.findElement(By.tagName("td")).getText() + "\n");
                        break;

                    case "Coniuge":
                        System.out.println("coniuge " + tr.findElement(By.tagName("td")).getText() + "\n");
                        break;

                    case "Padre":
                        System.out.println("padre " + tr.findElement(By.tagName("td")).getText() + "\n");
                        break;

                    case "Madre":
                        System.out.println("madre " + tr.findElement(By.tagName("td")).getText() + "\n");
                        break;

                    default:
                        break;
                }

            } catch (NoSuchElementException e) {
                System.out.println("ERRORE");
                continue;
            }
        }

    }
}
