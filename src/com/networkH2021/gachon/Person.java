package com.networkH2021.gachon;

public class Person {



    private String userID;
    private String userPassword;
    private String userName;
    private String userNickname;
    private String userEmail;
    private String userSNS;

    public Person(String id, char[] password, String name, String nickname, String email, String SNS) {

        this.userID = id;
        userPassword = password;
        this.userName = name;
        this.userNickname = nickname;
        this.userEmail = email;
        this.userSNS = SNS;
    }

    public String getUserID() {
        return userID;
    }
    public void setUserID(String userID) {
        this.userID = userID;
    }
    public String getUserPassword() {
        return userPassword;
    }
    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserNickname() {
        return userNickname;
    }
    public void setUserNickname(String userName) {
        this.userNickname = userNickname;
    }
    public String getUserEmail() {
        return userEmail;
    }
    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
    public String getUserSNS() {
        return userSNS;
    }
    public void setUserSNS(String userEmail) {
        this.userSNS = userSNS;
    }







}
