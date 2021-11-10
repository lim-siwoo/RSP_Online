package user;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class UserDAO {
    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    public UserDAO() {
        try {
            String dbURL = "jdbc:mysql://localhost/mydb";       // 추후 수정
            String dbID = "root";
            String dbPassword = "12345";
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(dbURL,dbID,dbPassword);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int login(String userID, String userPassword) {
        String SQL = "SELECT userPassword FROM USER WHERE userID = ?";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, userID);

            // 인젝션 해킹 등을 방지하기 위한 기법으로 ?에 ID 값을 받은 후 사용용
           rs = pstmt.executeQuery();
            if(rs.next()) {
                if(rs.getString(1).contentEquals(userPassword)) {
                    return 1;   // 로그인 성공
                }
                else
                    return 0;   // 비밀번호 불일치
            }
            return -1;  // There's no ID

        } catch (Exception e) {
            e.printStackTrace();
        }
        return -2;  // 데이터베이스 오류
    }


    public int join(User user) {
        String SQL = "INSERT INTO USER VALUES( ?, ?, ?, ?, ?)";
        try {
            pstmt = conn.prepareStatement(SQL);
            pstmt.setString(1, user.getUserID());
            pstmt.setString(2, user.getUserPassword());
            pstmt.setString(3, user.getUserName());
            pstmt.setString(4, user.getUserGender());
            pstmt.setString(5, user.getUserEmail());
            return pstmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public int rank(){

        String SQL = "SELECT NICKNAME, WIN, LOSE FROM USER ORDER BY WIN DESC LIMIT 5";
        try {
            pstmt = conn.prepareStatement(SQL);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                String nickname = rs.getString(1);
                if (rs.wasNull()) nickname = "null";
                String win = rs.getString(2);
                if (rs.wasNull()) win = "null";
                String lose = rs.getString(1);
                if (rs.wasNull()) lose = "null";
                System.out.println(nickname + "\t" + win + "\t" + lose);    // 출력 부분 수정
            }
            return 0;       // 정상적으로 작동할 경우

        }catch(Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public int updateWin(String userID){

        String SQL = "UPDATE USER SET WIN++ WHERE ID = ?";
        try {
            pstmt.setString(1, userID);
            return pstmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public int updateLose(String userID){

        String SQL = "UPDATE USER SET WIN-- WHERE ID = ?";
        try {
            pstmt.setString(1, userID);
            return pstmt.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }
        return -1;
    }


}
