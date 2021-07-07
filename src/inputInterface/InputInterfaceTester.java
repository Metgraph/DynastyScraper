package inputInterface;

import dataManagement.Storage;
import webscraper.WebScraper;

public class InputInterfaceTester {

    public static void main(String[] args){

        Storage store = new Storage("https://it.wikipedia.org/wiki/Imperatori_romani",new WebScraper("chromedriver.exe"));

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

        System.out.println("*" + inFrame.getDynastyName() + "*");

        store.close();
    }

}