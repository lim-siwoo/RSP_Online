/*
 * Author : Gompang
 * Desc : 네트워크 게임에서 사용되는(채팅도 포함) 방 개념 클래스
 * Blog : http://gompangs.tistory.com/
 */
package com.networkH2021.gachon.GameRoom;

import com.networkH2021.gachon.user.GameUser;
import com.networkH2021.gachon.user.user;

import java.util.ArrayList;
import java.util.List;

public class GameRoom {

    private int id; // 룸 ID
    private List<GameUser> userList;
    private GameUser roomOwner; // 방장
    private String roomName; // 방 이름

    public GameRoom(int roomId) { // 아무도 없는 방을 생성할 때
        this.id = roomId;
        userList = new ArrayList();
    }

    public GameRoom(GameUser user) { // 유저가 방을 만들때
        userList = new ArrayList();
        user.enterRoom(this);
        userList.add(user); // 유저를 추가시킨 후
        this.roomOwner = user; // 방장을 유저로 만든다.
    }

    public GameRoom(List users) { // 유저 리스트가 방을 생성할
        this.userList = users; // 유저리스트 복사

        // 룸 입장
        for(GameUser user : userList){
            user.enterRoom(this);
        }

        this.roomOwner = (GameUser) userList.get(0); // 첫번째 유저를 방장으로 설정
    }

    public void enterUser(GameUser user) {
        user.enterRoom(this);
        userList.add(user);
    }

    public void enterUser(List users) {
        for(GameUser gameUser : userList){
            gameUser.enterRoom(this);
        }
        userList.addAll(users);
    }

    /**
     * 해당 유저를 방에서 내보냄
     * @param user 내보낼 유저
     */
    public void exitUser(GameUser user) {
        user.exitRoom(this);
        userList.remove(user); // 해당 유저를 방에서 내보냄

        if (userList.size() < 1) { // 모든 인원이 다 방을 나갔다면
            RoomManager.removeRoom(this); // 이 방을 제거한다.
            return;
        }

        if (userList.size() < 2) { // 방에 남은 인원이 1명 이하라면
            this.roomOwner = (GameUser) userList.get(0); // 리스트의 첫번째 유저가 방장이 된다.
            return;
        }
    }

    /**
     * 해당 룸의 유저를 다 퇴장시키고 삭제함
     */
    public void close() {
        for (GameUser user : userList) {
            user.exitRoom(this);
        }
        this.userList.clear();
        this.userList = null;
    }

    // 게임 로직

    /**
     * 해당 byte 배열을 방의 모든 유저에게 전송
     * @param data 보낼 data
     */
    public void broadcast(byte[] data) {
        for (GameUser user : userList) { // 방에 속한 유저의 수만큼 반복
            // 각 유저에게 데이터를 전송하는 메서드 호출~
            // ex) user.SendData(data);

//			try {
//				user.sock.getOutputStream().write(data); // 이런식으로 바이트배열을 보낸다.
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
        }
    }

    public void setOwner(GameUser gameUser) {
        this.roomOwner = gameUser; // 특정 사용자를 방장으로 변경한다.
    }

    public void setRoomName(String name) { // 방 이름을 설정
        this.roomName = name;
    }

    public GameUser getUserByNickName(String nickName) { // 닉네임을 통해서 방에 속한 유저를 리턴함

        for (GameUser user : userList) {
            if (user.getUserNickname().equals(nickName)) {
                return user; // 유저를 찾았다면
            }
        }
        return null; // 찾는 유저가 없다면
    }

    public GameUser getUser(GameUser gameUser) { // GameUser 객체로 get

        int idx = userList.indexOf(gameUser);

        // 유저가 존재한다면(gameUser의 equals로 비교)
        if(idx > 0){
            return userList.get(idx);
        }
        else{
            // 유저가 없다면
            return null;
        }
    }

    public String getRoomName() { // 방 이름을 가져옴
        return roomName;
    }

    public int getUserSize() { // 유저의 수를 리턴
        return userList.size();
    }

    public GameUser getOwner() { // 방장을 리턴
        return roomOwner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List getUserList() {
        return userList;
    }

    public void setUserList(List userList) {
        this.userList = userList;
    }

    public GameUser getRoomOwner() {
        return roomOwner;
    }

    public void setRoomOwner(GameUser roomOwner) {
        this.roomOwner = roomOwner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GameRoom gameRoom = (GameRoom) o;

        return id == gameRoom.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}