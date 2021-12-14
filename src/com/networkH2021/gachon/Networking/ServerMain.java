package com.networkH2021.gachon.Networking;

import com.networkH2021.gachon.GameLauncher;

import java.net.UnknownHostException;



public class ServerMain {//게임통신용서버
    private static Server server;

    public static void main(String[] args){
        // write your code here
        server = new Server(5284);
    }

    public static Server getServer() {
        return server;
    }

    public static void setServer(Server server) {
        ServerMain.server = server;
    }
}
