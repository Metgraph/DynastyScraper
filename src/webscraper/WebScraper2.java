package webscraper;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

public class WebScraper2 {
    private final WebDriver driver;

    public WebScraper2() {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
        driver = new ChromeDriver();
    }

    public ArrayList<Member> getEmperors(String urlPage) {
        //apertura del browser al link urlDynasty
        driver.navigate().to(urlPage);

        //arraylist dove verranno salvate le dinastie della tabella
        ArrayList<Member> emperors = new ArrayList<>();

        //i nomi delle dinastie sono all'interno dei tag h4, perciò il programma cercherà per quelli
        List<WebElement> allDynasties = driver.findElements(By.tagName("h4"));

        for (WebElement dynasty : allDynasties) {
            //viene prelevato la prima tabella che ha come classe 'wikitable' dopo il tag h4 salvato in dynasty
            WebElement table = dynasty.findElement(By.xpath("./following::table[@class='wikitable']"));

            //prelevo ogni riga della tabella
            List<WebElement> listTr = table.findElements(By.tagName("tr"));
            //array dove verranno salvati gli imperatori della tabella

            for (WebElement wb : listTr) {
                List<WebElement> listTd = wb.findElements(By.tagName("td"));
                if (listTd.size() > 1) {

                    //prendo il nome e l'url dell'imperatore
                    WebElement memberNameWE = listTd.get(1);
                    String memberName = memberNameWE.getText();
                    String url = getUrlFromWE(memberNameWE);

                    //aggiungo l'imperatore nell'array
                    emperors.add(new Member(memberName, url));
                }
            }

        }
        return emperors;
    }

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
