package com.networkH2021.gachon.Networking;

import javax.management.remote.JMXServerErrorException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;

public class Client {
    private DatagramSocket socket;
    private InetAddress serverAddress;
    private int port;

    private int clientID;

    private String lastMessage;

    public Client(String address, int port){
        try{
            this.serverAddress = InetAddress.getByName(address);
            this.port = port;
            socket = new DatagramSocket();

            receive();
        }catch(Exception e){
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

                    if(!parseCommand(message)){
                        lastMessage = message;
                    }

                    lastMessage = message;
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }; thread.run();
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
