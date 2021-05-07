package webscraper;

import java.util.ArrayList;
import java.util.List;

public class Dinasty {
    private String name;
    private ArrayList<Member> members;

    public Dinasty(String name, ArrayList<Member> members) {
        this.name = name;
        this.members = members;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Member> getMembers() {
        return members;
    }

    public String toString(){
        StringBuilder print = new StringBuilder(name + "\n");
        for(Member member: members){
            print.append("\t").append(member.getName().replace("\n"," ")).append("\n");
        }
        return print.toString();
    }
}
