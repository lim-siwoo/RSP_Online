package com.networkH2021.gachon;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.networkH2021.gachon.user.GameUser;

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
    private GameUser user = GameLauncher.getUser();

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

                // 데이터 베이스
                int i = GameLauncher.database.login(ID_Str, PW_Str);
                System.out.println(i);
                if(i == 0){
                    JOptionPane.showMessageDialog(null,"Welcome to RSP Online!!");//다이얼로그 출력
                    setVisible(false);
                    GameLauncher.MakeMainLobby();
                    GameLauncher.getMainLobby().setVisible(true);
                }  // 로그인 성공
                else {
                    JOptionPane.showMessageDialog(null,"아이디와와 비번을 다시 입력해주세요!");
                }

                // 로그인한 정보로 객체 생성
                if(GameLauncher.getUserDAO().createUser(ID_Str) == 0) { // 성공

                    String nick = GameLauncher.getUserDAO().getNickname();
                    GameLauncher.getUser().setUserID(ID_Str);
                    GameLauncher.getUser().setUserNickname(nick);
                };
                // else 실패시

            }
        });
        SignUpbutton.addActionListener(new ActionListener() { // SignUp버튼 눌렀을때 실행됨
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                GameLauncher.getSignUp().setVisible(true);
            }
        });
    }

}
