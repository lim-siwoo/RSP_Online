package com.networkH2021.gachon;

import javax.swing.*;

public class Rank extends JFrame{
    private JPanel rankPanel;
    private JTextArea textArea1;

    public Rank(){
        setTitle("RSP Online Main Lobby");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(this.rankPanel);
        pack();
        GameLauncher.database.rank();

        setLocationRelativeTo(null);
        setVisible(false);
    }


}
