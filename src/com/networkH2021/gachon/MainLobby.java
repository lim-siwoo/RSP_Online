package com.networkH2021.gachon;

import com.networkH2021.gachon.client.ChatClientApp;
import com.networkH2021.gachon.user.GameUser;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class MainLobby extends JFrame{

    private JTextField chatTextField;//보낼채팅들어가는필드임
    private JButton Rankbutton;//랭크버튼임
    private JButton sendbutton;//채팅 보내기 버튼임
    private JPanel panelLobby;////유저리스트의 상위 파넬
    private JList<GameUser> userList;//유저리스트가 나옴
    DefaultListModel<GameUser> model = new DefaultListModel<>();
    private JTextArea chatTextArea;//채팅택스트
    private JButton Exit;//게임종료버튼
    private JLabel ChatRoom;
    private JScrollPane scrollPane;

    public JTextField getChatTextField() {
        return chatTextField;
    }

    public void setChatTextField(JTextField chatTextField) {
        this.chatTextField = chatTextField;
    }

    public JTextArea getChatTextArea() {
        return chatTextArea;
    }

    public void setChatTextArea(JTextArea chatTextArea) {
        this.chatTextArea = chatTextArea;
    }

    public JButton getSendbutton() {
        return sendbutton;
    }

    public void setSendbutton(JButton sendbutton) {
        this.sendbutton = sendbutton;
    }

    private static ChatClientApp CCA;

    public void startChatting() {
        CCA = new ChatClientApp();
    }

    public void showUserInfo(GameUser gameUser){
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        frame.setBounds(this.getX()+this.getWidth()/2 - this.getWidth()/4,this.getY()+this.getHeight()/2 - this.getHeight()/4,this.getWidth()/2,this.getHeight()/2);

        GameLauncher.getUserDAO().info(gameUser.getUserNickname());
        String info = GameLauncher.getUserDAO().getInfo();

        panel.setLayout(null);
        JLabel userinfo = new JLabel(info);

        String sen = "NICK   WIN   LOSE";
        JLabel sentence = new JLabel(sen);

        sentence.setHorizontalAlignment(JLabel.CENTER);
        sentence.setVerticalAlignment(JLabel.CENTER);

        userinfo.setHorizontalAlignment(JLabel.CENTER);
        userinfo.setVerticalAlignment(JLabel.CENTER);

        userinfo.setFont(new Font("맑은 고딕", Font.BOLD, 30));
        sentence.setFont(new Font("맑은 고딕", Font.BOLD, 30));

        frame.add(panel);
        panel.add(userinfo);
        panel.add(sentence);

        userinfo .setBounds(0, 0 ,400, 250);
        sentence .setBounds(0, 0 ,400, 160);
        frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public void showGameInvite(String opponent){//게임초대를 받음
        GameLauncher.getInvitation().getInviteLabel().setText(opponent+"이(가) 대전을 요청했습니다!");
        GameLauncher.getInvitation().setVisible(true);
    }
    
    // 게임방
    public void showGameRoom(){
        GameLauncher.getMainLobby().setVisible(false);
        GameLauncher.getGame().setVisible(true);  // 게임방으로 만들 거임
        GameLauncher.getGame().gameInfo();
    }



    public void refreshList(String[] userList){
        model.clear();
        for (int i =0; i < userList.length; i++){
            model.addElement(new GameUser("test1",userList[i]));
            chatTextArea.setCaretPosition(chatTextArea.getDocument().getLength());
        }
    }

    public MainLobby() {
        final JPopupMenu menu = new JPopupMenu("Menu");//유저리스트에서 오른쪽마우스하면 뜨는 ContextMenu구현임
        userList.setModel(model);
        JMenuItem info = new JMenuItem("info");
        JMenuItem invite = new JMenuItem("invite");
        ChatRoom.setText("Chat Room");

        menu.add(info);
        menu.add(invite);
        setVisible(true);


        setTitle("RSP Online Main Lobby");//Frame시작

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosing(e);
                GameLauncher.getClient().send("\\d"+GameLauncher.getClient().getClientID());
                GameLauncher.getClient().send("\\l");
                System.exit(0);
            }
        });
        setContentPane(this.panelLobby);
        pack();
        setLocationRelativeTo(null);
        setVisible(false);


        Rankbutton.addActionListener(new ActionListener() { //Rank버튼 눌렀을때 실행됨
            @Override
            public void actionPerformed(ActionEvent e) {
                GameLauncher.getUserDAO().rank();
                GameLauncher.getRank().update();
                GameLauncher.getRank().setVisible(true);
                chatTextArea.setCaretPosition(chatTextArea.getDocument().getLength());
            }
        });


        userList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {//유저리스트가 클릭됨
                super.mouseClicked(e);
                if (SwingUtilities.isRightMouseButton(e) && e.getClickCount()==1){
                    menu.show(userList, e.getX(), e.getY());
                    JList list = (JList)e.getSource();
                    int row = list.locationToIndex(e.getPoint());
                    list.setSelectedIndex(row);
                }
            }
        });
        info.addActionListener(new AbstractAction("info") {
            public void actionPerformed(ActionEvent e) {
                showUserInfo(userList.getSelectedValue());
            }
        });


        invite.addActionListener(new ActionListener() {//초대하기
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "INVITE :"+ userList.getSelectedValue());
                String username= String.valueOf(userList.getSelectedValue());
                GameLauncher.getClient().send(username);
                GameLauncher.getInvitation().setOppNick(username);
                GameLauncher.getClient().send(username);
                GameLauncher.getClient().send("\\i"+GameLauncher.getUserDAO().getNickname()+","+username);
                GameLauncher.getClient().send("\\l");
            }
        });

        Exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GameLauncher.getExit().setVisible(true);
            }
        });

    }
}