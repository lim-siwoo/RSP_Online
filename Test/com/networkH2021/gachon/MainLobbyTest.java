package com.networkH2021.gachon;

import com.networkH2021.gachon.client.ChatClientApp;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MainLobbyTest {

    MainLobby mainLobby;
    ChatClientApp chatClientApp;
    @BeforeEach
    void setUp() {
        GameLauncher.MakeMainLobby();
        mainLobby = GameLauncher.getMainLobby();
        //chatClientApp = new ChatClientApp();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void check(){

        assertEquals(null,mainLobby);

        /**
         assertEquals(582,mainLobby.getX());
         assertEquals(303,mainLobby.getY());
         */
    }

    @Test
    void chatting() {

    }
}