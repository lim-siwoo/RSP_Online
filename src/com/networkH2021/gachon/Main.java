package com.networkH2021.gachon;

import javax.swing.*;


public class Main {

    public static void main(String[] args) {
	// write your code here
        JFrame frame = new JFrame("RSP Online");
        frame.setContentPane(new Login().panelMain);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);



    }
}