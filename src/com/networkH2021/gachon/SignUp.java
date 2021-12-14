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
    private JButton BACKButton;

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

                name = NameField.getText();
                id = IDField.getText();
                password = String.valueOf(passwordField.getPassword());
                password2 = String.valueOf(CpasswordField.getPassword());
                email = EmailField.getText();
                SNS = textField1.getText();
                NickName = textField2.getText();

                if (!password.equals(password2)){
                    JOptionPane.showMessageDialog(null,"비밀번호를 다시 입력해주세요");
                }
                else {  // 비밀번호는 맞음
                    int i = GameLauncher.database.join(id, password, name, NickName, email, SNS);
                    System.out.println(i);
                    if (i == 0) {   // 계정 정상 생성
                        setVisible(false);
                        JOptionPane.showMessageDialog(null, "계정이 생성되었습니다.");
                        GameLauncher.getLogin().setVisible(true);
                    }else if(i == -1){  // ID 같은지 확인해야 함
                        JOptionPane.showMessageDialog(null, "중복된 아이디 입니다. 다른 아이디를 입력해주세요!");
                    }
                    else {  // i == 2
                        System.out.println("Error: Database Error");
                    }
                }
            }
        });



        BACKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                GameLauncher.getLogin().setVisible(true);
                setVisible(false);
            }
        });




    }

}
