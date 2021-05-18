package com.company;
import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class EmperorGUI extends JFrame {

    private String emperorName = "Gaio Giulio Cesare";
    private String emperorBio = "banananananana";
    private String emperorUrl = "https://it.wikipedia.org/wiki/Gaio_Giulio_Cesare";
    private String emperorPic = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8f/Gaius_Iulius_Caesar_%28Vatican_Museum%29.jpg/165px-Gaius_Iulius_Caesar_%28Vatican_Museum%29.jpg";
    public EmperorGUI(){

        //la gestione di tale pagina è divisa in 2 jpanel
        JPanel treepanel = new JPanel();
        JPanel emperorpanel = new JPanel();
        treepanel.setPreferredSize(new Dimension(960, 720));
        emperorpanel.setPreferredSize(new Dimension(320, 720));

        //creo il frame che contyiene per 3/4 la GUI dell' albero e per 1/4 la pagina relativa all' imperatore
        //fondamentale caratteristica è l'interruzione del programma a chiusura della finestra
        JFrame emperorFrame = new JFrame("Dinasty");
        emperorFrame.setLocationRelativeTo(null);
        emperorFrame.setSize(1280, 720);
        emperorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        emperorFrame.setLayout(new BorderLayout());


        //diuchiaro eventuali bottoni
        JButton button = new JButton("switch");

        BoxLayout gino = new BoxLayout(treepanel, 0);


        treepanel.add(new JLabel("culo"));
        button.setLocation(400,200);
        button.setAlignmentX(0);
        treepanel.setLayout(gino);
        treepanel.add(button, gino.X_AXIS);


        //
        emperorpanel.add(new JLabel("minchia potenz"));
        emperorpanel.setAlignmentX(960);
        emperorpanel.setBackground(Color.CYAN);



        //aggiungo i panel al frame e lo rendo visibile
        emperorFrame.add(emperorpanel, BorderLayout.WEST);
        emperorFrame.add(treepanel, BorderLayout.CENTER);




        emperorFrame.setVisible(true);

    }

}
