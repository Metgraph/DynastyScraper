package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputInterface {

    private String url;
    private String dynastyName;

    public InputInterface(){
        JFrame inpGUI = new JFrame();
        inpGUI.setVisible(true);
        inpGUI.setSize(780,160);
        inpGUI.setTitle("Dynasty Scraper");
        inpGUI.setResizable(false);
        inpGUI.setLocationRelativeTo(null);
        inpGUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        inpGUI.getContentPane().setLayout(null);

        JLabel linkInp = new JLabel("Inserisci link della pagina Wikipedia:");
        linkInp.setFont(new Font("Tahoma",Font.PLAIN,14));
        linkInp.setBounds(20,20,210,30);
        inpGUI.getContentPane().add(linkInp);

        JTextField linkField = new JTextField();
        linkField.setBounds(240,25,340,20);
        inpGUI.getContentPane().add(linkField);

        JLabel dynastyInp = new JLabel("Inserisci il nome della dinastia desiderata:");
        dynastyInp.setFont(new Font("Tahoma",Font.PLAIN,14));
        dynastyInp.setBounds(20,50,250,30);
        inpGUI.getContentPane().add(dynastyInp);

        JTextField dynastyField = new JTextField();
        dynastyField.setBounds(280,55,300,20);
        inpGUI.getContentPane().add(dynastyField);

        JButton createTree = new JButton("Genera albero");
        createTree.setFont(new Font("Tahoma",Font.PLAIN,14));
        createTree.setBounds(590,25,150,20);
        inpGUI.getContentPane().add(createTree);

        JButton clearFields = new JButton("Cancella tutto");
        clearFields.setFont(new Font("Tahoma",Font.PLAIN,14));
        clearFields.setBounds(590,55,150,20);
        inpGUI.getContentPane().add(clearFields);

        clearFields.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                linkField.setText(null);
                dynastyField.setText(null);
            }
        });

        createTree.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (linkField.getText().isEmpty() || dynastyField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Il link e/o il nome della dinastia " +
                            "non è/non sono stati inseriti");
                }
                else {
                    url = linkField.getText();
                    dynastyName = dynastyField.getText();
                    System.out.println("url -> " + url +
                            "\ndinasty -> " + dynastyName);
                }
            }
        });

    }

}