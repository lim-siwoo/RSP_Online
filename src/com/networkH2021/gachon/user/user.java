package com.networkH2021.gachon.user;

public class user {

    private String userID;
    private String userPassword;
    private String userName;
    private String userNickname;
    private String userEmail;
    private String userSNS;

    public user(){
    }
    public user(String id,String nickname) {
        this.userID = id;
        this.userNickname = nickname;
    }

    public user(String userID, String userPassword, String userName, String userNickname, String userEmail, String userSNS) {
        this.userID = userID;
        this.userPassword = userPassword;
        this.userName = userName;
        this.userNickname = userNickname;
        this.userEmail = userEmail;
        this.userSNS = userSNS;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        user user = (user) o;

        return userID.equals(user.userID);
    }

    @Override
    public int hashCode() {
        return userID.hashCode();
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

    @Override
    public String toString() {
        return userNickname;
    }
}
