package Hello.jdbc.domain;

/*
*JDBC DriverManager 사용
 */

import Hello.jdbc.connection.DBConnectionUtil;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;

import javax.swing.plaf.basic.BasicTreeUI;
import java.sql.*;
import java.util.NoSuchElementException;

@Slf4j
public class MemberRepositoryV0 {
    Connection con = null;
    String sql = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;

    public Member InsertMember(Member member) throws SQLException{
        sql = "INSERT INTO MEMBER(member_id,money) values (?, ?)";
        con = getConnection();
        try {
            //SQL 인젝션 공격을 막을 수 있음 -> 입력 데이터를 단순 파라미터로 취급
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, member.getMemberId());
            pstmt.setInt(2, member.getMoney());
            pstmt.executeUpdate(); //영향을 받은 row의 개수
            return member;

        } catch (SQLException e) {
            log.info("db Error", e);
            throw e;
        }finally {
            close(con,pstmt,null);
        }
    }
    public void GetAllMembers() throws SQLException{
        sql = "SELECT * FROM MEMBER";
        con = getConnection();

        try {
             pstmt = con.prepareStatement(sql);
             rs = pstmt.executeQuery();
            while(rs.next()){
                System.out.println("ID : " + rs.getNString(1) + "MONEY : "+ rs.getInt(2));
            }
        } catch (SQLException e) {
            log.info("DB ERROR", e);
            throw e;
        }finally {

            close(con, pstmt, rs);
        }
    }

    public Member GetMemberByName(String name) throws SQLException{
        sql = "Select * from Member where Member_id = ?";
        con = getConnection();
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();

            if(rs.next()){
                Member member = new Member(rs.getString(1), rs.getInt(2));
                return member;
            }
            else {
                throw new NoSuchElementException("Member Not Found");
            }
        } catch (SQLException e) {
            log.info("DB ERROR", "Get Member by Name Error", e);
            throw e;
        }finally {
            close(con, pstmt, rs);
        }
    }

    private Connection getConnection(){
        return DBConnectionUtil.getConnection();
    }

    private void close(Connection con, Statement stmt, ResultSet rs){
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                log.info("error", e);
            }
        }
    }
}
