package com.company;
import webscraper.*;
import java.util.*;

import java.util.ArrayList;

public class Storage {
    private ArrayList<Dinasty> dynasties;
    private WebScraper scraber=new WebScraper();
    private Set<String> trovati;
    private Set<String> emperors;

    public Storage(String url){
        ArrayList<Dinasty> dynasties = scraber.getDynasties(url);
    }

    public Member getTree(String nameDinasty){
        for(int i=0;i<dynasties.size();i++){
            if(dynasties.get(i).getName().equals(nameDinasty)){
                return search(dynasties.get(i));
            }
        }
        return null;
    }

    private Member search(Dinasty dinasty){
        Member emperor = dinasty.getMembers().get(0);
        for(int i=0;i<dinasty.getMembers().size();i++){
            emperors.add(dinasty.getMembers().get(i).getUrl());
        }
        ricorsione(emperor);
        if(emperors.isEmpty()){
            return emperor;
        }
        //se devo creare 2 rami scollegati da aggiungere
        return null;
    }

    private void ricorsione(Member emperor){
        scraber.addMemberInfo(emperor);
        for(int i=0;i<emperor.getIssue().size();i++){
            if(emperors.remove(emperor.getUrl())){
                emperor.setEmperor(true);
            }
            if(trovati.add(emperor.getIssue().get(i).getUrl())) {
                ricorsione(emperor.getIssue().get(i));
            }
        }
    }
}
