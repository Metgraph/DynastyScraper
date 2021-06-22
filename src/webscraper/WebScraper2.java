package webscraper;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


/**
 * This class provide methods to get information about emperors and their relatives
 */
public class WebScraper2 {
    private final WebDriver driver;

    /**
     * Constructor, initialize the web driver and open the browser
     */
    public WebScraper2() {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
        driver = new ChromeDriver();
    }

    /**
     * Search the empereors in a wikipedia page, if nothing has been found will return empty list
     *
     * @param urlPage The page where search the emperors
     * @return A list of all founded emperors in the page
     */
    public ArrayList<Member> getEmperors(String urlPage) {
        //apertura del browser al link urlDynasty
        driver.navigate().to(urlPage);

        //arraylist dove verranno salvate le dinastie della tabella
        ArrayList<Member> emperors = new ArrayList<>();

        //i nomi delle dinastie sono all'interno dei tag h4, perciò il programma cercherà per quelli
        List<WebElement> tables = driver.findElements(By.xpath("//tbody/tr/th[contains(text(),'Nome')]/ancestor::table"));

        System.out.println(tables.size());
        for (WebElement table : tables) {
            List<WebElement> listTr = table.findElements(By.tagName("tr"));
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

        //array dove verranno salvati gli imperatori della tabella


        return emperors;
    }

    /**
     * Group the emperors into their dynasties, for each emperor will be make a search on their page
     *
     * @param members The list of emperors
     * @return List of dinasties
     */
    public ArrayList<Dynasty> createDynastiesList(ArrayList<Member> members) {
        ArrayList<Dynasty> dynastiesList = new ArrayList<>();
        Dynasty currentDynasty = new Dynasty("");
        for (Member member : members) {
            this.addMemberInfo(member);

            if (!member.getDynastyName().equals("") && !member.getDynastyName().equals("nessuna")) {
                if (member.isInTheDynasty(currentDynasty.getUrl())) {
                    currentDynasty.addMember(member);

                } else {
                    currentDynasty = new Dynasty(member.getDynastyName(), member.getDynastyUrl());
                    currentDynasty.addMember(member);
                    dynastiesList.add(currentDynasty);
                }

            } else {
                currentDynasty = new Dynasty(member.getName(), member.getDynastyUrl());
                currentDynasty.addMember(member);
                dynastiesList.add(currentDynasty);
            }
            System.out.println(member.getName() + " --- " + member.getDynastyName() + " --- " + member.getDynastyUrl());

        }
        return dynastiesList;
    }

    /**
     * Set the member close relatives
     *
     * @param personLookingFor the person on which to take the family
     */
    public void addMemberInfo(Member personLookingFor) {
        //apro l'url sul browser
        driver.navigate().to(personLookingFor.getUrl());

        personLookingFor.setBiography(getBio());
        //vado alla tabella sinistra della pagina
        WebElement synoptic = driver.findElement(By.className("sinottico"));

        //se esiste un immagine dell'imperatore preleva il suo link e lo salva
        try{
            WebElement image = synoptic.findElement(By.xpath("//div[@class='floatnone']/a/img"));
            personLookingFor.setImageURL(image.getAttribute("src"));
        }catch (NoSuchElementException noImage){
            personLookingFor.setImageURL(null);
        }

        //se esiste la riga "Figli" ne preleva i nomi e eventuali url, altrimenti imposta un'array vuota
        try {
            WebElement descendants = synoptic.findElement(By.xpath("//tr/th[contains(text(),'Figli')]/following::td"));
            personLookingFor.setIssue(getPeopleArray(descendants, true));
        } catch (NoSuchElementException noDescendants) {
            personLookingFor.setIssue(new ArrayList<>());
        }

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
            personLookingFor.setFather(fatherArray.get(0));
        } catch (NoSuchElementException noFather) {
            personLookingFor.setFather(null);
        }

        //se esiste la riga "Coniuge" ne preleva il nome e eventuali url, altrimenti imposto null
        try {
            WebElement consort = synoptic.findElement(By.xpath("//tr/th[contains(text(),'Coniuge')]/following::td"));
            personLookingFor.setSpouses(getPeopleArray(consort, false));
        } catch (NoSuchElementException noConsort) {
            personLookingFor.setSpouses(null);
        }

        //se esiste la riga "Dinastia" ne preleva il nome, altrimenti imposta una stringa vuota
        try {
            WebElement dynastyWE = synoptic.findElement(By.xpath("//tr/th[contains(text(),'Dinastia')]/following::td"));
            String dynastyName = clearText(dynastyWE.getText());
            String dynastyUrl = getUrlFromWE(dynastyWE);
            personLookingFor.setDynastyName(dynastyName);
            personLookingFor.setDynastyUrl(dynastyUrl);
        } catch (NoSuchElementException noConsort) {
            personLookingFor.setDynastyName("");
        }
    }

    /**
     * Fusion of getEmperors and createDynastiesList
     *
     * @param urlPage The page where search the emperors
     * @return List of dynasties each one containing the its emperors
     * @see #getEmperors
     * @see #createDynastiesList
     */
    public ArrayList<Dynasty> getDynasties(String urlPage) {
        ArrayList<Member> emperors = getEmperors(urlPage);
        return createDynastiesList(emperors);
    }

    /**
     * Close the Browser, if not called the browser will be remain open after the end of the execution
     */
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

    private String getBio() {
        List<WebElement> ll = driver.findElements(By.xpath("//div[@class='toc']/preceding-sibling::p"));
        StringBuilder adjustedBioBuilder = new StringBuilder();
        for (WebElement webElement : ll) {
            String partOfBio = webElement.getAttribute("innerHTML");
            partOfBio = removeBracketsNum(webElement, partOfBio);
            adjustedBioBuilder.append(partOfBio);
        }
        String adjustedBio = adjustedBioBuilder.toString();
        adjustedBio = adjustedBio.replace('"', '\'');
        return adjustedBio;
    }

    private static String removeBracketsNum(WebElement el, String text) {
        List<WebElement> list = el.findElements(By.tagName("a"));
        for (WebElement we : list) {
            String weText = we.getText();
            if (Pattern.compile("\\[.*]").matcher(weText).find()) {

                //se il carattere prima della parentesi quadra chiusa e' un numero allora rimuovi le parentesi,
                // il contenuto e i tag che lo racchiudono
                char c = weText.charAt(weText.indexOf(']') - 1);
                if (c >= '0' && c <= '9') {
                    text = text.replace(we.getAttribute("outerHTML"), "");
                }
            }
        }
        return text;
    }

}
