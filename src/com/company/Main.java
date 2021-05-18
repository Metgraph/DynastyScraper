package com.company;

import javax.swing.*;

public class Main {

    private static InputInterface LINKFRAME;

    /**
     * Starts the program
    **/

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Main.LINKFRAME = new InputInterface();
            }
        });
    }
}