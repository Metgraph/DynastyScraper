package webscraper;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

public class WebScraper {
    private final WebDriver driver;

    public WebScraper() {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
        driver = new ChromeDriver();
    }

    public void getDinasties(String urlDinasty) {
        // Launch Website
        driver.navigate().to(urlDinasty);

        ArrayList<Dinasty> dinasties = new ArrayList<>();
        //Maximize the browser
        //driver.manage().window().maximize();
        List<WebElement> allDinasties = driver.findElements(By.tagName("h4"));
        for (WebElement el : allDinasties) {
            WebElement table = el.findElement(By.xpath("./following::table[@class='wikitable']"));
            String dinasty = el.getText();
            List<WebElement> listWb = table.findElements(By.tagName("tr"));
            ArrayList<Member> members = new ArrayList<>();
            for (int i = 0; i < listWb.size(); i++) {
                WebElement wb = listWb.get(i);
                List<WebElement> tempList = wb.findElements(By.tagName("td"));
                if (tempList.size()>1) {

                    WebElement temp = tempList.get(1);
                    String name = temp.getText();
                    if (!wb.findElements(By.tagName("a")).isEmpty()) {

                        //String url = temp.findElement(By.tagName("a")).getAttribute("href")
                    }
                    Member member = new Member(name, "nop", "0", "0");
                    members.add(member);
                }
            }
            dinasties.add(new Dinasty(dinasty, members));

        }
        for(Dinasty d: dinasties){
            System.out.println(d);
        }

    }


    public void close() {
        this.driver.close();
    }
}
