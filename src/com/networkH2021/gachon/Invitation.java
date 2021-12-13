package com.networkH2021.gachon;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Invitation extends JFrame{


    private JPanel panelInvite;
    private JButton 승락Button;
    private JButton 거절Button;
    private JLabel inviteLabel;
    private String oppNick;

    public Invitation(){
        setTitle("RSP Online Main Lobby");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(this.panelInvite);
        pack();
        setLocationRelativeTo(null);
        setVisible(false);
        승락Button.addActionListener(new ActionListener() {//승낙했을때!
            @Override
            public void actionPerformed(ActionEvent e) {
                GameLauncher.getClient().send("\\y"+GameLauncher.getUserDAO().getNickname()+","+oppNick);
                GameLauncher.getMainLobby().showGameRoom();
                setVisible(false);
            }
        });
        거절Button.addActionListener(new ActionListener() {//거절했을때!
            @Override
            public void actionPerformed(ActionEvent e) {
                //여기추가
                GameLauncher.getClient().send("\\n"+GameLauncher.getUserDAO().getNickname());
                setVisible(false);
            }
        });
    }


    public String getOppNick() {
        return oppNick;
    }

    public void setOppNick(String oppNick) {
        this.oppNick = oppNick;
    }

    public JLabel getInviteLabel() {
        return inviteLabel;
    }

    public void setInviteLabel(JLabel inviteLabel) {
        this.inviteLabel = inviteLabel;
    }

    public JPanel getPanelInvite() {
        return panelInvite;
    }

    public void setPanelInvite(JPanel panelInvite) {
        this.panelInvite = panelInvite;
    }
}
