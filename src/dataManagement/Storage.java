package dataManagement;

import webscraper.*;

import java.util.*;

public class Storage {
    private ArrayList<Dynasty> dynasties;   // contains all dynasties
    private DynastiesScraper scraper;             // scraper
    private Set<Member> trovati;            // contains all Member already analyzed

    /**
     * Constructor that takes the dynasties' Wikipedia url and the scraper to use,
     * creating everything needed to obtain the necessary informations.
     * @param url, scraper
     *
     * Costruttore prende come parametri url di wikipedia della paggina delle dinastie e lo scaper da utilizzare
     * creando poi tutto quello che servirà per ottenere le informazioni necessarie
     * @param url, scraper
     */

    public Storage(String url, DynastiesScraper scraper) throws IllegalArgumentException {
        trovati = new HashSet<>();
        this.scraper = scraper;
        dynasties = scraper.getDynasties(url);
    }

    /**
     * Creates an ArrayList of mMember that will be returned, taking the dynasty's name as parameter.
     * The returned Member are the roots of the indicated dynasty
     * @return ArrayList of roots
     *
     * Crea un ArrayList di Member che poi ritornerà prendendo come parametro il nome della dinastia
     * i Member ritornati sono le radici della dinastia indicata
     * @return ArrayList di radici
     */

    public ArrayList<Member> getTree(String nameDinasty) {
        //cerca la dinastia indicata | finds the indicated dynasty
        for (int i = 0; i < dynasties.size(); i++) {
            //verifica quella indicata | verifies the indicated dynasty
            if (dynasties.get(i).getName().equals(nameDinasty)) {
                //una volta trovata cerca tutti i membri | once the dynasty is found, the method finds all the members
                return search(dynasties.get(i));
            }
        }
        return null;
    }

    /**
     * Looks for everything to know about the dynasty's emperors.
     * Takes the dynasty where to to find the informations as parameter.
     * Returns all Member which are the tree's roots created for the dynasty
     * @return ArrayList of roots
     *
     * Cerca tutto quello che c'è da sapere su i vari imperatori della dinastia
     * prende come parametro la dinastia in cui deve cercare
     * ritorna i Member che sono le radici dell'albero creato per la dinastia
     * @return ArrayList di radici
     */

    private ArrayList<Member> search(Dynasty dinasty) {
        //variabile di ritorno | viarable to return
        ArrayList<Member> trees = new ArrayList<>();
        if(dinasty.getMembers()!=null){
            //cicla finchè per tutti gli emperors nella dinastia | loops all the emperors in the dynasty
            for (Member emperor : dinasty.getMembers()) {
                //aggiunge l'imperatore come radice dell'albero | adds the emperor as the tree's root
                trees.add(emperor);
                //crea l'albero con radice l'imperatore o inserice l'imperatore come nodo dell'albero se necessario | creates the tree with the emperor as the root, or inserts the emperor as the tree's node if necessary
                ricorsione(emperor, trees);
            }
        }
        return trees;

    }

    /**
     * Method that recursively finds the informations of various emperors, starting from a root and creating a relative tree.
     * Takes the Member where the method has to start looking as parameter
     * @param emperor
     *
     * Metodo che cerca ricorsivamente le informazioni dei vari imperatori partendo da una radice e creandone un relativo albero
     * prende come parametro il Member da cui dovrà iniziare a cercare
     *
     * @param emperor
     */

    private void ricorsione(Member emperor, ArrayList<Member> trees) {
        //controlla se l'imperatore è null
        if(emperor==null){
            trees.remove(emperor);
            return;
        }
        try {
            //cerca le informazioni sull'imperatore | finds informations about the emperor
            scraper.addMemberInfo(emperor);
            //dichiara che è un imperatore | declares that he's an emperor
            emperor.setEmperor(true);
            //controlla se ha un padre | finds if he has a father
            scraper.addMemberInfo(emperor.getFather());
        } catch (IllegalArgumentException e) {
            return;
        }
        //controlla se il padre è gia parte dell'albero | checks if the father is already part of the tree
        //itera per tutti i member già analizzati | iterates all the analised Member
        for (Member father : trovati) {

            //aggiorna il foglio nel padre | updates the father's child
            if (father.getIssue() != null) {
                for (int i = 0; i < father.getIssue().size(); i++) {
                    if (father.getIssue().get(i).equals(emperor)) {
                        //controlla se il figlio è adottato | checks if the child is adopted
                        if (father.getIssue().get(i).isAdopted()) {
                            //imposta il figlio come adottato | sets the child as adopted
                            emperor.setAdopted(true);
                        }
                        //imposta il padre trovato come padre dell'imperatore | sets the found father as the emperor's father
                        emperor.setFather(father);
                        father.getIssue().set(i, emperor);
                        //rimuove l'imperatore come radice del'albero | removes the emperor as the tree's root
                        trees.remove(emperor);
                    }
                }
            }

        }
        //aggiunge l'imperatore tra i trovati | adds the emperor to the found ones
        trovati.add(emperor);
        //cerca le informazioni per tutti i suoi figli | finds the informations about his children
        for (int i = 0; i < emperor.getIssue().size(); i++) {

            try {
                scraper.addMemberInfo(emperor.getIssue().get(i));
                if(emperor.getIssue().get(i).getIssue()!=null){
                    for(Member son:emperor.getIssue().get(i).getIssue()){
                        trovati.add(son);
                    }
                }
            } catch (Exception e) {

            }
            //aggiunge il figlio tra i trovati | adds the child to the found ones
            trovati.add(emperor.getIssue().get(i));
        }
    }

    /**
     * method getDynasties
     *
     * metodo getDynasties
     *
     * @return
     */

    public ArrayList<Dynasty> getDynasties() {
        return dynasties;
    }

    /**
     * closes the scraper
     *
     * chiude lo scraper
     */

    public void close() {
        scraper.close();
    }
}
