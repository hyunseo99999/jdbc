package hello.jdbc.repository;

import hello.jdbc.connection.DBConnectionUtil;
import hello.jdbc.domain.Member;
import lombok.extern.slf4j.Slf4j;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.NoSuchElementException;

import static hello.jdbc.connection.ConnectionConst.*;

@Slf4j
public class MemberRepositoryV0 {

    public Member save(Member member) throws SQLException {
        Connection connection = DBConnectionUtil.getConnection();
        PreparedStatement preparedStatement = null;
        String sql = "insert into member(member_id, money) values (?, ?)";
        ResultSet rs = null;

        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, member.getMemeber_id());
            preparedStatement.setInt(2, member.getMoney());
            preparedStatement.executeUpdate();
            return member;
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
            if (connection != null) {
                connection.close();
            }

            close(connection, preparedStatement, rs);
        }
    }

    public Member findById(String member_id) throws SQLException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = DBConnectionUtil.getConnection();
            ps = connection.prepareStatement("select * from member where member_id = ?");
            ps.setString(1, member_id);

            rs = ps.executeQuery();
            if (rs.next()) {
                Member member = new Member();
                member.setMemeber_id(rs.getString("member_id"));
                member.setMoney(rs.getInt("money"));
                return member;
            } else {
                throw new NoSuchElementException("member not found memberId = " + member_id);
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        } finally {
            close(connection, ps , rs);
        }
    }

    public void update(String memberId, int money) throws SQLException {
        String sql = "update member set money = ? where member_id = ?";

        Connection connection = null;
        PreparedStatement pstmt = null;
        try {
            connection = DBConnectionUtil.getConnection();
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, money);
            pstmt.setString(2, memberId);

            int resultSize = pstmt.executeUpdate();
            log.info("resultSize={}", resultSize);
         } catch (Exception e) {
            log.error("db error", e);
            throw e;
        } finally {
            close(connection, pstmt, null);
        }
    }

    public void delete(String memberId) throws SQLException {
        Connection conn = null;
        PreparedStatement pstm = null;
        String sql = "delete from member where member_id = ?";
        try {
            conn = DBConnectionUtil.getConnection();
            pstm = conn.prepareStatement(sql);
            pstm.setString(1, memberId);

            pstm.executeUpdate();

        } catch (Exception e) {
            log.error("SQLException {}", e.getMessage());
        } finally {
            close(conn, pstm, null);
        }


    }

    private void close(Connection connection, PreparedStatement preparedStatement, ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
        }

        if (preparedStatement != null) {
            preparedStatement.close();
        }

        if (connection != null) {
            connection.close();
        }

    }
}
