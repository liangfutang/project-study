package com.zjut.study.mybatis;

import com.alibaba.fastjson.JSONObject;
import com.zjut.study.common.junit.CommonJunitFilter;
import org.apache.ibatis.executor.SimpleExecutor;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransaction;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Executor执行器测试
 * @author jack
 */
public class ExecutorClient extends CommonJunitFilter {

    private Configuration configuration;
    private JdbcTransaction jdbcTransaction;

    @Before
    public void before() throws SQLException, IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        SqlSessionFactory build = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
        configuration = build.getConfiguration();

        Connection connection = configuration.getEnvironment().getDataSource().getConnection();
        jdbcTransaction = new JdbcTransaction(connection);
    }

    /**
     * 简单执行器，使用xml形式
     * @throws SQLException
     */
    @Test
    public void testSimple() throws SQLException {
        SimpleExecutor executor = new SimpleExecutor(configuration, jdbcTransaction);
        MappedStatement ms = configuration.getMappedStatement("com.zjut.study.mybatis.IsolationTestMapper.selectById");
        // doQuery是处理器中具体实现业务的方法，查询不带缓存
        List<Object> objects = executor.doQuery(ms, 1, RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, ms.getBoundSql(1));
        System.out.println(JSONObject.toJSONString(objects));
    }
}
