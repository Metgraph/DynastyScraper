package com.company;

import webscraper.*;

import java.util.*;

import java.util.ArrayList;

public class Storage {
    private ArrayList<Dinasty> dynasties;
    private WebScraper scraper;
    private Set<String> trovati;
    private Set<String> emperors;

    public Storage(String url) {
        emperors = new TreeSet<>();
        trovati = new TreeSet<>();
        scraper = new WebScraper();
        dynasties = scraper.getDynasties(url);
    }

    public Member getTree(String nameDinasty) {
        for (int i = 0; i < dynasties.size(); i++) {
            if (dynasties.get(i).getName().equals(nameDinasty)) {
                return search(dynasties.get(i));
            }
        }
        return null;
    }

    private Member search(Dinasty dinasty) {
        Member emperor = dinasty.getMembers().get(1);
                        System.out.println(dinasty.getMembers().size());

        for (int i = 0; i < dinasty.getMembers().size(); i++) {
            emperors.add(dinasty.getMembers().get(i).getUrl());
        }

//        for (Member member : dinasty.getMembers()) {
//            emperors.add(member.getUrl());
//        }

        ricorsione(emperor);
        if (emperors.isEmpty()) {
            return emperor;
        }
        //se devo creare 2 rami scollegati da aggiungere
        return null;
    }

    private void ricorsione(Member emperor) {
                        System.out.println("ricorsione: " + emperor.getUrl());
        scraper.addMemberInfo(emperor);
        for (int i = 0; i < emperor.getIssue().size(); i++) {
            if (emperors.remove(emperor.getUrl())) {
                emperor.setEmperor(true);
            }
            if (trovati.add(emperor.getIssue().get(i).getUrl())) {
                ricorsione(emperor.getIssue().get(i));
            }
        }
    }

    public ArrayList<Dinasty> getDynasties() {
        return dynasties;
    }

    public void close() {
        scraper.close();
    }
}
