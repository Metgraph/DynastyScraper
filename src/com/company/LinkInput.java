package com.company;

import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class LinkInput{

    public LinkInput() {
        JFrame linkInFrame = new JFrame();
        linkInFrame.setVisible(true);
        linkInFrame.setTitle("Dinasty Scraper");
        linkInFrame.setBounds(100, 100,690,150);
        linkInFrame.setResizable(false);
        linkInFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        linkInFrame.getContentPane().setLayout(null);

        JLabel linkLabel = new JLabel("Inserisci link della pagina Wikipedia:");
        linkLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
        linkLabel.setBounds(20, 20, 236, 23);
        linkInFrame.getContentPane().add(linkLabel);

        JTextField linkField = new JTextField();
        linkField.setBounds(246,20,400,23);
        linkField.setFont(new Font("Tahoma", Font.PLAIN, 14));
        linkInFrame.getContentPane().add(linkField);
        linkField.setColumns(10);

        JButton enterButton = new JButton("Avanti");

        enterButton.addActionListener(e -> {
            goToDinIn();
            linkInFrame.dispose();
        });

        enterButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        enterButton.setBounds(246, 50,123, 23);
        linkInFrame.getContentPane().add(enterButton);

        JButton deleteButton = new JButton("Cancella");

        deleteButton.addActionListener(e -> linkField.setText(null));

        deleteButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        deleteButton.setBounds(379,50,91,23);
        linkInFrame.getContentPane().add(deleteButton);

        JButton shutButton = new JButton("Esci dal programma");

        shutButton.addActionListener(e -> System.exit(0));

        shutButton.setFont(new Font("Tahoma", Font.PLAIN, 14));
        shutButton.setBounds(480, 50, 166, 23);
        linkInFrame.getContentPane().add(shutButton);
    }

    public void goToDinIn()
    {
        DinastyInput dinIn = new DinastyInput();
    }

}
