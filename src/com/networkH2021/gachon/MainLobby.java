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
import java.util.ArrayList;

public class MainLobby extends JFrame{

    private JTextField ChatTextField;//보낼채팅들어가는필드임
    private JButton Rankbutton;//랭크버튼임
    private JButton Sendbutton;//채팅 보내기 버튼임
    private JPanel panelLobby;////유저리스트의 상위 파넬
    private JList<GameUser> UserList;//유저리스트가 나옴
    DefaultListModel<GameUser> model = new DefaultListModel<>();
    private JTextArea ChatTextArea;//채팅택스트
    private JButton Exit;
    private JLabel ChatRoom;
    private JScrollPane scrollPane;

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

    //private DatagramSocket socket;//User Datagram Protocol
    //private DatagramPacket packet;//Data packet
    //private InetAddress address;//상대방주소
    private final int myPort = 10001;//내 포트
    private final int oppPort = 10002;//상대 포트

    public void startChatting() {
        CCA = new ChatClientApp(this);
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

    public void showGameInvite(String opponent){
        GameLauncher.getInvitation().getInviteLabel().setText(opponent+"이(가) 대전을 요청했습니다!");
        GameLauncher.getInvitation().setVisible(true);
    }
    
    // 게임방
    public void showGameRoom(){
        GameLauncher.getMainLobby().setVisible(false);
        GameLauncher.getGame().setVisible(true);  // 게임방으로 만들 거임
        GameLauncher.getGame().gameInfo();
    }



    private class JListHandler implements ListSelectionListener
    {
        // 리스트의 항목이 선택이 되면
        public void valueChanged(ListSelectionEvent event)
        {
            GameUser gameUser = UserList.getSelectedValue();
            JOptionPane.showMessageDialog(null, gameUser.getUserNickname());
        }
    }


    public void refreshList(String[] userList){
        model.clear();
        for (int i =0; i < userList.length; i++){
            model.addElement(new GameUser("test1",userList[i]));
            ChatTextArea.setCaretPosition(ChatTextArea.getDocument().getLength());
        }
    }

    public MainLobby() {
        final JPopupMenu menu = new JPopupMenu("Menu");//유저리스트에서 오른쪽마우스하면 뜨는 ContextMenu구현임
        UserList.setModel(model);
        JMenuItem info = new JMenuItem("info");
        JMenuItem invite = new JMenuItem("invite");
        ChatRoom.setText("Chat Room");

        menu.add(info);
        menu.add(invite);
        setVisible(true);

        //ChatTextArea.add(scrollPane);
        //scrollPane.setVisible(true);

        setTitle("RSP Online Main Lobby");//Frame시작
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
                ChatTextArea.setCaretPosition(ChatTextArea.getDocument().getLength());
            }
        });


        UserList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (SwingUtilities.isRightMouseButton(e) && e.getClickCount()==1){
                    menu.show(UserList, e.getX(), e.getY());
                    JList list = (JList)e.getSource();
                    int row = list.locationToIndex(e.getPoint());
                    list.setSelectedIndex(row);
//                    JList<GameUser> selected = (JList<GameUser>) e.getSource();
//                    int index = selected.locationToIndex(e.getPoint());
//                    System.out.println(index);
                }
            }
        });
        info.addActionListener(new AbstractAction("info") {
            public void actionPerformed(ActionEvent e) {
                showUserInfo(UserList.getSelectedValue());
            }
        });


        invite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "INVITE :"+UserList.getSelectedValue());
                String username= String.valueOf(UserList.getSelectedValue());
                GameLauncher.getClient().send(username);
                GameLauncher.getInvitation().setOppNick(username);
                GameLauncher.getClient().send(username);
                GameLauncher.getClient().send("\\n");
                GameLauncher.getClient().send("\\i"+GameLauncher.getUserDAO().getNickname()+","+username);

                //게임시작 코드를 추가해야함 넘겨주는값
                //상대방한태 초대받았다고 알려주는 코드 추가해야함
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