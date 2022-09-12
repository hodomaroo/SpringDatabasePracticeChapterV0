package Hello.jdbc.domain;

/*
 *JDBC DriverManager 사용
 */

import Hello.jdbc.connection.DBConnectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.support.JdbcUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.NoSuchElementException;

/*
JDBC DataSource 사용
 */

@Slf4j
public class MemberRepositoryV1 {
    private final DataSource dataSource;
    Connection con = null;
    String sql = null;
    ResultSet rs = null;
    PreparedStatement pstmt = null;

    public MemberRepositoryV1(DataSource dataSource) {
        this.dataSource = dataSource;
    }

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

    public void UpdateMember(String name, int money) throws SQLException{
        sql = "Update member Set money = ? where member_id = ?";
        con = getConnection();
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setInt(1, money);
            pstmt.setString(2, name);
            pstmt.execute();
        } catch (SQLException e) {
            log.info("DB ERROR", e);
            throw e;
        }finally {
            close(con, pstmt, rs);
        }
    }
    public void DeleteMember(String  name) throws SQLException{
        sql = "Delete from member where member_id = ?";
        con = getConnection();
        try {
            pstmt = con.prepareStatement(sql);
            pstmt.setString(1, name);
            pstmt.execute();
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
                Member resultMember = new Member(rs.getString(1), rs.getInt(2));
                return resultMember;
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


    private Connection getConnection() throws SQLException{
        Connection con = dataSource.getConnection();
        log.info("get Connection {}, {}",con, con.getClass());
        return con;
    }

    private void close(Connection con, Statement stmt, ResultSet rs){
        JdbcUtils.closeResultSet(rs);
        JdbcUtils.closeStatement(stmt);
        JdbcUtils.closeConnection(con);
    }
}
