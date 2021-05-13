package webscraper;

import java.util.ArrayList;
import java.util.List;

public class Dynasty {
    private String name;
    private ArrayList<Member> members;

    public Dynasty(String name, ArrayList<Member> members) {
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
            //print.append("\t").append(member.getName().replace("\n"," ")).append(" ").append(member.getUrl()).append("\n");
            print.append(member.toString()).append("\n");
        }
        return print.toString();
    }
}
