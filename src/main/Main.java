package main;

import dataManagement.Storage;
import inputInterface.InputGUI;
import outpugui.EmperorGUI;
import webscraper.Member;
import webscraper.WebScraper;

import javax.swing.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        while(true) {
            WebScraper scraper;
            try{
                scraper = new WebScraper("resources/chromedriver.exe");
            }catch (IllegalStateException exception){
                JOptionPane.showMessageDialog(null, "Chrome driver not found");
                break;
            }
            Storage store = new Storage("https://it.wikipedia.org/wiki/Imperatori_romani", scraper);

            InputGUI inFrame = new InputGUI(store.getDynasties());

            while (inFrame.isFinished()) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


            ArrayList<Member> tree = store.getTree(inFrame.getDynastyName());

            store.close();

            EmperorGUI outFrame = new EmperorGUI(tree, inFrame.getDynastyName());

            while (outFrame.isFinished()) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
