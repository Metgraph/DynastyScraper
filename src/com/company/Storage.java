package com.company;

import webscraper.*;
import java.util.*;

public class Storage {
    private ArrayList<Dinasty> dynasties;   //contiene tutte le dinastie
    private WebScraper scraper;             //scaber
    private Set<String> trovati;            //contiene i Member gia analizzati
    private Set<String> emperors;           //contiene gli tutti gli imperatori da analizzare

    /**
     *  Costruttore prende come parametri ulr di wikipedia della paggina delle dinastie
     *  creando poi tutto quello che servirà per ottenere le informazioni necessarie
     * @param url
     */

    public Storage(String url) {
        emperors = new TreeSet<>();
        trovati = new TreeSet<>();
        scraper = new WebScraper();
        dynasties = scraper.getDynasties(url);
    }

    /**
     *  Crea un Member che poi ritornerà prendendo come parametro il nome della dinastia
     *  il Member ritornato è la radice della dinastia indicata
     * @param nameDinasty
     * @return Member
     */

    public ArrayList<Member> getTree(String nameDinasty) {
        //cerca la dinastia indicata
        for (int i = 0; i < dynasties.size(); i++) {
            if (dynasties.get(i).getName().equals(nameDinasty)) {
                return search(dynasties.get(i));
            }
        }
        return null;
    }

    /**
     *  Cerca tutto quello che c'è da sapere su i vari imperatori della dinastia
     *  prende come parametro la dinastia in cui deve cercare
     *  ritorna un Member che sarà la radice dell'albero creato per la dinastia
     * @param dinasty
     * @return
     */

    private ArrayList<Member> search(Dinasty dinasty) {
        Member emperor = dinasty.getMembers().get(0);   //prende il primo imperatore
        ArrayList<Member> trees=new ArrayList<>();

        //riempe l'insieme di emperors
        for (int i = 0; i < dinasty.getMembers().size(); i++) {
            emperors.add(dinasty.getMembers().get(i).getUrl());
        }
        int i=0;
        //cicla finchè non si svuola emperors
        while(!emperors.isEmpty()) {
            //aggiunge l'imperatore all'albero
            trees.add(emperor);
            //crea l'albero con radice l'imperatore
            ricorsione(emperor);
            i++;
            //cerca il prossimo imperatore non analizzato
            while(!emperors.remove(dinasty.getMembers().get(i).getUrl())){
                i++;
            }
            //riaggiunge l'imperatore tolto per poterlo analizzare
            emperors.add(dinasty.getMembers().get(i).getUrl());
            //setta su emperor il prossimo imperatore da analizzare
            emperor=dinasty.getMembers().get(i);
        }
        return trees;

    }

    /**
     *  Metodo che cerca ricorsivamente le informazioni dei vari imperatori partendo da una radice e creandone un relativo albero
     *  prende come parametro il Member da cui dovrà iniziare a cercare
     * @param emperor
     */

    private void ricorsione(Member emperor) {
        //cerca le informazioni sull'imperatore
        scraper.addMemberInfo(emperor);
        //cerca le informazioni per tutti i suoi figli
        for (int i = 0; i < emperor.getIssue().size(); i++) {
            //controlla se il figlio è un imperatore
            if (emperors.remove(emperor.getUrl())) {
                emperor.setEmperor(true);
            }
            //controlla se il Member è gia stato analizzato
            if (trovati.add(emperor.getIssue().get(i).getUrl())) {
                ricorsione(emperor.getIssue().get(i));
            }
        }
    }

    /**
     *  metodo getDynasties
     * @return
     */

    public ArrayList<Dinasty> getDynasties() {
        return dynasties;
    }

    /**
     *  chiude lo scraber
     */

    public void close() {
        scraper.close();
    }
}
