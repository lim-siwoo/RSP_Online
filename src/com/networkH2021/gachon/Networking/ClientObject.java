package com.networkH2021.gachon.Networking;

public class ClientObject {

    private final String address;
    private final int port;
    private final int id;
    private String nickName;

    private boolean reponsive = true;
    private double checkTime;
    private int timeSinceCheck;
    private boolean inGame = false;
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

    public boolean isReponsive() {
        return reponsive;
    }

    public void setReponsive(boolean reponsive) {
        this.reponsive = reponsive;
    }

    public double getCheckTime() {
        return checkTime;
    }

    public void setCheckTime(double checkTime) {
        this.checkTime = checkTime;
    }

    public int getTimeSinceCheck() {
        return timeSinceCheck;
    }

    public void setTimeSinceCheck(int timeSinceCheck) {
        this.timeSinceCheck = timeSinceCheck;
    }

    public boolean getInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }
}
