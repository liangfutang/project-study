package com.zjut.study.jvm.pools.loader;

import java.sql.*;

public class JdbcTest {
    public static void main(String[] args) throws SQLException {
        ResultSet rs = null;
        Statement sta = null;
        Connection conn = null;
//        try {
//            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/user_auth_center");
            sta = conn.createStatement();
            rs = sta.executeQuery("select * from user");
            while(rs.next()) {
                System.out.println(rs.getString("deptno"));
            }
//        } catch(ClassNotFoundException e) {
//            System.out.println("没有找到相应驱动");
//        } catch(SQLException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if(rs != null) rs.close();
//                if(sta != null) sta.close();
//                if(conn != null) conn.close();
//            } catch(SQLException e) {
//                e.printStackTrace();
//            }
//
//        }

    }
}
