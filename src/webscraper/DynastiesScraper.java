package webscraper;

import org.openqa.selenium.NoSuchElementException;

import java.util.ArrayList;

public interface DynastiesScraper {
    ArrayList<Dynasty> getDynasties(String url);
    void addMemberInfo(Member emperor);
    void close();

}
