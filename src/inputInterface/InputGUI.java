package inputInterface;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Input GUI to select a Roman dynasty and view its family tree
 */
public class InputGUI extends JFrame {

    private final String[] dynasties = {"Gens Iulia", "Dinastia giulioclaudia", "Guerra civile Romana", "Dinastia dei Flavi",
            "Imperatori adottivi", "Guerra civile romana", "Dinastia dei Severi",
            "Anarchia militare fino ad Emiliano", "Dinastia valeriana", "Imperatori illirici",
            "Riforma tetrarchica", "Guerra civile romana", "Dinastia costantiniana",
            "Casata di Valentiniano e di Teodosio", "Casata di Teodosio", "Ultimi imperatori"};

    private final String URL = "https://it.wikipedia.org/wiki/Imperatori_romani";
    private String dynastyName = "";
    private boolean finished = true;

    /**
     * Creates input GUI.
     */
    public InputGUI() {

        // frame settings
        setSize(600, 355);
        setTitle("Dynasty Scraper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);

        // graphical list containing all the dynasties
        JList dynastyList = new JList(dynasties);
        dynastyList.setBounds(0,0,220, 270);
        dynastyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(dynastyList);

        // sets the selected choice to dynastyName when it is clicked
        dynastyList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                dynastyName = (String) dynastyList.getSelectedValue();
            }
        });

        // button
        JButton createTree = new JButton("Genera albero");
        createTree.setBounds(0,270,220,30);

        // disposes the frame
        createTree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dynastyName.equals("")) {
                    JOptionPane.showMessageDialog(null, "Il nome della dinastia non è stato inserito");
                } else {
                    finished = false;

                    /*if (finished) {

                    }else{
                        System.out.println("finito");
                    }*/
                    dispose();
                }
            }
        });

        add(createTree);
        setVisible(true);
    }

    /**
     * returns the name of the chosen dynasty
     * @return dynastyName the name of the selected dynasty
     */
    public String getDynastyName(){
        return dynastyName;
    }

    /**
     * returns the Wikipedia URL of the Roman emperors
     * @return URL
     */
    public String getURL(){
        return URL;
    }

    /**
     * returns
     * @return finished
     */
    public boolean isFinished(){
        return finished;
    }

}