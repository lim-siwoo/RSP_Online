package com.networkH2021.gachon.user;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.net.Socket;

public class GameUser extends user{
    private int port = 10001;
    private InetAddress ipAddress;
    private String lastLog;
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
