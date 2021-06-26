package dataManagement;
import webscraper.Member;
import webscraper.Dynasty;

import java.util.ArrayList;

public interface DynastiesScraper {
    public ArrayList<Dynasty> getDynasties(String url);
    public void addMemberInfo(Member emperor);
    public void close();

}
