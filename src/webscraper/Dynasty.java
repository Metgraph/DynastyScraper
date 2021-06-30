package webscraper;

import java.util.ArrayList;
import java.util.List;

/**
 * This class provide a data structure where save a dynasty
 */
public class Dynasty {
    private String name;
    private String url;
    private ArrayList<Member> members;
    private int counter;

    /**
     * Create an object whit the given name and the list of members
     *
     * @param name    The dynasty name
     * @param members The dynasty members
     */
    public Dynasty(String name, ArrayList<Member> members) {
        this.name = name;
        this.url = "";
        this.members = members;
        this.counter = 0;
    }

    public Dynasty(String name, ArrayList<Member> members, int counter) {
        this.name = name;
        this.url = "";
        this.members = members;
        this.counter = counter;
    }

    /**
     * Create an object whit the given name and set a empty url
     *
     * @param name The dynasty name
     */
    public Dynasty(String name) {
        this.name = name;
        this.url = "";
        this.members = new ArrayList<>();
        this.counter = 0;
    }

    /**
     * Create an object whit the given name and given url
     *
     * @param name The dynasty name
     * @param url  The Wikipedia page url
     */
    public Dynasty(String name, String url) {
        this.name = name;
        this.members = new ArrayList<>();
        this.url = url;
        this.counter = 0;
    }

    /**
     * Return the name of dynasty
     *
     * @return The dynasty name
     */
    public String getName() {
        if (counter == 0)
            return name;
        else
            return name+"("+counter+")";
    }

    /**
     * Return the original name of dynasty without brackets
     *
     * @return The dynasty name
     */
    public String getOriginalName() {
        return name;
    }

    /**
     * Return the people in the dynasty
     *
     * @return A list of the dynasty members
     */
    public ArrayList<Member> getMembers() {
        return members;
    }

    public String toString() {
        StringBuilder print = new StringBuilder(name + "\n");
        for (Member member : members) {
            //print.append("\t").append(member.getName().replace("\n"," ")).append(" ").append(member.getUrl()).append("\n");
            print.append(member.toString()).append("\n");
        }
        return print.toString();
    }

    /**
     * Add a member in the dynasty
     *
     * @param member The member to add in the dynasty
     */
    public void addMember(Member member) {
        this.members.add(member);
    }

    /**
     * Return the Wikipedia page of the dynasty
     *
     * @return The Wikipedia url of the dynasty
     */
    public String getUrl() {
        return url;
    }

    /**
     * Update the Wikipedia url of the dynasty
     *
     * @param url The Wikipedia url of the dynasty
     */
    public void setUrl(String url) {
        this.url = url;
    }

    public void setCounter(int counter){ this.counter = counter;}
}
