package webscraper;

import java.util.List;

public class Dinasty {
    private String name;
    private Member[] members;

    public Dinasty(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Member[] getMembers() {
        return members;
    }
}
