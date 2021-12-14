package com.networkH2021.gachon;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Exit extends JFrame{
    private JButton yesButton;
    private JButton NoButton;
    private JLabel ExitSentence;
    private JPanel Exit;


    public Exit(){

        setTitle("RSP Online Exit");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(this.Exit);
        pack();
        setLocationRelativeTo(null);
        setVisible(false);
        ExitSentence.setText("Do you want Exit?");


        yesButton.addActionListener(new ActionListener() { //승낙했을때!
            @Override
            public void actionPerformed(ActionEvent e) {
                GameLauncher.getClient().send("\\G"+GameLauncher.getUserDAO().getNickname()+","+GameLauncher.getInvitation().getOppNick()+","+3);
                GameLauncher.getClient().send("\\d"+GameLauncher.getClient().getClientID());
                System.exit(0);
            }
        });
        NoButton.addActionListener(new ActionListener() { //거절했을때!
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }

}
