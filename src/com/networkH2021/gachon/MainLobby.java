package com.networkH2021.gachon;

import com.networkH2021.gachon.client.ChatClientApp;
import com.networkH2021.gachon.user.GameUser;
import com.networkH2021.gachon.user.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainLobby extends JFrame{

    private JTextField ChatTextField;//보낼채팅들어가는필드임
    private JButton Rankbutton;//랭크버튼임
    private JButton Sendbutton;//채팅 보내기 버튼임
    private JPanel panelLobby;////유저리스트의 상위 파넬
    private JList<GameUser> UserList;//유저리스트가 나옴
    DefaultListModel<GameUser> model = new DefaultListModel<>();
    private JTextArea ChatTextArea;//채팅택스트

    public JTextField getChatTextField() {
        return ChatTextField;
    }

    public void setChatTextField(JTextField chatTextField) {
        ChatTextField = chatTextField;
    }

    public JTextArea getChatTextArea() {
        return ChatTextArea;
    }

    public void setChatTextArea(JTextArea chatTextArea) {
        ChatTextArea = chatTextArea;
    }

    public JButton getSendbutton() {
        return Sendbutton;
    }

    public void setSendbutton(JButton sendbutton) {
        Sendbutton = sendbutton;
    }

    private Socket socket;
    private String name;

    private PrintWriter pw;
    private BufferedReader br;
    private String totalUser;
    private static ChatClientApp CCA;

    //private DatagramSocket socket;//User Datagram Protocal
    //private DatagramPacket packet;//Data packet
    //private InetAddress address;//상대방주소
    private int myPort = 10001;//내 포트
    private int oppPort = 10002;//상대 포트

    public void startChatting() {
        CCA = new ChatClientApp(this);
    }

    public MainLobby() {
        final JPopupMenu menu = new JPopupMenu("Menu");//유저리스트에서 오른쪽마우스하면 뜨는 ContextMenu구현임
        UserList.setModel(model);
        model.addElement(new GameUser("test1","홍길동"));
        model.addElement(new GameUser("test2","최재혁 교수님"));
        model.addElement(new GameUser("test3","허수아비"));
        JMenuItem info = new JMenuItem("info");
        JMenuItem invite = new JMenuItem("invite");

        menu.add(info);
        menu.add(invite);
        setVisible(true);


        setTitle("RSP Online Main Lobby");//Frame시작
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(this.panelLobby);
        pack();
        setLocationRelativeTo(null);
        setVisible(false);


        Rankbutton.addActionListener(new ActionListener() { //Rank버튼 눌렀을때 실행됨
            @Override
            public void actionPerformed(ActionEvent e) {
                GameLauncher.getRank().setVisible(true);
            }
        });


        UserList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (SwingUtilities.isRightMouseButton(e) && e.getClickCount()==1){
                    menu.show(UserList, e.getX(), e.getY());
                    JList<GameUser> selected = (JList<GameUser>) e.getSource();
                    int index = selected.locationToIndex(e.getPoint());
                    System.out.println(index);
                }
            }
        });
        menu.addMouseListener(new MouseAdapter() {//메뉴에서 뭐가 선택됨
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
            }
        });
    }
}
