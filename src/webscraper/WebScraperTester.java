package webscraper;

import okhttp3.WebSocket;
import org.openqa.selenium.WebDriverException;

import java.util.ArrayList;

public class WebScraperTester {
    public static void main(String[] args) {
        testWebScraper();

    }


    public static void testWebScraper() {
        WebScraper ws = new WebScraper("resources/chromedriver.exe");

        testURL(ws);

        ArrayList<Dynasty> arr = ws.getDynasties("https://it.wikipedia.org/wiki/Imperatori_romani");

        testPrelevazioneDati(arr, ws);

        ws.close();

    }


    private static void testURL(WebScraper ws) {
        try {
            ArrayList<Dynasty> arr = ws.getDynasties("https://it.wikipedia.net/wiki/Imperatori_romani");
            System.out.println("Test errore URL fallito");
        } catch (IllegalArgumentException e) {
            System.out.println("Test errore URL passato");
        } catch (Exception e) {
            System.out.println("Test errore URL fallito:\n" + e);
        }
    }

    public static void testPrelevazioneDati(ArrayList<Dynasty> arr, WebScraper ws) {
        try {
            System.out.println("Stampa della famiglia prevista");
            Member m = arr.get(0).getMembers().get(0);
            ws.addMemberInfo(m);
            System.out.println("\nStampa della lista");
            System.out.println(m.getIssue());
            System.out.println("Madre: " + m.getMother());
            System.out.println("Padre: " + m.getFather());
            System.out.println("Coniuge: " + m.getSpouses());
            System.out.println();
        } catch (Exception e) {
            System.out.println("Test prelevazione fallito:\n" + e);
        }
        try {

            System.out.println("Stampa della famiglia non prevista, dovrebbe tornare errore");
            Member m2 = new Member("paolo", "nrlikwsaef");
            ws.addMemberInfo(m2);
            System.out.println("\nStampa della lista");
            System.out.println(m2.getIssue());
            System.out.println("Madre: " + m2.getMother());
            System.out.println("Padre: " + m2.getFather());
            System.out.println("Coniuge: " + m2.getSpouses());
            System.out.println();
            System.out.println("Test prelevazione fallito, il programma avrebbe dovuto generare errore");
        } catch (IllegalArgumentException e) {
            System.out.println("Test prelevazione eseguito con successo");
        } catch (Exception e) {
            System.out.println("Test prelevazione fallito:\n" + e);
        }
    }

}
