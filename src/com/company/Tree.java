package com.company;

import java.util.ArrayList;

public class Tree {

    private String url;
    private String name;
    private ArrayList<Son> sons;
    private String mother;
    private ArrayList<Tree> wives;
    private boolean emperor;
    private boolean dictator;
    private String startYear;
    private String endYear;

    /**
     *  costruttore
     * @param url
     * @param name
     * @param sons
     * @param mother
     * @param wives
     * @param emperor
     * @param dictator
     * @param startYear
     * @param endYear
     */
    public Tree(String url,String name,ArrayList<Son> sons,String mother,ArrayList<Tree> wives,boolean emperor,boolean dictator,String startYear,String endYear){
        this.url=url;
        this.name=name;
        this.sons=sons;
        this.mother=mother;
        this.wives=wives;
        this.emperor=emperor;
        this.dictator=dictator;
        this.startYear=startYear;
        this.endYear=endYear;
    }

    /**
     * set url
     * @param url
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * set name
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * set sons
     * @param sons
     */
    public void setSons(ArrayList<Son> sons) {
        this.sons = sons;
    }

    /**
     * set mother
     * @param mother
     */
    public void setMother(String mother) {
        this.mother = mother;
    }

    /**
     * set wives
     * @param wives
     */
    public void setWives(ArrayList<Tree> wives) {
        this.wives = wives;
    }

    /**
     * ser emperor
     * @param emperor
     */
    public void setEmperor(boolean emperor) {
        this.emperor = emperor;
    }

    /**
     * ser dictator
     * @param dictator
     */
    public void setDictator(boolean dictator) {
        this.dictator = dictator;
    }

    /**
     * se start year
     * @param startYear
     */
    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    /**
     * set end year
     * @param endYear
     */
    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    /**
     * get url
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * get name
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * get sons
     * @return sons
     */
    public ArrayList<Son> getSons() {
        return sons;
    }

    /**
     * get mother
     * @return mother
     */
    public String getMother() {
        return mother;
    }

    /**
     * get wives
     * @return wives
     */
    public ArrayList<Tree> getWives() {
        return wives;
    }

    /**
     * is emperor
     * @return emperor
     */
    public boolean isEmperor() {
        return emperor;
    }

    /**
     * is dictator
     * @return dictator
     */
    public boolean isDictator() {
        return dictator;
    }

    /**
     * get start year
     * @return startYear
     */
    public String getStartYear() {
        return startYear;
    }

    /**
     * get end year
     * @return endYear
     */
    public String getEndYear() {
        return endYear;
    }

    /**
     * add son
     * @param son
     */
    public void addSon(Son son){
        sons.add(son);
    }

    /**
     * add wife
     * @param wife
     */
    public void addWife(Tree wife){
        wives.add(wife);
    }
}
