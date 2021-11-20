package com.networkH2021.gachon;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.networkH2021.gachon.GameLauncher;

public class SignUp extends JFrame{

    private JButton OKButton;
    private JPanel panelSignUp;
    private JTextField EmailField;
    private JTextField IDField;
    private JPasswordField passwordField;
    private JPasswordField CpasswordField;
    private JTextField NameField;
    private JTextField textField1;
    private JTextField textField2;

    private String name;
    private String id;
    private String email;
    private String password;
    private String password2;
    private String SNS;
    private String NickName;


    public SignUp() {
        setTitle("RSP Online");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(this.panelSignUp);
        pack();
        setLocationRelativeTo(null);
        setVisible(false);

        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                name = NameField.getName();
                id = IDField.getText();
                password = String.valueOf(passwordField.getPassword());
                password2 = String.valueOf(CpasswordField.getPassword());
                SNS = textField1.getText();
                NickName = textField2.getText();

                if (!password.equals(password2)){
                    JOptionPane.showMessageDialog(null,"비밀번호를 다시 입력해주세요");
                }
                else{
                    JOptionPane.showMessageDialog(null,"계정이 생성되었습니다.");
                }


                // 아래 세 줄을 else 안에 넣어야 함
                setVisible(false);
                GameLauncher.getLogin().setVisible(true);
            }
        });
    }

}
