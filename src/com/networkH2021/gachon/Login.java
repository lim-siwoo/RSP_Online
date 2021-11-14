package com.networkH2021.gachon;

import javax.swing.*;
import java.awt.desktop.ScreenSleepListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
                char[] PW_Str = PW_INPUT.getPassword();
                JOptionPane.showMessageDialog(null,"Welcome to RSP Online!!");//다이얼로그 출력
                setVisible(false);
                GameLauncher.MakeMainLobby();
                GameLauncher.getMainLobby().setVisible(true);
                //데이터베이스에서 값을 가져와야함
                //user.setName("GachonKim");
                //user.setEmail("Hello@gachon.ac.kr");
                //user.setScore(100);
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
