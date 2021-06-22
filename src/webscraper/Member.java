package webscraper;

import java.util.ArrayList;

public class Member{
    private final String name;
    private final String url;
    private String startReign;
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

    public Member(String name, String url) {
        this.name = name;
        this.url = url;
        int endName = name.indexOf('\n');
        if(endName != -1){
            this.shortName = name.substring(0, endName);
        }else{
            this.shortName = name;
        }

        this.adopted = false;
    }

    public Member(String name, String url, boolean adopted) {
        this.name = name;
        this.url = url;
        this.adopted = adopted;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public String getEndReign() {
        return endReign;
    }

    public void setEndReign(String endReign) {
        this.endReign = endReign;
    }

    public ArrayList<Member> getIssue() {
        return issue;
    }

    public void setIssue(ArrayList<Member> arr){
        this.issue = arr;
    }

    public String getStartReign() {
        return startReign;
    }

    public void setStartReign(String startReign) {
        this.startReign = startReign;
    }

    public boolean isAdopted() {
        return adopted;
    }

    public void setAdopted(boolean adopted) {
        this.adopted = adopted;
    }

    public Member getFather() {
        return father;
    }

    public void setFather(Member father) {
        this.father = father;
    }

    public Member getMother() {
        return mother;
    }

    public void setMother(Member mother) {
        this.mother = mother;
    }

    public ArrayList<Member> getSpouses() {
        return spouses;
    }

    public void setSpouses(ArrayList<Member> spouses) {
        this.spouses = spouses;
    }

    public String toString() {
        StringBuilder toPrint = new StringBuilder(name).append(" - ");
        if(adopted)
            toPrint.append("adottato - ");

        toPrint.append(dynastyName).append(" - ");

        toPrint.append(url);
        return toPrint.toString();
    }

    public String getDynastyName() {
        return dynastyName;
    }

    public void setDynastyName(String dynastyName) {
        this.dynastyName = dynastyName;
    }

    //nuovi metodi
    public boolean isInTheDynasty(String dynastyName){
        return this.dynastyUrl.equals(dynastyName);
    }

    public String getDynastyUrl() {
        return dynastyUrl;
    }

    public void setDynastyUrl(String dynastyUrl) {
        this.dynastyUrl = dynastyUrl;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public boolean isEmperor() {
        return emperor;
    }

    public void setEmperor(boolean emperor) {
        this.emperor = emperor;
    }

    //TODO cambiare con qualcosa di migliore

    public boolean equals(Member o) {
        if(this.url.equals(o.getUrl())){
            if(this.url.equals("")){
                if(this.name.equals(o.getName())){
                    return true;
                }
            }else{
                return true;
            }
        }
        return false;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}
