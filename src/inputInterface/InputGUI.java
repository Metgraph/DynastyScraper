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
    private String dynastyName = "";
    private boolean finished = true;

    /**
     * Creates input GUI.
     */
    public InputGUI(ArrayList<Dynasty> dynasties) {
        // Creazione lista dinastie
        this.dynasties = new String[dynasties.size()];
        for (int i = 0; i < dynasties.size(); i++){
            this.dynasties[i] = dynasties.get(i).getName();
        }
        // frame settings
        setSize(600, 355);  // size frame
        setTitle("Dynasty Scraper");    // title frame
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // close frame when clicking on the X button
        setResizable(false);    // Block frame resize
        setLocationRelativeTo(null);    //  Set frame position in the middle of the screen
        setLayout(null);    // set empty layout

        // graphical list containing all the dynasties
        JList dynastyList = new JList(this.dynasties);
        dynastyList.setBounds(0,0,220, 270);    // set dimension and position in the frame of the JList
        dynastyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);  // set selection mode
        add(dynastyList); // add JList to the frame

        // sets the selected choice to dynastyName when it is clicked
        dynastyList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                dynastyName = (String) dynastyList.getSelectedValue();
            }
        });

        // button
        JButton createTree = new JButton("Genera albero");
        createTree.setBounds(0,270,220,30);   // set dimension and position in the frame of the JButton

        // disposes the frame
        createTree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (dynastyName.equals("")) { // if no dynasty is selected, shows a frame noticing the user to select a choice
                    JOptionPane.showMessageDialog(null, "Il nome della dinastia non è stato selezionato");
                } else {    // else disposes the frame
                    finished = false;
                    dispose();
                }
            }
        });

        add(createTree);    // add JButton to the frame
        setVisible(true);   // set the frame visible on the screen
    }

    /**
     * returns the name of the chosen dynasty
     * @return dynastyName the name of the selected dynasty
     */
    public String getDynastyName(){
        return dynastyName;
    }


    /**
     * returns selection status
     * @return finished
     */
    public boolean isFinished(){
        return finished;
    }

}