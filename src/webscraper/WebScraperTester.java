package webscraper;

public class WebScraperTester {
    public static void main(String[] args) {
        WebScraper wb = new WebScraper();
        wb.getDinasties("https://it.wikipedia.org/wiki/Imperatori_romani");
        wb.close();
    }
}
