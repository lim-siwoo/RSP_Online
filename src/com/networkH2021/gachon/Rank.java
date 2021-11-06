package com.networkH2021.gachon;

import javax.swing.*;

public class Rank extends JFrame{
    private JList list1;
    private JPanel rankPanel;

    public Rank(){
        setTitle("RSP Online Main Lobby");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(this.rankPanel);
        pack();
        setLocationRelativeTo(null);
        setVisible(false);
    }
}
