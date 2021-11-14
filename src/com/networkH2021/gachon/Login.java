package com.networkH2021.gachon;

import javax.swing.*;
import java.awt.desktop.ScreenSleepListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.UnknownHostException;

import com.networkH2021.gachon.GameLauncher;

public class Login extends JFrame{
    private JLabel ID;
    private JLabel Password;
    private JPasswordField PW_INPUT;
    private JButton LoginButton;
    private JTextField ID_INPUT;
    public JPanel panelLogin;
    private JButton SignUpbutton;
    private JLabel SignUpText;

    private String ID_Str;
    private String PW_Str;
    private MyCharacter user = GameLauncher.getUser();

    public Login() {
        setTitle("RSP Online");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(panelLogin);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        LoginButton.addActionListener(new ActionListener() {//로그인 버튼을 누르면 실행됨
            @Override
            public void actionPerformed(ActionEvent e) {
                String ID_Str = ID_INPUT.getText();
                String PW_Str = String.valueOf(PW_INPUT.getPassword());
                JOptionPane.showMessageDialog(null,"Welcome to RSP Online!!");//다이얼로그 출력
                setVisible(false);
                GameLauncher.MakeMainLobby();
                GameLauncher.getMainLobby().setVisible(true);

                // 데이터 베이스
                int i = GameLauncher.getUserDAO().login(ID_Str, PW_Str);
                if(i == 0);  // 로그인 성공
                else if(i == 1){    //
                    JOptionPane.showMessageDialog(null,"ID is wrong!");
                }
                else{
                    JOptionPane.showMessageDialog(null,"Password is wrong!");
                }


                // 캐릭터 생성
                if(GameLauncher.getUserDAO().createUser(ID_Str) == 0) { // 성공

                    String nick = GameLauncher.getUserDAO().getNickname();
                    GameLauncher.getUser().setUserID(ID_Str);
                    GameLauncher.getUser().setUserNickname(nick);
                };
                // else 실패시 

            }
        });
        SignUpbutton.addActionListener(new ActionListener() {//SignUp버튼 눌렀을때 실행됨
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                GameLauncher.getSignUp().setVisible(true);
            }
        });
    }

}
