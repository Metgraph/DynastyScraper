package webscraper;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

//guide xpath https://www.lambdatest.com/blog/complete-guide-for-using-xpath-in-selenium-with-examples/#testid1.2
//browser for Selenium https://github.com/machinepublishers/jbrowserdriver
/**
 * This class provide methods to get information about emperors and their relatives
 */
public class WebScraper implements DynastiesScraper{
    private final WebDriver driver;

    /**
     * Constructor, initialize the web driver and open the browser
     */
    public WebScraper() {
        System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
        driver = new ChromeDriver();
    }


    /**
     * Search the dynasties in a wikipedia page, if nothing has been found will return empty list
     *
     * @param urlDynasty The page where search the emperors
     * @return A list of all founded emperors in the page
     */
    public ArrayList<Dynasty> getDynasties(String urlDynasty) throws IllegalArgumentException{
        //if it isn't a wikipedia url it will raies IllegalArgumentException
        if(!isWikipediaURL(urlDynasty)){
            throw new IllegalArgumentException("Invalid URL, it is not a wikipedia URL");
        }

        //opens url in the browser
        driver.navigate().to(urlDynasty);

        ArrayList<Dynasty> dynasties = new ArrayList<>();

        //take the references to the dynsasty name
        List<WebElement> allDynasties = driver.findElements(By.xpath("//tbody/tr/th[contains(text(),'Nome')]/ancestor::table[@class='wikitable']/preceding::*[@class=\"mw-headline\"][1]"));


        for (WebElement dynasty : allDynasties) {
            //takes first table that has as class "wikitable" after the reference of the WebElement
            WebElement table = dynasty.findElement(By.xpath("./following::table[@class='wikitable']"));

            //takes dynasty name
            String dynastyName = clearText(dynasty.getText());
            int counter = 0;
            for (Dynasty dynasty1 : dynasties) {
                if(dynasty1.getOriginalName().equals(dynastyName))
                    counter++;
            }
            //takes each line of table
            List<WebElement> listTr = table.findElements(By.tagName("tr"));
            ArrayList<Member> members = new ArrayList<>();

            for (WebElement wb : listTr) {
                List<WebElement> listTd = wb.findElements(By.tagName("td"));
                if (listTd.size() > 1) {

                    //takes name and url of emperor
                    WebElement memberNameWE = listTd.get(1);
                    String memberName = memberNameWE.getText();
                    String url = getUrlFromWE(memberNameWE);

                    //adds emperor in the list
                    members.add(new Member(memberName, url));
                }
            }

            //adds dynasty in the list
            dynasties.add(new Dynasty(dynastyName, members, counter));

        }

        return dynasties;
    }


    /**
     * Set the member close relatives
     *
     * @param personLookingFor the person on which to take the family
     */
    public void addMemberInfo(Member personLookingFor) throws NoSuchElementException, IllegalArgumentException{
        //if it isn't a wikipedia url it will raies IllegalArgumentException error
        if(!isWikipediaURL(personLookingFor.getUrl())){
            throw new IllegalArgumentException("Invalid URL, it is not a wikipedia URL");
        }

        //opens url in the browser
        driver.navigate().to(personLookingFor.getUrl());

        //goes to the right table of page
        WebElement synoptic = driver.findElement(By.className("sinottico"));
        personLookingFor.setBiography(getBio());

        //if there is a image of the person will takes is url, otherwise sets null
        try{
            WebElement image = synoptic.findElement(By.xpath("//div[@class='floatnone']/a/img"));
            personLookingFor.setImageURL(image.getAttribute("src"));
        }catch (NoSuchElementException noImage){
            personLookingFor.setImageURL(null);
        }

        //if the line "Figli" is present takes their names and urls, otherwise sets empty ArrayList
        try {
            WebElement descendants = synoptic.findElement(By.xpath("//tr/th[contains(text(),'Figli')]/following::td"));
            personLookingFor.setIssue(getPeopleArray(descendants, true));
        } catch (NoSuchElementException noDescendants) {
            personLookingFor.setIssue(new ArrayList<>());
        }

        //if the line "Madre" is present takes its name, otherwise sets null
        try {
            WebElement mother = synoptic.findElement(By.xpath("//tr/th[contains(text(),'Madre')]/following::td"));
            String motherName = clearText(mother.getText());
            String motherUrl = getUrlFromWE(mother);
            personLookingFor.setMother(new Member(motherName, motherUrl));
        } catch (NoSuchElementException noMother) {
            personLookingFor.setMother(null);
        }

        //if the line "Padre" is present takes its name, otherwise sets null
        try {
            WebElement father = synoptic.findElement(By.xpath("//tr/th[contains(text(),'Padre')]/following::td"));
            ArrayList<Member> fatherArray = getPeopleArray(father, false);
            personLookingFor.setFather(fatherArray.get(0));
        } catch (NoSuchElementException noFather) {
            personLookingFor.setFather(null);
        }

        //if the line "Coniuge" is present takes its name, otherwise sets null
        try {
            WebElement consort = synoptic.findElement(By.xpath("//tr/th[contains(text(),'Coniuge')]/following::td"));
            personLookingFor.setSpouses(getPeopleArray(consort, false));
        } catch (NoSuchElementException noConsort) {
            personLookingFor.setSpouses(null);
        }

        //if the line "Dinastia" is present takes its name, otherwise sets an empty string
        try {
            WebElement dynastyWE = synoptic.findElement(By.xpath("//tr/th[contains(text(),'Dinastia')]/following::td"));
            String dynastyName = clearText(dynastyWE.getText());
            personLookingFor.setDynastyName(dynastyName);
        } catch (NoSuchElementException noConsort) {
            personLookingFor.setDynastyName("");
        }

        //takes the short name
        try{
            WebElement shName = driver.findElement(By.xpath("//div[@id='mw-content-text']//p/b"));
            personLookingFor.setName(shName.getText());
        }catch (NoSuchElementException noShortName){
            //leave the old setted short name
        }
    }


    /**
     * Close the Browser, if not called the browser will be remain open after the end of the execution
     */
    public void close() {
        this.driver.close();
    }


    //returns an url if presents, otherwise returns an empty string
    private static String getUrlFromWE(WebElement WE) {
        if (!WE.findElements(By.tagName("a")).isEmpty()) {

            return WE.findElement(By.tagName("a")).getAttribute("href");
        } else {
            return "";
        }
    }


    //method to take more people from a WebElement
    //setAdopted = true if you want to assign to people found if their adopted or not (default value is false)
    private static ArrayList<Member> getPeopleArray(WebElement WE, boolean setAdopted) {
        //takes the text
        String text = WE.getText();

        //list that will contain all the people found
        ArrayList<Member> members = new ArrayList<>();

        boolean areAdopted = false;

        //divides text for each line
        for (String s : text.split("\n")) {
            boolean isAdopted = false;

            //if in the string there is contained "Adott" it means that people below this string are all adopted
            if (s.contains("Adott")) {
                areAdopted = true;
                continue;
            }

            //if in the string there is contained "adott" it means that this person is adopted
            if (s.contains("adott")) {
                isAdopted = true;
            }

            String url;

            //searches the tag a that contains the person's name, if it finds it the url will be extracted
            try {
                s = clearText(s);

                url = WE.findElement(By.xpath(".//a[text()='" + s + "']")).getAttribute("href");
            } catch (NoSuchElementException e) {
                url = "";
            }

            //adds person in the list
            members.add(new Member(s, url, (areAdopted || isAdopted) && setAdopted));

        }

        return members;
    }


    //cleans text from brackets and punctuation
    private static String clearText(String text) {
        //removes the round brackets and their contents
        text = text.replaceAll("\\(.*\\)", "");
        //removes the square brackets and their contents
        text = text.replaceAll("\\[.*]", "");
        //removes punctuation
        text = text.replaceAll("\\p{Punct}", "");

        return text.trim();
    }


    private String getBio() {
        List<WebElement> span = driver.findElements(By.xpath("//span[@class='mw-headline']"));
        List<WebElement> ll = span.get(0).findElements(By.xpath("./preceding::p"));
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

    //removes the notes from text
    private static String removeBracketsNum(WebElement el, String text) {
        List<WebElement> list = el.findElements(By.tagName("a"));
        for (WebElement we : list) {
            String weText = we.getText();
            if (Pattern.compile("\\[.*]").matcher(weText).find()) {

                ////if the character before the closing square brackets is a number so removes the brackets and
                // their contenents
                char c = weText.charAt(weText.indexOf(']') - 1);
                if (c >= '0' && c <= '9') {
                    text = text.replace(we.getAttribute("outerHTML"), "");
                }
            }
        }
        return text;
    }

    //check if the domain is it.wikipedia.org
    private static boolean isWikipediaURL(String url){
        try{
            URI uri = new URI(url);
            String domain = uri.getHost().toLowerCase();
            return domain.contains("it.wikipedia.org");

        }catch (URISyntaxException e){
            return false;
        }
    }

}
