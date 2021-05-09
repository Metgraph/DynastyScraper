package com.company;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class DinastyInput {

    public DinastyInput() {
        JFrame dinastyInFrame = new JFrame();
        dinastyInFrame.setVisible(true);
        dinastyInFrame.setTitle("Dinasty Scraper");
        dinastyInFrame.setBounds(100, 100,520,170);
        dinastyInFrame.setResizable(false);
        dinastyInFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        dinastyInFrame.getContentPane().setLayout(null);

        JLabel dinastyLabel = new JLabel("Inserisci il nome della dinastia romana:");
        dinastyLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        dinastyLabel.setBounds(20, 20, 230, 23);
        dinastyInFrame.getContentPane().add(dinastyLabel);

        JTextField dinastyField = new JTextField();
        dinastyField.setBounds(260, 20, 220,23);
        dinastyField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        dinastyInFrame.getContentPane().add(dinastyField);

        JButton deleteButton = new JButton("Cancella");

        deleteButton.addActionListener(e -> dinastyField.setText(null));

        deleteButton.setBounds(260,50, 100, 23);
        deleteButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        dinastyInFrame.getContentPane().add(deleteButton);

        JButton backButton =  new JButton("Indietro");

        backButton.addActionListener( e -> {
            goBack();
            dinastyInFrame.dispose();
        });

        backButton.setBounds(380,50,100,23);
        backButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        dinastyInFrame.getContentPane().add(backButton);

        JButton treeButton = new JButton("Genera genealogia");
        treeButton.setBounds(260,80, 220, 23);
        treeButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        dinastyInFrame.getContentPane().add(treeButton);

    }

    public void goBack() {
        LinkInput linIn = new LinkInput();
    }
}