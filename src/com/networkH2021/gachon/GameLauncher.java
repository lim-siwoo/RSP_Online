package com.networkH2021.gachon;


import com.networkH2021.gachon.Networking.Client;
import com.networkH2021.gachon.Networking.Server;
import com.networkH2021.gachon.user.GameUser;
import com.networkH2021.gachon.user.UserDAO;

import java.net.UnknownHostException;

public class GameLauncher {

    public static MainLobby mainLobby;
    private static SignUp signUp;
    private static Login login;
    private static Rank rank;
    private static GameUser user;
    public static UserDAO database;
    public static Client client;
    public static Server server;
    //public static Game game;


    public GameLauncher() throws UnknownHostException {
        login = new Login();
        signUp = new SignUp();
        rank = new Rank();
        database = new UserDAO();
        user = new GameUser("NULL", "NULL");
        mainLobby = new MainLobby();
        //game = new Game();

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
    public static UserDAO getUserDAO() { return database; }

    public static void setMainLobby(MainLobby mainLobby) {
        GameLauncher.mainLobby = mainLobby;
    }

    public static void MakeMainLobby() {
        mainLobby = new MainLobby();
    }
    public static void MakeClient(){
        client = new Client("14.47.251.177",5284);
    }

    /*public static Game getGame(){
        return game;
    }*/

    public static Rank getRank(){
        return rank;
    }

    public static GameUser getUser() {
        return user;
    }

    public static Client getClient() {
        return client;
    }

    public static void setClient(Client client) {
        GameLauncher.client = client;
    }

    public static Server getServer() {
        return server;
    }

    public static void setServer(Server server) {
        GameLauncher.server = server;
    }

    public static void main(String[] args) throws UnknownHostException {
	// write your code here
        new GameLauncher();
    }
}