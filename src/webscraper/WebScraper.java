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
    public ArrayList<Dinasty> getDynasties(String urlDynasty) {
        //apertura del browser al link urlDynasty
        driver.navigate().to(urlDynasty);

        //arraylist contente le dinastie
        ArrayList<Dinasty> dynasties = new ArrayList<>();

        //i nomi delle dinastie sono all'interno dei tag h4, perciò il programma cerchera' per quelli
        List<WebElement> allDynasties = driver.findElements(By.tagName("h4"));


        for (WebElement dynasty : allDynasties) {
            //viene prelevato la prima tabella che ha come classe 'wikitable' dopo il tag h4 salvato in dynasty
            WebElement table = dynasty.findElement(By.xpath("./following::table[@class='wikitable']"));

            String dynastyName = dynasty.getText();
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
            dynasties.add(new Dinasty(dynastyName, members));

        }
        return dynasties;

    }

    public void addMemberInfo(Member personLookingFor) {
        driver.navigate().to(personLookingFor.getUrl());
        WebElement synoptic = driver.findElement(By.className("sinottico"));

        try {
            WebElement descendants = synoptic.findElement(By.xpath("//tr/th[contains(text(),'Figli')]/following::td"));
            personLookingFor.setIssue(getPeopleArray(descendants));
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
            ArrayList<Member> fatherArray = getPeopleArray(father);
            if(fatherArray.size() > 1){
                personLookingFor.setAdoptiveFather(fatherArray.get(1));
            }
            personLookingFor.setFather(fatherArray.get(0));
        }catch (NoSuchElementException noFather){
            personLookingFor.setFather(null);
        }

        try {
            WebElement consort = synoptic.findElement(By.xpath("//tr/th[contains(text(),'Coniuge')]/following::td"));
            String consortName = clearText(consort.getText());
            String consortUrl = getUrlFromWE(consort);
            personLookingFor.setSpouse(new Member(consortName, consortUrl));
        }catch (NoSuchElementException noConsort){
            personLookingFor.setSpouse(null);
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

    private static ArrayList<Member> getPeopleArray(WebElement WE) {
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

                url = WE.findElement(By.xpath(".//a[text()='" + s + "']")).getAttribute("href");
            } catch (NoSuchElementException e) {
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
