package com.company;

import javax.swing.*;

public class Main {

    private static LinkInput LINKFRAME;

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Main.LINKFRAME = new LinkInput();
            }
        });
    }
}
