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

import javax.swing.*;

public class Game extends JFrame{
    private boolean SendCheck;
    private boolean ReceiveCheck;
    private int myValue;
    private String myNick;
    private int oppG;
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
        SendCheck=false;
        ReceiveCheck=false;
        receive();

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

    public String getOppNick() {
        return oppNick;
    }

    public void receive() {
        Thread thread = new Thread() {
            public void run() {
                while (true) {
                    String res = "";

                    if (SendCheck == true && ReceiveCheck == true) {
                        if (myValue == 0 && oppG == 2 ||
                                myValue == 1 && oppG == 0 ||
                                myValue == 2 && oppG == 1) {
                            res = "YOU WIN!";
                            GameLauncher.getUserDAO().updateWin(GameLauncher.getUser().getUserID());
                        }
                        else if (myValue == 0 && oppG == 0 ||
                                myValue == 1 && oppG == 1 ||
                                myValue == 2 && oppG == 2)
                            res = "DRAW!";
                        else {
                            res = "YOU LOSE";
                            GameLauncher.getUserDAO().updateLose(GameLauncher.getUser().getUserID());
                        }

                        //result.output(btnSrc.getIcon(), imgIcons[Integer.parseInt(opp)], res);
                        SendCheck=false;
                        ReceiveCheck=false;
                        JOptionPane.showMessageDialog(null,res);
                    }
                }
            }
        }; thread.start();

    }

    class EventHandler implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            //getSource가 Object 타입임으로 타입변환을 해야한다.
            String a;
            JButton btnSrc = (JButton)e.getSource();

            String res = "";
            SendCheck= true;

            if(btnSrc.getIcon() == imgIcons[0]){
                myValue=0;
                a="0";
                GameLauncher.getClient().send("\\G"+GameLauncher.getUserDAO().getNickname()+","+GameLauncher.getInvitation().getOppNick()+","+a);
            }
            else if(btnSrc.getIcon() == imgIcons[1]){
                myValue=1;
                a="1";
                GameLauncher.getClient().send("\\G"+GameLauncher.getUserDAO().getNickname()+","+GameLauncher.getInvitation().getOppNick()+","+a);
            }
            else if(btnSrc.getIcon() == imgIcons[2]){
                myValue=2;
                a="2";
                GameLauncher.getClient().send("\\G"+GameLauncher.getUserDAO().getNickname()+","+GameLauncher.getInvitation().getOppNick()+","+a);
            }

        }

    }

    public boolean isSendCheck() {
        return SendCheck;
    }

    public void setSendCheck(boolean sendCheck) {
        SendCheck = sendCheck;
    }

    public boolean isReceiveCheck() {
        return ReceiveCheck;
    }

    public void setReceiveCheck(boolean receiveCheck) {
        ReceiveCheck = receiveCheck;
    }

    public String getMyNick() {
        return myNick;
    }

    public void setMyNick(String myNick) {
        this.myNick = myNick;
    }

    public int getOppG() {
        return oppG;
    }

    public void setOppG(int oppG) {
        this.oppG = oppG;
    }

    public void setOppNick(String oppNick) {
        this.oppNick = oppNick;
    }

}