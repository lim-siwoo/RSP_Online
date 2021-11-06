package com.networkH2021.gachon;

import javax.swing.*;


public class GameLauncher {

    private MainLobby mainLobby;



    public GameLauncher(){
        new Login();
    }

    public static void main(String[] args) {
	// write your code here
        new GameLauncher();
    }
}