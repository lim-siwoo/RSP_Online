package com.networkH2021.gachon;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Login {
    private JLabel ID;
    private JLabel Password;
    private JPasswordField PW_INPUT;
    private JButton LoginButton;
    private JTextField ID_INPUT;
    public JPanel panelLogin;
    private JButton Cratebutton;
    private String ID_tmp;


    public Login() {
        LoginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null,"Helloworld!");
            }
        });
    }
}
