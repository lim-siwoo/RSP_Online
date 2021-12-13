package com.networkH2021.gachon.user;


import com.networkH2021.gachon.GameLauncher;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import static com.networkH2021.gachon.GameLauncher.*;


public class UserDAO {


    private GameUser user = getUser();   /// 점검
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;
    private String nickname;
    private ArrayList<String> rank = new ArrayList<>();


    /* Getter and Seteer */
    public ArrayList<String> getRank() {
        return rank;
    }
    public void setRank(ArrayList<String> rank) {
        this.rank = rank;
    }
    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }


    public UserDAO() {
        try {
            String dbURL = "jdbc:mysql://220.116.200.107/network";
            String dbID = "network";
            String dbPassword = "1124";
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL,dbID,dbPassword);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("데이터베이스 오류 발생"); // 나중에 지워야 함
        }
    }


    public int setIP(String userID){

        String SQL = "UPDATE USER SET IP = ? WHERE ID = ?";
        String ip = GameLauncher.getUser().getIpAddress().toString();

        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, ip);
            pstmt.setString(2, userID);
            pstmt.executeUpdate();
            return 0;
        }catch(Exception e){
            e.printStackTrace();
        }
        return -1;
    }




    public int loginTime(String userID){

        String SQL = "UPDATE USER SET logined_log = ? WHERE ID = ?";

        LocalDate day = LocalDate.now();
        LocalTime time = LocalTime.now();
        String date = day.toString() + " " + time.toString().substring(0, 5);
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, date);
            pstmt.setString(2, userID);
            pstmt.executeUpdate();
            return 0;
        }catch(Exception e){
            e.printStackTrace();
        }
        return -1;
    }



    public int loginCount(String userID){

        String SQL = "UPDATE USER SET login_count = login_count +1 WHERE ID = ?";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, userID);
            pstmt.executeUpdate();
            return 0;
        }catch(Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public int login(String userID, String userPassword) {
        String SQL = "SELECT PASSWORD FROM USER WHERE ID = ?";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, userID);

            // 인젝션 해킹 등을 방지하기 위한 기법으로 ?에 ID 값을 받은 후 사용용
           rs = pstmt.executeQuery();
            if(rs.next()) {
                if(rs.getString(1).contentEquals(userPassword)) {

                    if(loginCount(userID) == -1 || loginTime(userID) == -1 || setIP(userID) == -1) {
                        return -3;  // 로그인 카운터나 로그인 시간 부분 오류
                    }
                    return 0;   // 로그인 성공
                }
                else
                    return 1;   // 비밀번호 불일치
            }
            return -1;  // ID 불일치

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -2;  // 데이터베이스 오류
    }


    public int checkID(String id){

        String SQL = "SELECT ID FROM USER WHERE id = ?";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                if (rs.getString(1) != null){
                    return -1;
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return 0;
    }



    public int join(String id, String password, String name, String nick, String email, String SNS) {


        String SQL = "insert into user value ( ?, ?, ?, ?, ?, ?)";

        if (checkID(id) == -1){ // 아이디 중복
            return -1;
        };

        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, id);
            pstmt.setString(2, password);
            pstmt.setString(3, name);
            pstmt.setString(4, nick);
            pstmt.setString(5, email);
            pstmt.setString(6, SNS);
            pstmt.executeUpdate();
            return 0;
        }catch(Exception e){
            e.printStackTrace();
        }
        return -2;  // 쿼리 문제
    }

    public int createUser(String userID) {

        String SQL = "SELECT NICKNAME FROM USER WHERE ID = ?";

        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, userID);
            rs = pstmt.executeQuery();
            while (rs.next()) {
                setNickname(rs.getString(1));
            }
            return 0;   // 생성 성공

        }catch(Exception e){
            e.printStackTrace();
        }
        return -1;    // 생성 실패
    }


    public int rank(){

        String SQL = "SELECT NICKNAME, WIN, LOSE FROM USER ORDER BY WIN DESC LIMIT 5";
        rank.clear();

        try {
            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String nickname = rs.getString(1);
                if (rs.wasNull()) nickname = "null";
                String win = rs.getString(2);
                if (rs.wasNull()) win = "null";
                String lose = rs.getString(3);
                if (rs.wasNull()) lose = "null";
                rank.add(nickname + "\t" + win + "\t" + lose);
            }
            return 0;       // 정상적으로 작동할 경우

        }catch(Exception e){
            e.printStackTrace();
        }
        return -1;  // 정상적으로 작동하지 않을 경우
    }

    public int updateWin(String userID){

        String SQL = "UPDATE USER SET WIN = WIN +1 WHERE ID = ?";
        try {
            pstmt.setString(1, userID);
            return pstmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public int updateLose(String userID){

        String SQL = "UPDATE USER SET WIN = WIN -1 WHERE ID = ?";
        try {
            pstmt.setString(1, userID);
            return pstmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
        return -1;
    }
}