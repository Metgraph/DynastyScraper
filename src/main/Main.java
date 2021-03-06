package main;

import dataManagement.Storage;
import inputInterface.InputGUI;
import outpugui.EmperorGUI;
import webscraper.Member;
import webscraper.WebScraper;
import org.openqa.selenium.WebDriverException;

import javax.swing.*;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        String path = getPathDriver(args);

        while (true) {
            WebScraper scraper;
            try {
                scraper = new WebScraper(path);
            } catch (IllegalStateException | WebDriverException exception) {
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

    private static String getPathDriver(String[] args) {
        //find the parameter l and his value
        boolean findLArg = false;
        for (String arg : args) {
            if (findLArg) {
                return arg;
            } else {
                findLArg = arg.equals("-l");
            }
        }

        //if value was not found it will set the default path
        return "resources/chromedriver.exe";
    }

}