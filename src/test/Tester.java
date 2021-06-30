package test;

import dataManagement.Storage;
import inputInterface.InputGUI;
import outpugui.EmperorGUI;
import webscraper.Member;
import webscraper.WebScraper;
import java.util.ArrayList;

public class Tester {

    public static void main(String[] args) {
        Storage store = new Storage("https://it.wikipedia.org/wiki/Imperatori_romani",new WebScraper());

        InputGUI inFrame = new InputGUI(store.getDynasties());

        while(true) {
            if (inFrame.isFinished()) {
                try {
                    Thread.sleep(20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }else{
                break;
            }
        }


        ArrayList<Member> tree = store.getTree(inFrame.getDynastyName());

        store.close();

        new EmperorGUI(tree, inFrame.getDynastyName());

    }

}
