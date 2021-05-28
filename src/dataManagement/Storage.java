package dataManagement;

import org.openqa.selenium.NoSuchElementException;
import webscraper.*;

import java.util.*;

public class Storage {
    private ArrayList<Dynasty> dynasties;   //contiene tutte le dinastie
    private WebScraper scraper;             //scaber
    private Set<Member> trovati;            //contiene i Member gia analizzati

    /**
     *  Costruttore prende come parametri ulr di wikipedia della paggina delle dinastie
     *  creando poi tutto quello che servirà per ottenere le informazioni necessarie
     * @param url
     */

    public Storage(String url) {
        trovati = new TreeSet<>();
        scraper = new WebScraper();
        dynasties = scraper.getDynasties(url);
    }

    /**
     *  Crea un Member che poi ritornerà prendendo come parametro il nome della dinastia
     *  i Member ritornati sono le radici della dinastia indicata
     * @param nameDinasty
     * @return ArrayList di radici
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
     *  ritorna i Member che sono le radici dell'albero creato per la dinastia
     * @param dinasty
     * @return ArrayList di radici
     */

    private ArrayList<Member> search(Dynasty dinasty) {
        ArrayList<Member> trees=new ArrayList<>();

        //cicla finchè per tutti gli emperors nella dinastia
        for(Member emperor :dinasty.getMembers()) {
            //aggiunge l'imperatore all'albero
            trees.add(emperor);
            //crea l'albero con radice l'imperatore
            ricorsione(emperor,trees);
        }
        return trees;

    }

    /**
     *  Metodo che cerca ricorsivamente le informazioni dei vari imperatori partendo da una radice e creandone un relativo albero
     *  prende come parametro il Member da cui dovrà iniziare a cercare
     * @param emperor
     */

    private void ricorsione(Member emperor,ArrayList<Member> trees) {
        //cerca le informazioni sull'imperatore
        scraper.addMemberInfo(emperor);
        emperor.setEmperor(true);
        scraper.addMemberInfo(emperor.getFather());
        if(trovati.contains(emperor.getFather())){
            for(Member father : trovati){
                if(father.equals(emperor.getFather())){
                    emperor.setFather(father);
                    trees.remove(emperor);
                    break;
                }
            }
        }
        //cerca le informazioni per tutti i suoi figli
        for (int i = 0; i < emperor.getIssue().size(); i++) {
            //controlla se il figlio è un imperatore
            try {
                scraper.addMemberInfo(emperor.getIssue().get(i));
                trovati.add(emperor.getIssue().get(i));
            } catch (NoSuchElementException e){

            }

//            //controlla se il Member è gia stato analizzato
//            if (trovati.add(emperor.getIssue().get(i).getUrl())) {
//                ricorsione(emperor.getIssue().get(i));
//            }
        }
    }

    /**
     *  metodo getDynasties
     * @return
     */

    public ArrayList<Dynasty> getDynasties() {
        return dynasties;
    }

    /**
     *  chiude lo scraber
     */

    public void close() {
        scraper.close();
    }
}
