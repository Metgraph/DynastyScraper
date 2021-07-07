package webscraper;

import okhttp3.WebSocket;

import java.util.ArrayList;

public class WebScraperTester {
    public static void main(String[] args) {
        testWebScraper();

    }


    public static void testWebScraper() {
//        try{
//
//        }catch (){
//
//        }
        WebScraper ws = new WebScraper("resources/chromedriver.exe");
        ArrayList<Dynasty> arr = ws.getDynasties("https://it.wikipedia.org/wiki/Imperatori_romani");

        try {
            test1(arr, ws);
            test2(arr, ws);
        } catch (Exception e) {
            System.out.println("Test fallito:\n" + e);
        } finally {
            ws.close();
        }

    }

    public static void test1(ArrayList<Dynasty> arr, WebScraper ws) {
        Member m = arr.get(0).getMembers().get(0);
        ws.addMemberInfo(m);
        System.out.println("\nStampa della lista");
        System.out.println(m.getIssue());
        System.out.println("Madre: " + m.getMother());
        System.out.println("Padre: " + m.getFather());
        System.out.println("Coniuge: " + m.getSpouses());
        System.out.println();

        Member m2 = arr.get(1).getMembers().get(0);
        ws.addMemberInfo(m2);
        System.out.println("\nStampa della lista");
        System.out.println(m2.getIssue());
        System.out.println("Madre: " + m2.getMother());
        System.out.println("Padre: " + m2.getFather());
        System.out.println("Coniuge: " + m2.getSpouses());
        System.out.println();
        System.out.println("fine");
    }

    public static void test2(ArrayList<Dynasty> arr, WebScraper ws) {
        for (Dynasty dynasty : arr) {
            System.out.println(dynasty.getName());
            ArrayList<Member> members = dynasty.getMembers();
            for (Member member : members) {
                ws.addMemberInfo(member);
                System.out.println("Imperatore: " + member);
                System.out.println("\nStampa della lista");
                System.out.println(member.getIssue());
                System.out.println("Madre: " + member.getMother());
                System.out.println("Padre: " + member.getFather());

                System.out.println("Coniuge: " + member.getSpouses());
                System.out.println("-------------------\n");
            }
        }
    }


}
