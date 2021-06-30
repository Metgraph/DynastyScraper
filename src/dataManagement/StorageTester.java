package dataManagement;

import webscraper.Dynasty;
import webscraper.Member;
import java.util.*;


public class StorageTester {
    public static void main(String[] args) {
        Storage storage = new Storage("https://it.wikipedia.org/wiki/Imperatori_romani", new WebScraper());
        for(Dynasty f: storage.getDynasties()){
            System.out.println(f.getName());
        }
        try {
            System.out.println("oraaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            ArrayList<Member> m = storage.getTree("Anarchia militarefinoademiliano");
            System.out.println(m.size());
            for(Member f: m){
                //System.out.println(f.getName()+"?");
                corri(f,0);
            }
        }finally {
            storage.close();
        }

    }

    public static void corri(Member m,int v){
        for(int i=0;i<v;i++){
            System.out.print(" | ");
        }
        System.out.println(m.getUrl());
        if (m.getIssue()==null){
            return;
        }
        for(Member g: m.getIssue()){
            corri(g,v+1);
        }
    }


}