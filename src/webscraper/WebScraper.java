package webscraper;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

//guida xpath https://www.lambdatest.com/blog/complete-guide-for-using-xpath-in-selenium-with-examples/#testid1.2
//classe Optional
//browser per Selenium https://github.com/machinepublishers/jbrowserdriver

public class WebScraper {
    private final WebDriver driver;

    public WebScraper() {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
        driver = new ChromeDriver();
    }

    public ArrayList<Dinasty> getDinasties(String urlDinasty) {
        //apertura del browser al link urlDinasty
        driver.navigate().to(urlDinasty);

        //arraylist contente le dinastie
        ArrayList<Dinasty> dinasties = new ArrayList<>();

        //i nomi delle dinastie sono all'interno dei tag h4, percio' il programma cerchera' per quelli
        List<WebElement> allDinasties = driver.findElements(By.tagName("h4"));


        for (WebElement dinasty : allDinasties) {
            //viene prelevato la prima tabella che ha come classe 'wikitable' dopo il tag h4 salvato in dinasty
            WebElement table = dinasty.findElement(By.xpath("./following::table[@class='wikitable']"));

            String dinastyName = dinasty.getText();
            List<WebElement> listTr = table.findElements(By.tagName("tr"));
            ArrayList<Member> members = new ArrayList<>();

            for (WebElement wb : listTr) {
                List<WebElement> listTd = wb.findElements(By.tagName("td"));
                if (listTd.size() > 1) {

                    WebElement memberNameWE = listTd.get(1);
                    String memberName = memberNameWE.getText();
                    String url = getUrlFromWE(memberNameWE);
                    Member member = new Member(memberName, url);
                    members.add(member);
                }
            }
            dinasties.add(new Dinasty(dinastyName, members));

        }
        return dinasties;

    }

    public void addMemberInfo(Member personLookingFor) {
        driver.navigate().to(personLookingFor.getUrl());
        WebElement synoptic = driver.findElement(By.className("sinottico"));

        try {
            WebElement descendants = synoptic.findElement(By.xpath("//tr/th[contains(text(),'Figli')]/following::td"));
            personLookingFor.setIssue(getDescendantsArray(descendants));
        }catch (NoSuchElementException noDescendants){
            personLookingFor.setIssue(new ArrayList<>());
        }

        try {
            WebElement mother = synoptic.findElement(By.xpath("//tr/th[contains(text(),'Madre')]/following::td"));
            String motherName = clearText(mother.getText());
            String motherUrl = getUrlFromWE(mother);
            personLookingFor.setMother(new Member(motherName, motherUrl));
        }catch (NoSuchElementException noMother){
            personLookingFor.setMother(null);
        }

        try {
            WebElement father = synoptic.findElement(By.xpath("//tr/th[contains(text(),'Padre')]/following::td"));
            String fatherName = clearText(father.getText());
            String fatherUrl = getUrlFromWE(father);
            personLookingFor.setFather(new Member(fatherName, fatherUrl));
        }catch (NoSuchElementException noFather){
            personLookingFor.setFather(null);
        }
    }

    public void close() {
        this.driver.close();
    }

    private static String getUrlFromWE(WebElement WE) {
        if (!WE.findElements(By.tagName("a")).isEmpty()) {

            return WE.findElement(By.tagName("a")).getAttribute("href");
        } else {
            return "";
        }
    }

    private static ArrayList<Member> getDescendantsArray(WebElement WE) {
        String prova = WE.getText();
        ArrayList<Member> m = new ArrayList<>();
        boolean areAdopted = false;
        for (String s : prova.split("\n")) {
            boolean isAdopted = false;

            if (s.contains("Adott")) {
                areAdopted = true;
                continue;
            }

            if (s.contains("adott")) {
                isAdopted = true;
            }

            String url;
            try {
                s = clearText(s);
                System.out.println(s);

                url = WE.findElement(By.xpath(".//a[text()='" + s + "']")).getAttribute("href");
            } catch (NoSuchElementException e) {
                System.out.println("Nessun link trovato");
                url = "no url";
            }

            m.add(new Member(s, url, areAdopted || isAdopted));

        }

        return m;
    }

    private static String clearText(String text) {
        //rimozione delle parentesi tonde e del loro contenuto
        text = text.replaceAll("\\(.*\\)", "");

        //rimozione delle parentesi quadre e del loro contenuto
        text = text.replaceAll("\\[.*]", "");

        //rimozione della punteggiatura
        text = text.replaceAll("\\p{Punct}", "");

        return text.trim();
    }
}
