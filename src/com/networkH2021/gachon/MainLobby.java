package com.networkH2021.gachon;

import com.networkH2021.gachon.client.ChatClientApp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MainLobby extends JFrame{
    private JTextField textField;
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
    private JList list1;
    private JTextArea textArea;

    private DatagramSocket socket;//User Datagram Protocal
    private DatagramPacket packet;//Data packet
    private InetAddress address;//상대방주소
    private int myPort = 10001;//내 포트
    private int oppPort = 10002;//상대 포트
    private ChatClientApp LobbyChat;


    public void Chatting(){

    }

    public MainLobby(){

        setTitle("RSP Online Main Lobby");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(this.panelLobby);
        pack();
        setLocationRelativeTo(null);
        setVisible(false);
        LobbyChat = new ChatClientApp();




        Rankbutton.addActionListener(new ActionListener() { //Rank버튼 눌렀을때 실행됨
            @Override
            public void actionPerformed(ActionEvent e) {
                GameLauncher.getRank().setVisible(true);
            }
        });
    }
}
