package com.networkH2021.gachon;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

        private boolean SendCheck;
        private boolean ReceiveCheck;
        private int myValue;
        private String myNick;
        private int oppG;
        private String oppNick;


        ImageIcon[] imgIcons = {
                new ImageIcon("IMG/kawi.jpg"),
                new ImageIcon("IMG/bawi.jpg"),
                new ImageIcon("IMG/bo.jpg"),
                new ImageIcon("IMG/mainlobby.jpg")

        };

        GameBottom select = new Ingame.GameBottom();
        GameTop result = new Ingame.GameTop();

        class GameBottom extends JPanel{
                JButton[] btnButtons = new JButton[4];

                public GameBottom() {
                        setBackground(Color.gray);

                        for(int i=0; i<imgIcons.length; i++) {
                                btnButtons[i] = new JButton(imgIcons[i]);
                                this.add(btnButtons[i]);

                                btnButtons[i].addActionListener(new Ingame.EventHandler());
                        }
                }
        }

        class GameTop extends JPanel{
                JLabel userJLabel = new JLabel("user1");
                JLabel comJLabel = new JLabel("user2");
                JLabel resultJLabel = new JLabel("winner");

                public GameTop() {
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
                        else if(btnSrc.getIcon() == imgIcons[3]){
                                myValue=3;
                                a="3";
                                GameLauncher.getClient().send("\\G"+GameLauncher.getUserDAO().getNickname()+","+GameLauncher.getInvitation().getOppNick()+","+a);
                                setVisible(false);
                                GameLauncher.getMainLobby().setVisible(true);
                                //로비로 이동
                        }
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
//                    System.out.println("OnThread");
                                        //System.out.println("sendCheck:"+SendCheck);
                                        //System.out.println("receiveCheck"+ReceiveCheck);
                                        if ((SendCheck == true) && (ReceiveCheck == true)) {
                                                System.out.println("Both are true!!");
                                                if(oppG==3){
                                                        res = "The other user left";
                                                        JOptionPane.showMessageDialog(null,res);
                                                        setVisible(false);
                                                        GameLauncher.getMainLobby().setVisible(true);
                                                }
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
                                                else if((myValue == 2 && oppG == 0 ||
                                                        myValue == 0 && oppG == 1 ||
                                                        myValue == 1 && oppG == 2)){
                                                        res = "YOU LOSE";
                                                        GameLauncher.getUserDAO().updateLose(GameLauncher.getUser().getUserID());
                                                }

                                                //result.output(btnSrc.getIcon(), imgIcons[Integer.parseInt(opp)], res);
                                                SendCheck=false;
                                                ReceiveCheck=false;
                                                JOptionPane.showMessageDialog(null,res);
                                                System.out.println("success");
                                        }
                                }
                        }
                }; thread.start();

        }

        public Ingame() {

                SendCheck=false;
                ReceiveCheck=false;
                receive();

                setDefaultCloseOperation(EXIT_ON_CLOSE);

                GameTop.add(result,"Center");
                GameBottom.add(select, "South");

                // 기본 설정
                setTitle("RSP online");
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setContentPane(this.panelIngame);
                pack();
                setLocationRelativeTo(null);
                setVisible(false);

                
                // 전적 라벨
                GameLauncher.getUserDAO().info(GameLauncher.getUserDAO().getNickname());
                String me = GameLauncher.getUserDAO().getInfo();
                GameLauncher.getUserDAO().info(GameLauncher.getInvitation().getOppNick());
                String opp = GameLauncher.getUserDAO().getInfo();

                MYINFO.setText(me);
                OPPINFO.setText(opp);
                

                // 액션 리스너

                ROCK.addActionListener(new ActionListener() { // 나기기 버튼
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                // 서버로 메시지 전송 필요, 대결 유저도 대기실로 나가게
                                GameLauncher.getClient().send("\\y"+GameLauncher.getUserDAO().getNickname());       /// 수정 필요
                                GameLauncher.getExit().setVisible(true);
                        }
                });

                SCISSOR.addActionListener(new ActionListener() { // 나기기 버튼
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                // 서버로 메시지 전송 필요, 대결 유저도 대기실로 나가게
                                GameLauncher.getClient().send("\\y"+GameLauncher.getUserDAO().getNickname());       /// 수정 필요
                                GameLauncher.getExit().setVisible(true);
                        }
                });
                PAPER.addActionListener(new ActionListener() { // 나기기 버튼
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                // 서버로 메시지 전송 필요, 대결 유저도 대기실로 나가게
                                GameLauncher.getClient().send("\\y"+GameLauncher.getUserDAO().getNickname());       /// 수정 필요
                                GameLauncher.getExit().setVisible(true);
                        }
                });


                // 하단 버튼

                EXITButton.addActionListener(new ActionListener() { // 나기기 버튼
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                // 서버로 메시지 전송 필요, 대결 유저도 대기실로 나가게
                                GameLauncher.getClient().send("\\y"+GameLauncher.getUserDAO().getNickname());       /// 수정 필요
                                GameLauncher.getExit().setVisible(true);
                        }
                });

                ROBBYButton.addActionListener(new ActionListener() { // 로비 버튼
                        @Override
                        public void actionPerformed(ActionEvent e) {
                                
                                // 서버로 메시지 전송 필요, 대결 유저도 대기실로 나가게
                                GameLauncher.getClient().send("\\y"+GameLauncher.getUserDAO().getNickname());       /// 수정 필요
                                GameLauncher.getMainLobby().setVisible(true);
                                setVisible(false);
                        }
                });
                READYButton.addActionListener(new ActionListener() { // 레디버튼
                        @Override
                        public void actionPerformed(ActionEvent e) {

                                // 레디버튼 누르면 서버로 전송 필요

                        }
                });

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
