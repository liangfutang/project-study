package com.zjut.study.mybatis.jdbc.jdbc;

import com.zjut.study.common.junit.CommonJunitFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

/**
 * 主要用作jdbc相关测试
 * @author jack
 */
public class JdbcClient extends CommonJunitFilter {

    private Connection connection;

    @Before
    public void before() throws IOException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        // 准备连接参数
        InputStream in = new BufferedInputStream(new FileInputStream("src/main/resources/dbconfig.properties"));
        Properties properties = new Properties();
        properties.load(in);
        String driverClass = properties.getProperty("jdbc.driverClass");
        String url = properties.getProperty("jdbc.url");
        String username = properties.getProperty("jdbc.username");
        String password = properties.getProperty("jdbc.password");

        // 1. 加载驱动程序
        // 不做这里的驱动加载也能正常的执行。jdbc4.0 是不用显式的去加载驱动，如果驱动包符合 SPI 模式就会自动加载.
        // @see java.sql.DriverManager.loadInitialDrivers
        Class.forName(driverClass).newInstance();
        // 1. 获取连接
        // 管理各种不同的JDBC驱动
        connection = DriverManager.getConnection(url, username, password);
    }

    @After
    public void after() throws SQLException {
        connection.close();
    }

    @Test
    public void jdbcTest() throws SQLException {
        // 2. 预编译
        String sql = "SELECT * FROM isolation_test WHERE id=?";
        // Statement 由 Connection 产生、负责发送执行SQL语句
        // Statement：用于执行不带参数的简单SQL语句；
        // PreparedStatement（从 Statement 继承）：用于执行带或不带参数的预编译SQL语句；
        // CallableStatement（从PreparedStatement 继承）：用于执行数据库存储过程的调用。
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, 1);
        // 3. 执行
        preparedStatement.execute();
        // 4. 获取结果集
        ResultSet resultSet = preparedStatement.getResultSet();
        while (resultSet.next()) {
            String name = resultSet.getString(2);
            System.out.println("第二列的结果:" + name);
            int audit = resultSet.getInt("audit");
            System.out.println("audit列的结果:" + audit);
        }
        // 5. 关闭
        resultSet.close();
        preparedStatement.close();
    }
}
