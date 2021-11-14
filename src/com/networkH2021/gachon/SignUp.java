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

    private String name;
    private String id;
    private String email;
    private char[] password;
    private char[] password2;


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
                password = passwordField.getPassword();
                password2 = CpasswordField.getPassword();
                /*if (!password.equals(password2)){
                    JOptionPane.showMessageDialog(null,"비밀번호를 다시 입력해주세요");
                }
                else{
                    JOptionPane.showMessageDialog(null,"계정이 생성되었습니다.");
                }*/
                //User = new Person(name,id,password,email,0);
                setVisible(false);
                GameLauncher.getLogin().setVisible(true);
            }
        });
    }

}
