package webscraper;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public interface DynastiesScraper {
    ArrayList<Dynasty> getDynasties(String url) throws IllegalArgumentException;
    void addMemberInfo(Member emperor) throws IllegalArgumentException;
    void close();

}
