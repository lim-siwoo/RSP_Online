package com.networkH2021.gachon.Networking;

public class ClientObject {

    private String address;
    private int port;
    private int id;
    private String nickName;


    public ClientObject(String address,int port,int id){
        this.address = address;
        this.port = port;
        this.id = id;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getAddress() {
        return address;
    }

    public int getPort() {
        return port;
    }

    public int getId() {
        return id;
    }

    public String getNickName() {
        return nickName;
    }
}
