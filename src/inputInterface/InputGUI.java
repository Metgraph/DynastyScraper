package inputInterface;

import webscraper.Dynasty;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Input GUI to select a Roman dynasty and view its family tree
 */
public class InputGUI extends JFrame {

    private String[] dynasties;
    private final String URL = "https://it.wikipedia.org/wiki/Imperatori_romani";
    private String dynastyName = "";
    private boolean finished = true;

    /**
     * Creates input GUI.
     */
    public InputGUI(ArrayList<Dynasty> dynasties) {
        this.dynasties = new String[dynasties.size()];
        for (int i = 0; i < dynasties.size(); i++){
            this.dynasties[i] = dynasties.get(i).getName();
        }
        // frame settings
        setSize(600, 355);
        setTitle("Dynasty Scraper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(null);

        // graphical list containing all the dynasties
        JList dynastyList = new JList(this.dynasties);
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