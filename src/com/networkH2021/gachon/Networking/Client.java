package com.networkH2021.gachon.Networking;

import com.networkH2021.gachon.GameLauncher;

import javax.management.remote.JMXServerErrorException;
import javax.swing.*;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class Client {
    private DatagramSocket socket;
    private InetAddress serverAddress;
    private int port;
    private boolean online;
    private int clientID;

    private String lastMessage;

    public Client(String address, int port){
        try{
            this.serverAddress = InetAddress.getByName(address);
            this.port = port;
            socket = new DatagramSocket();

            online = true;
            send("\\c"+ GameLauncher.getUserDAO().getNickname() + "," + GameLauncher.getUser().getUserID());
            System.out.println(GameLauncher.getUser().getUserID());  ////////////////////// 확인
            receive();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

    public void receive(){
        Thread thread = new Thread(){
            public void run(){
                while(online) {
                    try {
                        byte[] rawData = new byte[1024];
                        DatagramPacket packet = new DatagramPacket(rawData, rawData.length);
                        socket.receive(packet);

                        String message = new String(rawData);
                        message = message.substring(0, message.indexOf("\\e"));//end of line

                        if (!parseCommand(message)) {
                            lastMessage = message;
                        }

                        lastMessage = message;
                        System.out.println(lastMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }; thread.start();
    }

    public void send(String message){
        try {
            message = message + "\\e";
            byte[] data = message.getBytes(StandardCharsets.UTF_8);
            DatagramPacket packet = new DatagramPacket(data, data.length,serverAddress, port);
            socket.send(packet);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public boolean parseCommand(String message){
        if(message.startsWith("\\cid:")){
            this.clientID = Integer.parseInt(message.substring(5));
            return true;
        }
        if (message.startsWith("\\l")){
            String[] userList = message.substring(2).split(",");
            GameLauncher.getMainLobby().refreshList(userList);
            return true;
        }
        if (message.startsWith("\\i")){
            String myNick = GameLauncher.getUserDAO().getNickname();
            String oppoNick = message.substring(2);

            GameLauncher.getInvitation().setOppNick(oppoNick);
            GameLauncher.getMainLobby().showGameInvite(oppoNick);
            return true;
        }

        // 승락 했다고 했을 때
        if (message.startsWith("\\y")){
            GameLauncher.getMainLobby().showGameRoom();
            return true;
        }


        // 거절 했다고 했을 때
        if (message.startsWith("\\n")){
            String myNick;
            String oppoNick;
            ClientObject opponent = null;
            message = message.substring(2);
            myNick = message.split(",")[0];
            oppoNick = message.split(",")[1];
            JOptionPane.showMessageDialog(null, myNick+"이(가) 대전을 거절했습니다.");
            GameLauncher.getInvitation().setVisible(false);
            return true;
        }

        if (message.startsWith("\\g")){
            int opp;
            String myNick;
            String oppoNick;
            ClientObject opponent = null;
            message = message.substring(2);
            myNick = message.split(",")[0];
            oppoNick = message.split(",")[1];
            opp = Integer.parseInt(message.split(",")[2]);
            GameLauncher.getGame().setMyNick(myNick);
            GameLauncher.getGame().setOppNick(oppoNick);
            GameLauncher.getGame().setOppG(opp);
            GameLauncher.getGame().setReceiveCheck(true);
            if (opp == 3){
                GameLauncher.getMainLobby().setVisible(true);
                GameLauncher.getGame().setVisible(false);
                GameLauncher.getGame().getChatBox().setText("");
                if (GameLauncher.getGame().getReadyCheck() == true){
                    GameLauncher.getGame().append("상대방이 탈주하여 자동 승리 처리됩니다.\n");
                    GameLauncher.getUserDAO().updateWin(GameLauncher.getUser().getUserID());
                }
                JOptionPane.showMessageDialog(null,"상대방이 나갔습니다.");
            }
            System.out.println("Received");
            return true;
        }
        if (message.equals("\\concheck")){
            send("\\conalive:"+clientID);
            return true;
        }

        if (message.startsWith("\\t")){
            String myNick;
            String oppoNick;
            String text;
            ClientObject opponent = null;
            message = message.substring(2);
            myNick = message.split(",")[0];
            oppoNick = message.split(",")[1];
            text = message.split(",")[2];
            text = myNick+":"+text+"\n";
            GameLauncher.getGame().append(text);
            return true;
        }
        //상대방이 준비 버튼을 눌렀을 때
        if (message.startsWith("\\r")){
            String myNick;
            String oppoNick;
            String text;
            ClientObject opponent = null;
            message = message.substring(2);
            myNick = message.split(",")[0];
            oppoNick = message.split(",")[1];
            GameLauncher.getGame().setMyNick(myNick);
            GameLauncher.getGame().setOppNick(oppoNick);
            GameLauncher.getGame().setReadyCheck(true);
            GameLauncher.getGame().append("상대방이 준비를 완료했습니다.\n");
            JOptionPane.showMessageDialog(null, myNick+"이(가) 준비를 완료했습니다.");
            return true;
        }

        return false;
    }

    public int getClientID() {
        return clientID;
    }

    public String getMessage() {
        String message = lastMessage;
        lastMessage = null;
        return message;
    }
}