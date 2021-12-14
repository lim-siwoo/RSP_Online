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
        private JPanel GamePanel;


        public Ingame() {

                
                // 기본 설정
                setTitle("RSP online");
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                setContentPane(this.panelIngame);
                setContentPane(this.GamePanel);
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

}
