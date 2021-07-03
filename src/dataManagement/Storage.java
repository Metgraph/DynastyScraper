package dataManagement;

import webscraper.*;

import java.util.*;

public class Storage {
    private ArrayList<Dynasty> dynasties;   // contains all dynasties
    private DynastiesScraper scraper;             // scraper
    private Set<Member> trovati;            // contains all Member already analyzed

    /**
     * Costruttore prende come parametri url di wikipedia della paggina delle dinastie e lo scaper da utilizzare
     * creando poi tutto quello che servirà per ottenere le informazioni necessarie
     *
     * @param url, scraper
     */

    public Storage(String url, DynastiesScraper scraper) {
        trovati = new HashSet<>();
        this.scraper = scraper;
        dynasties = scraper.getDynasties(url);
    }

    /**
     * Crea un ArrayList di Member che poi ritornerà prendendo come parametro il nome della dinastia
     * i Member ritornati sono le radici della dinastia indicata
     *
     * @param nameDinasty
     * @return ArrayList di radici
     */

    public ArrayList<Member> getTree(String nameDinasty) {
        //cerca la dinastia indicata
        for (int i = 0; i < dynasties.size(); i++) {
            //verifica quella indicata
            if (dynasties.get(i).getName().equals(nameDinasty)) {
                //una volta trovata cerca tutti i membri
                return search(dynasties.get(i));
            }
        }
        return null;
    }

    /**
     * Cerca tutto quello che c'è da sapere su i vari imperatori della dinastia
     * prende come parametro la dinastia in cui deve cercare
     * ritorna i Member che sono le radici dell'albero creato per la dinastia
     *
     * @param dinasty
     * @return ArrayList di radici
     */

    private ArrayList<Member> search(Dynasty dinasty) {
        //variabile di ritorno
        ArrayList<Member> trees = new ArrayList<>();
        //cicla finchè per tutti gli emperors nella dinastia
        for (Member emperor : dinasty.getMembers()) {
            //aggiunge l'imperatore come radice dell'albero
            trees.add(emperor);
            //crea l'albero con radice l'imperatore o inserice l'imperatore come nodo dell'albero se necessario
            ricorsione(emperor, trees);
        }
        return trees;

    }

    /**
     * Metodo che cerca ricorsivamente le informazioni dei vari imperatori partendo da una radice e creandone un relativo albero
     * prende come parametro il Member da cui dovrà iniziare a cercare
     *
     * @param emperor
     */

    private void ricorsione(Member emperor, ArrayList<Member> trees) {
        //cerca le informazioni sull'imperatore
        scraper.addMemberInfo(emperor);
        //dichiara che è un imperatore
        emperor.setEmperor(true);
        //controlla se ha un padre
        try {
            scraper.addMemberInfo(emperor.getFather());
        } catch (Exception e) {

        }
        //controlla se il padre è gia parte dell'albero
        //itera per tutti i member già analizzati
        for (Member father : trovati) {
            //controlla se sono uguali
            if (father.equals(emperor.getFather())) {
                //imposta il padre trovato come padre dell'imperatore
                emperor.setFather(father);
                //aggiorna il foglio nel padre
                if (father.getIssue() != null) {
                    for (int i = 0; i < father.getIssue().size(); i++) {
                        if (father.getIssue().get(i).equals(emperor)) {
                            father.getIssue().set(i, emperor);
                            //rimuove l'imperatore come radice del'albero
                            trees.remove(emperor);
                        }
                    }
                }
            }
        }
        //aggiunge l'imperatore tra i trovati
        trovati.add(emperor);
        //cerca le informazioni per tutti i suoi figli
        for (int i = 0; i < emperor.getIssue().size(); i++) {

            try {
                scraper.addMemberInfo(emperor.getIssue().get(i));
            } catch (Exception e) {

            }
            //aggiunge il figlio tra i trovati
            trovati.add(emperor.getIssue().get(i));
        }
    }

    /**
     * metodo getDynasties
     *
     * @return
     */

    public ArrayList<Dynasty> getDynasties() {
        return dynasties;
    }

    /**
     * chiude lo scraper
     */

    public void close() {
        scraper.close();
    }
}
