package com.networkH2021.gachon.user;

import java.net.InetAddress;
import java.net.UnknownHostException;
import com.networkH2021.gachon.GameRoom.GameRoom;
import java.net.Socket;

public class GameUser extends user{
    private int port = 10001;
    private InetAddress ipAddress;
    private String lastLog;
    private GameRoom room;
    private Socket sock;

    public GameUser() {// 아무런 정보가 없는 깡통 유저를 만들 때
        super();
    }
    public GameUser(String id, String nickname) {
        super(id, nickname);
        try {
            setIpAddress(InetAddress.getLocalHost());//테스트로 로컬로 설정함
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * 방에 입장시킴
     * @param room  입장할 방
     */
    public void enterRoom(GameRoom room) {
        room.enterUser(this); // 룸에 입장시킨 후
        this.room = room; // 유저가 속한 방을 룸으로 변경한다.(중요)
    }

    /**
     * 방에서 퇴장
     * @param room 퇴장할 방
     */
    public void exitRoom(GameRoom room){
        this.room = null;
        // 퇴장처리(화면에 메세지를 준다는 등)
        // ...
    }


    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public InetAddress getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
    }


}
