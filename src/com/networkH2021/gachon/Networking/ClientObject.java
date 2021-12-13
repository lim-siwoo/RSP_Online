package com.networkH2021.gachon.Networking;

public class ClientObject {

    private String address;
    private int port;
    private int id;
    private String nickName;


    private String userID;


    public ClientObject(String address, int port, int id, String clientNick, String userID){
        this.address = address;
        this.port = port;
        this.id = id;
        this.nickName = clientNick;
        this.userID = userID;
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
