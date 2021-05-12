package com.company;
import javax.swing.*;
import java.awt.*;

public class EmperorGUI extends JFrame {

    String emperorName = "Gaio Giulio Cesare";
    String emperorBio = "banananananana";
    String emperorUrl = "https://it.wikipedia.org/wiki/Gaio_Giulio_Cesare";
    String emperorPic = "https://upload.wikimedia.org/wikipedia/commons/thumb/8/8f/Gaius_Iulius_Caesar_%28Vatican_Museum%29.jpg/165px-Gaius_Iulius_Caesar_%28Vatican_Museum%29.jpg";
    public EmperorGUI(){
        JFrame emperorFrame = new JFrame("Dinasty");
        emperorFrame.setLocationRelativeTo(null);
        emperorFrame.setSize(1280, 720);
        emperorFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        emperorFrame.setVisible(true);

    }

}
