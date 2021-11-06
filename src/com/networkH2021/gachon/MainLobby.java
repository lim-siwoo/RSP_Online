package com.networkH2021.gachon;

import javax.swing.*;

public class MainLobby extends JFrame{
    private JTextField textField1;
    private JTextField textField2;
    private JButton Rankbutton;
    private JButton Sendbutton;
    private JButton INFOButton5;
    private JButton INFOButton4;
    private JButton INFOButton;
    private JButton INFOButton3;
    private JButton INFOButton2;
    private JButton INFOButton1;
    private JButton INVITEButton;
    private JButton INVITEButton1;
    private JButton INVITEButton2;
    private JButton INVITEButton3;
    private JButton INVITEButton4;
    private JButton INVITEButton5;
    private JPanel panelLobby;

    public MainLobby(){
        setTitle("RSP Online Main Lobby");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelLobby);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);



    }
}
