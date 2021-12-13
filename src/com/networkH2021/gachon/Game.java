package com.networkH2021.gachon;

import com.networkH2021.gachon.Networking.ClientObject;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Game extends JFrame{
    private DatagramSocket socket;
    private InetAddress serverAddress;
    private int port;
    private boolean online;
    private int clientID;
    private String lastMessage;
    private String opp;
    private String oppNick;

    ImageIcon[] imgIcons = {
            new ImageIcon("IMG/kawi.jpg"),
            new ImageIcon("IMG/bawi.jpg"),
            new ImageIcon("IMG/bo.jpg")

    };

    SelectPanel select = new SelectPanel();
    ResultDisplay result = new ResultDisplay();

    public Game() {
        super("RSC");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        add(result,"Center");
        add(select, "South");

        setSize(600,600);
        setVisible(false);
    }


    class SelectPanel extends JPanel{
        JButton[] btnButtons = new JButton[3];

        public SelectPanel() {
            setBackground(Color.gray);

            for(int i=0; i<imgIcons.length; i++) {
                btnButtons[i] = new JButton(imgIcons[i]);
                this.add(btnButtons[i]);

                btnButtons[i].addActionListener(new EventHandler());
            }
        }
    }

    class ResultDisplay extends JPanel{
        JLabel userJLabel = new JLabel("user1");
        JLabel comJLabel = new JLabel("user2");
        JLabel resultJLabel = new JLabel("winner");

        public ResultDisplay() {
            setBackground(Color.white);
            add(userJLabel);
            add(resultJLabel);
            add(comJLabel);
        }
        public void output(Icon img,Icon comImage, String res ) {
            userJLabel.setIcon(img);
            userJLabel.setHorizontalTextPosition(JLabel.LEFT);
            comJLabel.setIcon(comImage);
            resultJLabel.setText(res);
            result.setFont(new Font(Font.DIALOG, Font.BOLD, 20));

        }
    }

    public void send(String message){
        try {
            message = message + "\\e";
            byte[] data = message.getBytes(StandardCharsets.UTF_8);
            DatagramPacket packet = new DatagramPacket(data, data.length,serverAddress, port);
            socket.send(packet);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public String getOppNick() {
        return oppNick;
    }

    public void receive(String myNick,String oppNick,int oppG){


    }

    class EventHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            //getSource가 Object 타입임으로 타입변환을 해야한다.
            JButton btnSrc = (JButton)e.getSource();
            String a;
            String res = "";

            if(btnSrc.getIcon() == imgIcons[0]){
                a="0";
                GameLauncher.getClient().send("\\G"+GameLauncher.getUserDAO().getNickname()+","+oppNick+","+a);
            }
            else if(btnSrc.getIcon() == imgIcons[1]){
                a="1";
                GameLauncher.getClient().send("\\G"+GameLauncher.getUserDAO().getNickname()+","+oppNick+","+a);
            }
            else if(btnSrc.getIcon() == imgIcons[2]){
                a="2";
                GameLauncher.getClient().send("\\G"+GameLauncher.getUserDAO().getNickname()+","+oppNick+","+a);
            }

            if(btnSrc.getIcon() == imgIcons[0] && Integer.parseInt(opp) == 2 ||
                    btnSrc.getIcon() == imgIcons[1] && Integer.parseInt(opp) == 0 ||
                    btnSrc.getIcon() == imgIcons[2] && Integer.parseInt(opp) == 1 )
                res = "YOU WIN!";
            else if(btnSrc.getIcon() == imgIcons[0] && Integer.parseInt(opp) == 0 ||
                    btnSrc.getIcon() == imgIcons[1] && Integer.parseInt(opp) == 1 ||
                    btnSrc.getIcon() == imgIcons[2] && Integer.parseInt(opp) == 2 )
                res = "DRAW!!";
            else
                res = "YOU LOSE";
            result.output(btnSrc.getIcon(), imgIcons[Integer.parseInt(opp)], res);
        }

    }
}