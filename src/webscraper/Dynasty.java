package webscraper;

import java.util.ArrayList;
import java.util.List;

public class Dynasty {
    private String name;
    private String url;
    private ArrayList<Member> members;

    //vecchio
    public Dynasty(String name, ArrayList<Member> members) {
        this.name = name;
        this.members = members;
    }

    public Dynasty(String name) {
        this.name = name;
        this.url = "";
        this.members = new ArrayList<>();
    }

    public Dynasty(String name, String url){
        this.name = name;
        this.members = new ArrayList<>();
        this.url = url;
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

    public void addMember(Member member){
        this.members.add(member);
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
