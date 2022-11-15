package com.zjut.study;

import com.taosdata.jdbc.TSDBDriver;
import com.zjut.study.common.junit.CommonJunitFilter;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 测试涛思数据库连接
 * @author tlf
 */
public class TDengineClientConnected extends CommonJunitFilter {

    /**
     * 原生连接
     */
    @Test
    public void origin() throws SQLException {
        String jdbcUrl = "jdbc:TAOS://127.0.0.1:6030?user=dev&password=123456";
        Properties connProps = new Properties();
        connProps.setProperty(TSDBDriver.PROPERTY_KEY_CHARSET, "UTF-8");
        connProps.setProperty(TSDBDriver.PROPERTY_KEY_LOCALE, "en_US.UTF-8");
        connProps.setProperty(TSDBDriver.PROPERTY_KEY_TIME_ZONE, "UTC-8");
        Connection conn = DriverManager.getConnection(jdbcUrl, connProps);
        System.out.println("Connected");
        conn.close();
    }

    /**
     * REST方式
     * @throws SQLException
     */
    @Test
    public void rest() throws SQLException {
        String jdbcUrl = "jdbc:TAOS-RS://hy.kalman-navigation.com:6041?user=hy&password=Hy123456";
        Properties connProps = new Properties();
        connProps.setProperty(TSDBDriver.PROPERTY_KEY_BATCH_LOAD, "true");
        Connection conn = DriverManager.getConnection(jdbcUrl, connProps);
        System.out.println("Connected");
        conn.close();
    }
}

