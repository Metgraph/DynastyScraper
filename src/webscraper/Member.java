package webscraper;

import java.util.ArrayList;

/**
 * This class provide a data structure to save information about a dynasty member
 */
public class Member {
    private final String name;
    private final String url;
    private String beginReign;
    private String endReign;
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
    private String shortName;

    /**
     * Create object with given the name and own Wikipedia page url
     *
     * @param name The person name
     * @param url  The Wikipedia page url
     */
    public Member(String name, String url) {
        this.name = name;
        this.url = url;
        int endName = name.indexOf('\n');
        if (endName != -1) {
            this.shortName = name.substring(0, endName);
        } else {
            this.shortName = name;
        }

        this.adopted = false;
    }

    /**
     * Create object with the given name, own Wikipedia page url and if he's adopted
     *
     * @param name
     * @param url
     * @param adopted
     */
    public Member(String name, String url, boolean adopted) {
        this.name = name;
        this.url = url;
        int endName = name.indexOf('\n');
        if (endName != -1) {
            this.shortName = name.substring(0, endName);
        } else {
            this.shortName = name;
        }
        this.adopted = adopted;
    }

    /**
     * Get the person name
     *
     * @return The person name
     */
    public String getName() {
        return name;
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
     * Get when his reign ended
     *
     * @return The date of the reign end
     */
    public String getEndReign() {
        return endReign;
    }

    /**
     * Set when his reign ended
     *
     * @param endReign The date when the reign end
     */
    public void setEndReign(String endReign) {
        this.endReign = endReign;
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
     * Get when his reign started
     *
     * @return The date when the reign begin
     */
    public String getBeginReign() {
        return beginReign;
    }

    /**
     * Set when his reign started
     *
     * @param beginReign The date when the reign begin
     */
    public void setBeginReign(String beginReign) {
        this.beginReign = beginReign;
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

    //TODO cambiare con qualcosa di migliore

//    public boolean equals(Member o) {
//        if(this.url.equals(o.getUrl())){
//            if(this.url.equals("")){
//                if(this.name.equals(o.getName())){
//                    return true;
//                }
//            }else{
//                return true;
//            }
//        }
//        return false;
//    }


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
     * Get the person's short name, that will be only the person main name
     *
     * @return The person's short name
     */
    public String getShortName() {
        return shortName;
    }

    /**
     * Set the person's short name, that will be only the person main name
     *
     * @param shortName The person's short name
     */
    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public boolean equals(Object o) {
        if (o==null)
            return false;

        if (this == o)
            return true;

        if (o.getClass() != this.getClass()) {
            return false;
        }
        Member otherMember = (Member) o;
        if (otherMember.getUrl().equals(getUrl())) {
            if (getUrl() == null || getUrl().equals("")) {
                return otherMember.getName().equals(getName());
            }
            return true;
        }
        return false;
    }
}
