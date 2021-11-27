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
        setLocationRelativeTo(null);
        setVisible(false);
    }

    public void update(){
        textArea1.setText(null);
        for (String rank : GameLauncher.getUserDAO().getRank()) textArea1.append(rank + "\n");
    }

}
