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

    public void setUrl(String url) {
        this.url = url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSons(ArrayList<Son> sons) {
        this.sons = sons;
    }

    public void setMother(String mother) {
        this.mother = mother;
    }

    public void setWives(ArrayList<Tree> wives) {
        this.wives = wives;
    }

    public void setEmperor(boolean emperor) {
        this.emperor = emperor;
    }

    public void setDictator(boolean dictator) {
        this.dictator = dictator;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Son> getSons() {
        return sons;
    }

    public String getMother() {
        return mother;
    }

    public ArrayList<Tree> getWives() {
        return wives;
    }

    public boolean isEmperor() {
        return emperor;
    }

    public boolean isDictator() {
        return dictator;
    }

    public String getStartYear() {
        return startYear;
    }

    public String getEndYear() {
        return endYear;
    }

    public void addSon(Son son){
        sons.add(son);
    }

    public void addWife(Tree wife){
        wives.add(wife);
    }
}
