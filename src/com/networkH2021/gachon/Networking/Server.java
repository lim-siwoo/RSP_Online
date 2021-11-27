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

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void receive(){
        Thread thread = new Thread(){
            public void run(){
                try{
                    byte[] rawData = new byte[1024];
                    DatagramPacket packet = new DatagramPacket(rawData, rawData.length);
                    socket.receive(packet);

                    String message = new String(rawData);
                    message = message.substring(0,message.indexOf("\\e"));//end of line

                    if(!parseCommand(message, packet)){
                        lastMessage = message;
                    }

                    lastMessage = message;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }; thread.run();
    }

    public void send(String message, String ip, int port){
        try {
            message = message + "\\e";
            byte[] data = message.getBytes(StandardCharsets.UTF_8);
            DatagramPacket packet = new DatagramPacket(data, data.length, InetAddress.getByName(ip), port);
            socket.send(packet);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateClient(String message){
        for (int i=0;i<clients.size();i++){
            ClientObject client = clients.get(i);
            send(message, client.getAddress(),client.getPort());
        }
    }

    public boolean parseCommand(String message, DatagramPacket packet){
        if (message.startsWith("\\c")){//create client parse
            //CONNECT CLIENT TO SERVER
            ClientObject client = new ClientObject (packet.getAddress().toString(), packet.getPort(), clientID+1);
            clients.add(client);
            send("\\cid:"+client.getId(), client.getAddress(), client.getPort());
            clientID++;
            return true;
        }else
            if (message.startsWith("\\d")){//delete client parse
                int id = Integer.parseInt(message.substring(3));
                for (int i =0; i <clients.size();i++){
                    if (clients.get(i).getId() == i){
                        clients.remove(clients.get(i));
                        return true;
                    }
                }
//                client.send("\\d:"+clientID);
                System.out.println("Server: Error, Client With ID"+id+", was not found!");
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
