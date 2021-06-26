package webscraper;
import webscraper.Member;
import webscraper.Dynasty;

import java.util.ArrayList;

public interface DynastiesScraper {
    ArrayList<Dynasty> getDynasties(String url);
    void addMemberInfo(Member emperor);
    void close();

}
