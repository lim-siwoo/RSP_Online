package com.networkH2021.gachon.Networking;

import com.mysql.cj.protocol.a.DebugBufferingPacketReader;
import com.networkH2021.gachon.user.GameUser;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Server {
    private DatagramSocket socket;
    private int port;
    private boolean online;


    private ArrayList<ClientObject> clients = new ArrayList<ClientObject>();
    private int clientID =999;
    private String lastMessage;

    public Server(int port){
        try{
            this.port = port;
            socket = new DatagramSocket(port);
            online = true;

            receive();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void receive(){
        Thread thread = new Thread(){
            public void run(){
                while(online) {
                    try {
                        byte[] rawData = new byte[2048];
                        DatagramPacket packet = new DatagramPacket(rawData, rawData.length);
                        socket.receive(packet);

                        String message = new String(rawData);
                        message = message.substring(0, message.indexOf("\\e"));//end of line
                        System.out.println("Client:" + message);
                        if (!parseCommand(message, packet)) {
                            lastMessage = message;
                        }
                        if (message.equalsIgnoreCase("ping")){
                            send("pong",packet.getAddress().getHostAddress(),packet.getPort());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }; thread.start();
    }

    public void send(String message, String ip, int port){//send to specific client
        try {
            message = message + "\\e";
            byte[] data = message.getBytes(StandardCharsets.UTF_8);
            DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName(ip), port);
            socket.send(packet);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateClient(String message){//send to every connected client
        for (int i=0;i<clients.size();i++){
            ClientObject client = clients.get(i);
            send(message, client.getAddress(),client.getPort());
        }
    }

    public boolean parseCommand(String message, DatagramPacket packet){
        if (message.startsWith("\\c")){//create client parse
            //CONNECT CLIENT TO SERVER
            String clientNick = message.substring(2).split(",")[0];
            System.out.println("Nick:"+clientNick);
            String userID = message.split(",")[1];
            //System.out.println(userID);
            ClientObject client = new ClientObject (packet.getAddress().getHostAddress(), packet.getPort(), clientID+1,clientNick, userID);
            clients.add(client);
            send("\\cid:"+client.getId(), client.getAddress(), client.getPort());
            clientID++;
            return true;
        }else
            if (message.startsWith("\\d")){//delete client parse
                int id = Integer.parseInt(message.substring(2));
                String nickname = message.substring(2);
                for (int i =0; i <clients.size();i++){
                    if (clients.get(i).getId() == id){
                        clients.remove(clients.get(i));
                        return true;
                    }
                }
//                client.send("\\d:"+clientID);
                System.out.println("Server: Error, Client With ID"+id+", was not found!");
                return true;
            }
            if (message.startsWith("\\l")){//send userlist
                String userList = "\\l";
                for (int i=0;i<clients.size();i++){
                    ClientObject client = clients.get(i);
                    //send(message, client.getAddress(),client.getPort());
                    userList = userList +client.getNickName()+",";
                }
                send(userList, packet.getAddress().getHostAddress(), packet.getPort());
                System.out.println("send: "+userList);
                return true;
            }
            if (message.startsWith("\\i")){
                String myNick;
                String oppoNick;
                ClientObject opponent = null;
                message = message.substring(2);
                myNick = message.split(",")[0];
                oppoNick = message.split(",")[1];
                for (int i=0;i<clients.size();i++){
                    if (clients.get(i).getNickName().equalsIgnoreCase(oppoNick)){
                        opponent = clients.get(i);
                    }
                }
                send("\\i"+myNick,opponent.getAddress(),opponent.getPort());
                return true;
            }
        return false;
    }

    public int getPort() {
        return port;
    }

    public boolean isOnline() {
        return online;
    }

    public ArrayList<ClientObject> getClients() {
        return clients;
    }

    public String getMessage() {
        String message = lastMessage;
        lastMessage = null;
        return message;
    }
}
