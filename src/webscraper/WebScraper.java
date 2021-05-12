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

        //arraylist dove verranno salvate le dinastie della tabella
        ArrayList<Dinasty> dynasties = new ArrayList<>();

        //i nomi delle dinastie sono all'interno dei tag h4, perciò il programma cercherà per quelli
        List<WebElement> allDynasties = driver.findElements(By.tagName("h4"));


        for (WebElement dynasty : allDynasties) {
            //viene prelevato la prima tabella che ha come classe 'wikitable' dopo il tag h4 salvato in dynasty
            WebElement table = dynasty.findElement(By.xpath("./following::table[@class='wikitable']"));

            //prendo il nome della dinastia
            String dynastyName = dynasty.getText();
            //prelevo ogni riga della tabella
            List<WebElement> listTr = table.findElements(By.tagName("tr"));
            //array dove verranno salvati gli imperatori della tabella
            ArrayList<Member> members = new ArrayList<>();

            for (WebElement wb : listTr) {
                List<WebElement> listTd = wb.findElements(By.tagName("td"));
                if (listTd.size() > 1) {

                    //prendo il nome e l'url dell'imperatore
                    WebElement memberNameWE = listTd.get(1);
                    String memberName = memberNameWE.getText();
                    String url = getUrlFromWE(memberNameWE);

                    //aggiungo l'imperatore nell'array
                    members.add(new Member(memberName, url));
                }
            }

            //aggiungo la dinastia nell'array
            dynasties.add(new Dinasty(dynastyName, members));

        }

        return dynasties;

    }

    public void addMemberInfo(Member personLookingFor) {
        //apro l'url sul browser
        driver.navigate().to(personLookingFor.getUrl());

        //vado alla tabella sinistra della pagina
        WebElement synoptic = driver.findElement(By.className("sinottico"));

        //se esiste la riga "Figli" ne preleva i nomi e eventuali url, altrimenti imposta un'array vuota
        try {
            WebElement descendants = synoptic.findElement(By.xpath("//tr/th[contains(text(),'Figli')]/following::td"));
            personLookingFor.setIssue(getPeopleArray(descendants, true));
        } catch (NoSuchElementException noDescendants) {
            personLookingFor.setIssue(new ArrayList<>());
        }

        //se esiste la riga "Madre" ne preleva il nome e eventuali url, altrimenti imposto null
        try {
            WebElement mother = synoptic.findElement(By.xpath("//tr/th[contains(text(),'Madre')]/following::td"));
            String motherName = clearText(mother.getText());
            String motherUrl = getUrlFromWE(mother);
            personLookingFor.setMother(new Member(motherName, motherUrl));
        } catch (NoSuchElementException noMother) {
            personLookingFor.setMother(null);
        }

        //se esiste la riga "Padre" ne preleva il nome e eventuali url, altrimenti imposto null
        //se trova più di un padre metterà il secondo come adottivo
        try {
            WebElement father = synoptic.findElement(By.xpath("//tr/th[contains(text(),'Padre')]/following::td"));
            ArrayList<Member> fatherArray = getPeopleArray(father, false);
            if (fatherArray.size() > 1) {
                personLookingFor.setAdoptiveFather(fatherArray.get(1));
            }
            personLookingFor.setFather(fatherArray.get(0));
        } catch (NoSuchElementException noFather) {
            personLookingFor.setFather(null);
        }

        //se esiste la riga "Coniuge" ne preleva il nome e eventuali url, altrimenti imposto null
        try {
            WebElement consort = synoptic.findElement(By.xpath("//tr/th[contains(text(),'Coniuge')]/following::td"));
//            String consortName = clearText(consort.getText());
//            String consortUrl = getUrlFromWE(consort);
//            personLookingFor.setSpouses(new Member(consortName, consortUrl));
            personLookingFor.setSpouses(getPeopleArray(consort, false));
        } catch (NoSuchElementException noConsort) {
            personLookingFor.setSpouses(null);
        }

        //se esiste la riga "Dinastia" ne preleva il nome, altrimenti imposta una stringa vuota
        try {
            WebElement dynastyWE = synoptic.findElement(By.xpath("//tr/th[contains(text(),'Dinastia')]/following::td"));
            String dynastyName = clearText(dynastyWE.getText());
            personLookingFor.setDynastyName(dynastyName);
        } catch (NoSuchElementException noConsort) {
            personLookingFor.setDynastyName("");
        }
    }

    //chiude il browser, se non viene chiamato il browser rimarrà aperto anche dopo la fine del programma
    public void close() {
        this.driver.close();
    }

    //ritorna l'url se presente, altrimenti ritorna una stringa vuota
    private static String getUrlFromWE(WebElement WE) {
        if (!WE.findElements(By.tagName("a")).isEmpty()) {

            return WE.findElement(By.tagName("a")).getAttribute("href");
        } else {
            return "";
        }
    }

    //metodo per prelevare più persone da un webElement,
    //setAdopted = true se si vuole assegnare alle persone trovate se sono adottate o meno (da usare solo coi figli)
    private static ArrayList<Member> getPeopleArray(WebElement WE, boolean setAdopted) {
        //prendo l'intero testo della riga
        String text = WE.getText();

        //array che conterrà tutte le persone trovate
        ArrayList<Member> members = new ArrayList<>();

        //variabile che servirà per riconoscere se i figli sono stati adottati o meno
        boolean areAdopted = false;

        //divido il testo per ogni a capo
        for (String s : text.split("\n")) {
            //variabile che servirà per riconoscere se i figli è stato adottato o meno
            boolean isAdopted = false;

            //se è presente la parola Adott allora tutte le persone sotto saranno adottate
            if (s.contains("Adott")) {
                areAdopted = true;
                continue;
            }

            //se è presente la parola adott allora questa persona è stata adottata
            if (s.contains("adott")) {
                isAdopted = true;
            }

            String url;

            //cerca il tag a contenente il nome della persona, se lo trova estrae l'url
            try {
                s = clearText(s);

                url = WE.findElement(By.xpath(".//a[text()='" + s + "']")).getAttribute("href");
            } catch (NoSuchElementException e) {
                url = "";
            }

            //aggiungo la persona alla lista
            members.add(new Member(s, url, (areAdopted || isAdopted) && setAdopted));

        }

        return members;
    }


    //pulisce il testo dalla punteggiatura e dalle parentesi e il contenuto al loro interno
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
