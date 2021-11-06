package com.networkH2021.gachon;


public class GameLauncher {

    private static MainLobby mainLobby;
    private static SignUp signUp;
    private static Login login;
    private static Rank rank;
    private Person User;

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

    public static void main(String[] args) {
	// write your code here
        new GameLauncher();
    }
}