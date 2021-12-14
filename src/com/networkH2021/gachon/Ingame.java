package com.networkH2021.gachon;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Ingame extends  JFrame{

        private JButton READYButton;
        private JTextField TypingBox;
        private JButton SENDButton;
        private JTextPane ChatBox;
        private JPanel panelIngame;
        private JButton EXITButton;
        private JButton ROBBYButton;
        private JLabel MYINFO;
        private JLabel OPPINFO;
        private JPanel GameTop;
        private JPanel GameBottom;
        private JButton SCISSOR;
        private JButton ROCK;
        private JButton PAPER;
        private JButton MYBOTTON;
        private JButton OPPBOTTON;
        private JPanel ChoosePanel;
        private JPanel TextPanel;
        //private JScrollPane scrollPane = new JScrollPane(ChatBox);

        private boolean SendCheck;
        private boolean ReceiveCheck;
        private boolean ReadyCheck;

        private int myValue;
        private String myNick;
        private int oppG;
        private String oppNick;
        private String oppReady;


        ImageIcon[] imgIcons = {
                new ImageIcon("IMG/kawi.jpg"),
                new ImageIcon("IMG/bawi.jpg"),
                new ImageIcon("IMG/bo.jpg"),
                new ImageIcon("IMG/mainlobby.jpg")

        };

        public String getOppNick() {
                return oppNick;
        }
        public String getOppReady() {
                return oppReady;
        }
        public void receive() {
                Thread thread = new Thread() {
                        public void run() {
                                while (true) {
                                        String res = "";
//                    System.out.println("OnThread");
                                        System.out.println("sendCheck:"+SendCheck);
                                        System.out.println("receiveCheck"+ReceiveCheck);
                                        if ((SendCheck == true) && (ReceiveCheck == true)&&(ReadyCheck == true)) {
                                                OPPBOTTON.setIcon(imgIcons[oppG]);
                                                if(oppG==3){
                                                        res = "The other user left";
                                                        JOptionPane.showMessageDialog(null,res);
                                                        setVisible(false);
                                                        GameLauncher.getMainLobby().setVisible(true);
                                                }
                                                if (((myValue == 0) && (oppG == 2)) ||
                                                        ((myValue == 1) && (oppG == 0)) ||
                                                        ((myValue == 2) && (oppG == 1))) {
                                                        res = "YOU WIN!";
                                                        GameLauncher.getUserDAO().updateWin(GameLauncher.getUser().getUserID());
                                                }

                                                else if (((myValue == 0) && (oppG == 0)) ||
                                                        ((myValue == 1) && (oppG == 1)) ||
                                                        ((myValue == 2) && (oppG == 2)))
                                                        res = "DRAW!";

                                                else if(((myValue == 2) && (oppG == 0)) ||
                                                        ((myValue == 0) && (oppG == 1)) ||
                                                        ((myValue == 1) && (oppG == 2))){
                                                        res = "YOU LOSE";
                                                        GameLauncher.getUserDAO().updateLose(GameLauncher.getUser().getUserID());
                                                }

                                                //result.output(btnSrc.getIcon(), imgIcons[Integer.parseInt(opp)], res);
                                                SendCheck=false;
                                                ReceiveCheck=false;
                                                ReadyCheck=false;

                                                JOptionPane.showMessageDialog(null,res);
                                                gameInfo();
                                        }
                                }
                        }
                }; thread.start();

        }

        public void gameInfo(){

                // 전적 라벨
                GameLauncher.getUserDAO().info(GameLauncher.getUserDAO().getNickname());
                String me = GameLauncher.getUserDAO().getInfo();
                GameLauncher.getUserDAO().info(GameLauncher.getInvitation().getOppNick());
                String opp = GameLauncher.getUserDAO().getInfo();

                MYINFO.setText(me);
                OPPINFO.setText(opp);
        }



        public Ingame() {


                SCISSOR.setIcon(imgIcons[0]);
                ROCK.setIcon(imgIcons[1]);
                PAPER.setIcon(imgIcons[2]);
                MYBOTTON.setIcon(imgIcons[0]);
                OPPBOTTON.setIcon(imgIcons[0]);

                SendCheck=false;
                ReceiveCheck=false;
                setDefaultCloseOperation(EXIT_ON_CLOSE);

                // 기본 설정
                setTitle("RSP online");
                setContentPane(this.panelIngame);
                pack();
                setLocationRelativeTo(null);
                setVisible(false);

                receive();



                SCISSOR.addActionListener(new ActionListener() { // 나기기 버튼
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                SendCheck= true;
                                myValue =0;
                                GameLauncher.getClient().send("\\G"+GameLauncher.getUserDAO().getNickname()+","+GameLauncher.getInvitation().getOppNick()+",0");
                                MYBOTTON.setIcon(imgIcons[0]);
                        }
                });

                ROCK.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                SendCheck= true;
                                myValue =1;
                                GameLauncher.getClient().send("\\G"+GameLauncher.getUserDAO().getNickname()+","+GameLauncher.getInvitation().getOppNick()+",1");
                                MYBOTTON.setIcon(imgIcons[1]);
                        }
                });

                PAPER.addActionListener(new ActionListener() { // 나기기 버튼
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                SendCheck= true;
                                myValue =2;
                                GameLauncher.getClient().send("\\G"+GameLauncher.getUserDAO().getNickname()+","+GameLauncher.getInvitation().getOppNick()+",2");
                                MYBOTTON.setIcon(imgIcons[2]);
                        }
                });


                // 하단 버튼

                EXITButton.addActionListener(new ActionListener() { // 나기기 버튼
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                // 서버로 메시지 전송 필요, 대결 유저도 대기실로 나가게
                                GameLauncher.getExit().setVisible(true);
                        }
                });

                ROBBYButton.addActionListener(new ActionListener() { // 로비 버튼
                        @Override
                        public void actionPerformed(ActionEvent e) {

                                // 서버로 메시지 전송 필요, 대결 유저도 대기실로 나가게
                                GameLauncher.getClient().send("\\G"+GameLauncher.getUserDAO().getNickname()+","+GameLauncher.getInvitation().getOppNick()+","+3);
                                GameLauncher.getMainLobby().setVisible(true);
                                setVisible(false);
                        }
                });
                READYButton.addActionListener(new ActionListener() { // 레디버튼
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                GameLauncher.getClient().send("\\r"+GameLauncher.getUserDAO().getNickname()+","+GameLauncher.getInvitation().getOppNick());
                                // 레디버튼 누르면 서버로 전송 필요

                        }
                });
                SENDButton.setBackground(Color.GRAY);
                SENDButton.setForeground(Color.WHITE);
                SENDButton.addActionListener(new ActionListener() {//전송
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                sendMessage();
                        }
                });
                TypingBox.addKeyListener(new KeyAdapter() {
                        @Override
                        public void keyPressed(KeyEvent e) {
                                super.keyPressed(e);
                                char keyCode = e.getKeyChar();
                                if (keyCode == KeyEvent.VK_ENTER) {
                                        sendMessage();
                                }
                        }
                });

        }
        public void append(String text){
                try {
                        Document doc = ChatBox.getDocument();
                        doc.insertString(doc.getLength(), text, null);
                        ChatBox.setCaretPosition(ChatBox.getDocument().getLength());
                } catch(BadLocationException exc) {
                        exc.printStackTrace();
                }
        }
        private void sendMessage(){
                String message = TypingBox.getText();
                append(message);
                ChatBox.setCaretPosition(ChatBox.getDocument().getLength());
                GameLauncher.getClient().send("\\t"+GameLauncher.getUserDAO().getNickname()+","+GameLauncher.getInvitation().getOppNick()+","+message);
                TypingBox.setText("");
                TypingBox.requestFocus();
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

        public boolean isReadyCheck() {
                return SendCheck;
        }

        public void setReadyCheck(boolean sendCheck) {
                SendCheck = sendCheck;
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
