package org.psawesome;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MemberDAO {

    public Long save(Connection conn, Member member) {
        PreparedStatement pstmt = null;

        var sql = "INSERT INTO MEMBER(USERNAME, PHONE_NUMBER) VALUES (?, ?)";
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, member.getUsername());
            pstmt.setString(2, member.getPhoneNumber());

            pstmt.executeUpdate();
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getLong(1);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(conn, pstmt);
        }
    }

    public Member findOne(Connection conn, Long memberId) {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        String sql = "SELECT * FROM MEMBER WHERE MEMBER_ID = ?";

        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, memberId);

            rs = pstmt.executeQuery();
            if (rs.next()) {
                return Member.builder()
                        .memberId(rs.getLong("MEMBER_ID"))
                        .username(rs.getString("USERNAME"))
                        .phoneNumber(rs.getString("PHONE_NUMBER"))
                        .build();
            }

            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            close(conn, pstmt);
        }
    }

    public void close(Connection conn, PreparedStatement pstmt) {
        try {
            conn.close();
            pstmt.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
