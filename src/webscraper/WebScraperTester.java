package webscraper;

import java.util.ArrayList;

public class WebScraperTester {
    public static void main(String[] args) {
        WebScraper ws = new WebScraper();
        ArrayList<Dinasty> arr = ws.getDinasties("https://it.wikipedia.org/wiki/Imperatori_romani");
//        for(Dinasty d: arr){
//            System.out.println(d);
//        }
        try {
            Member m = arr.get(0).getMembers().get(0);
            ws.getPersonInfo(m);
            System.out.println("\nStampa della lista");
            System.out.println(m.getIssue() + "\n");
            m = arr.get(1).getMembers().get(0);
            ws.getPersonInfo(m);
            System.out.println("\nStampa della lista");
            System.out.println(m.getIssue() + "\n");
            System.out.println("fine");
        }finally {
            ws.close();
        }

    }
}
