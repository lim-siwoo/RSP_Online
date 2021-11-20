package com.networkH2021.gachon;
import java.awt.Color;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameWaitingRoom extends JFrame{

        ImageIcon Icon = new ImageIcon("IMG/ready.jpg");
        ImageIcon Icon2 = new ImageIcon("IMG/RSC2.jpg");
          ScreenPanel sc = new ScreenPanel();
          ReadyPanel ready = new ReadyPanel();

        public GameWaitingRoom() {
            super("RSC");
            setDefaultCloseOperation(EXIT_ON_CLOSE);

            add(ready, "South");
            add(sc, "Center");

            setSize(600,600);
            setVisible(true);
        }

        class ReadyPanel extends JPanel{
        JButton rdButton = new JButton();

        public ReadyPanel() {
            setBackground(Color.white);
                rdButton = new JButton(Icon);
                this.add(rdButton);
        }
    }

    class ScreenPanel extends JPanel{
        JLabel myLabel = new JLabel();
        public ScreenPanel() {
            setBackground(Color.white);
            myLabel.setIcon(Icon2);
            this.add(myLabel);
        }
    }

    public static void main(String[] args) {
        new GameWaitingRoom();
    }
}