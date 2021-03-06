package webscraper;

import java.util.ArrayList;

/**
 * This class provide a data structure to save information about a dynasty member
 */
public class Member {
    private final String fullName;
    private final String url;
    private String dynastyName;
    private String dynastyUrl;
    private ArrayList<Member> issue;
    private Member mother;
    private Member father;
    private ArrayList<Member> spouses;
    private String biography;
    private boolean adopted;
    private boolean emperor;
    private String imageURL;
    private String name;

    /**
     * Create object with given the name and own Wikipedia page url
     *
     * @param name The person name
     * @param url  The Wikipedia page url
     */
    public Member(String name, String url) {
        this.fullName = name;
        this.url = url;
        int endName = name.indexOf('\n');
        if (endName != -1) {
            this.name = name.substring(0, endName);
        } else {
            this.name = name;
        }

        this.adopted = false;
        this.issue = new ArrayList<>();
        this.spouses = new ArrayList<>();
        this.biography = "";
        this.imageURL = "";
        this.emperor = false;
        this.dynastyUrl = "";
        this.dynastyName = "";
    }

    /**
     * Create object with the given name, own Wikipedia page url and if he's adopted     *
     * @param name The person name
     * @param url The Wikipedia page url
     * @param adopted If he's adopted
     */
    public Member(String name, String url, boolean adopted) {
        this.fullName = name;
        this.url = url;
        int endName = name.indexOf('\n');
        if (endName != -1) {
            this.name = name.substring(0, endName);
        } else {
            this.name = name;
        }
        this.adopted = adopted;
        this.issue = new ArrayList<>();
        this.spouses = new ArrayList<>();
        this.biography = "";
        this.imageURL = "";
        this.emperor = false;
        this.dynastyUrl = "";
        this.dynastyName = "";
    }

    /**
     * Get the person full name
     *
     * @return The person name
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * Get the Wikipedia page url
     *
     * @return The Wikipedia url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Get the person's sons and daughters
     *
     * @return list of people
     */
    public ArrayList<Member> getIssue() {
        return issue;
    }

    /**
     * Set the person's sons and daughters
     *
     * @param arr list of people
     */
    public void setIssue(ArrayList<Member> arr) {
        this.issue = arr;
    }

    /**
     * Get if he's adopted
     *
     * @return If he's adopted
     */
    public boolean isAdopted() {
        return adopted;
    }

    /**
     * Set if he's adopted
     *
     * @param adopted If he's adopted
     */
    public void setAdopted(boolean adopted) {
        this.adopted = adopted;
    }

    /**
     * Get the person's father
     *
     * @return Person's father's data
     */
    public Member getFather() {
        return father;
    }

    /**
     * Set the person's father
     *
     * @param father Person's father's data
     */
    public void setFather(Member father) {
        this.father = father;
    }

    /**
     * Get the person's mother
     *
     * @return Person's mother's data
     */
    public Member getMother() {
        return mother;
    }

    /**
     * Set the person's mother
     *
     * @param mother Person's mother's data
     */
    public void setMother(Member mother) {
        this.mother = mother;
    }

    /**
     * Get the person's spouses
     *
     * @return List person's spouses' data
     */
    public ArrayList<Member> getSpouses() {
        return spouses;
    }

    /**
     * Set the person's spouses
     *
     * @param spouses List person's spouses' data
     */
    public void setSpouses(ArrayList<Member> spouses) {
        this.spouses = spouses;
    }

    public String toString() {
        StringBuilder toPrint = new StringBuilder(name).append(" - ");
        if (adopted)
            toPrint.append("adottato - ");

        toPrint.append(dynastyName).append(" - ");

        toPrint.append(url);
        return toPrint.toString();
    }

    /**
     * Get the person's dynasty name
     *
     * @return The person's dynasty name
     */
    public String getDynastyName() {
        return dynastyName;
    }

    /**
     * Set the person's dynasty name
     *
     * @param dynastyName The person's dynasty name
     */
    public void setDynastyName(String dynastyName) {
        this.dynastyName = dynastyName;
    }

    //nuovi metodi

    /**
     * Check if he's a member of the given dynasty
     *
     * @param dynastyName The dynasty name to check
     * @return If he's in the dynasty
     */
    public boolean isInTheDynasty(String dynastyName) {
        return this.dynastyUrl.equals(dynastyName);
    }

    /**
     * Get the person's dynasty Wikipedia url
     *
     * @return The person's dynasty Wikipedia url
     */
    public String getDynastyUrl() {
        return dynastyUrl;
    }

    /**
     * Set the person's dynasty Wikipedia url
     *
     * @param dynastyUrl The person's dynasty Wikipedia url
     */
    public void setDynastyUrl(String dynastyUrl) {
        this.dynastyUrl = dynastyUrl;
    }

    /**
     * Get the person's biography
     *
     * @return The person's biography
     */
    public String getBiography() {
        return biography;
    }

    /**
     * Set the person's biography
     *
     * @param biography The person's biography
     */
    public void setBiography(String biography) {
        this.biography = biography;
    }

    /**
     * Check if he's an emperor
     *
     * @return If he's an emperor
     */
    public boolean isEmperor() {
        return emperor;
    }

    /**
     * Set if he's an emperor
     *
     * @param emperor If he's an emperor
     */
    public void setEmperor(boolean emperor) {
        this.emperor = emperor;
    }


    /**
     * Get the person's Wikipedia image url
     *
     * @return The person's Wikipedia image url
     */
    public String getImageURL() {
        return imageURL;
    }

    /**
     * Set the person's Wikipedia image url
     *
     * @param imageURL The person's Wikipedia image url
     */
    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    /**
     * Get the person's name, that will be only the person main name
     *
     * @return The person's short name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the person's short name, that will be only the person main name
     *
     * @param name The person's short name
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        //if o is null returns false
        if (o==null)
            return false;

        //if o is the same object return true
        if (this == o)
            return true;

        //if o is an instance of different class returns false
        if (o.getClass() != this.getClass()) {
            return false;
        }

        //cast the object into a member instance
        Member otherMember = (Member) o;
        //if they have the same url and it isn't null or empty returns true
        //otherwise returns if the names are equals (some Member instance can not have an url)
        if (otherMember.getUrl().equals(getUrl())) {
            if (getUrl() == null || getUrl().equals("")) {
                return otherMember.getFullName().equals(getFullName());
            }
            return true;
        }
        return false;
    }
}
