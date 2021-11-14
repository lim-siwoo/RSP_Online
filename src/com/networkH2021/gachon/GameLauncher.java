package com.networkH2021.gachon;


import com.networkH2021.gachon.user.UserDAO;

import java.nio.file.attribute.UserPrincipal;

public class GameLauncher {

    private static MainLobby mainLobby;
    private static SignUp signUp;
    private static Login login;
    private static Rank rank;
    private static MyCharacter user;
    private static UserDAO database;

    public GameLauncher(){
        login = new Login();
        signUp = new SignUp();
        rank = new Rank();
    }
    public static Login getLogin(){
        return login;
    }
    public static SignUp getSignUp(){
        return signUp;
    }
    public static MainLobby getMainLobby(){
        return mainLobby;
    }

    public static void MakeMainLobby(){
        mainLobby = new MainLobby();
    }

    public static Rank getRank(){
        return rank;
    }

    public static MyCharacter getUser() {
        return user;
    }

    public static void main(String[] args) {
	// write your code here
        new GameLauncher();
    }
}