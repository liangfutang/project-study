package com.zjut.study.mybatis.mj;

import com.alibaba.fastjson.JSONObject;
import com.zjut.study.common.junit.CommonJunitFilter;
import com.zjut.study.mybatis.mj.entity.IsolationTestDO;
import com.zjut.study.mybatis.mj.mapper.IsolationTestMapper;
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

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void before() throws SQLException, IOException {
        SqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SqlSessionFactoryBuilder();
        sqlSessionFactory = sqlSessionFactoryBuilder.build(Resources.getResourceAsStream("mybatis-config.xml"));
        configuration = sqlSessionFactory.getConfiguration();

        Connection connection = sqlSessionFactory.openSession().getConnection();
        // 下面的连接导致失败
//        Connection connection = configuration.getEnvironment().getDataSource().getConnection();
        jdbcTransaction = new JdbcTransaction(connection);
    }

    /**
     * 简单执行器，使用xml形式
     * @throws SQLException
     */
    @Test
    public void testSimple() throws SQLException {
        SimpleExecutor executor = new SimpleExecutor(configuration, jdbcTransaction);
        MappedStatement ms = configuration.getMappedStatement("com.zjut.study.mybatis.mj.mapper.IsolationTestMapper.selectById1");
        // doQuery是处理器中具体实现业务的方法，查询不带缓存
        Object objects = executor.doQuery(ms, "xixixi", RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, ms.getBoundSql("xixixi"));
        System.out.println(JSONObject.toJSONString(objects));
        // 多次请求，会有多次预编译
        Object objects1 = executor.doQuery(ms, "xixixi", RowBounds.DEFAULT, SimpleExecutor.NO_RESULT_HANDLER, ms.getBoundSql("xixixi"));
        System.out.println(JSONObject.toJSONString(objects1));
    }


    /**
     * sqlsession查询的不通方式
     */
    @Test
    public void testMapper() {
        IsolationTestMapper mapper = sqlSessionFactory.openSession(true).getMapper(IsolationTestMapper.class);
        List<IsolationTestDO> o = mapper.selectById1("xixixi");
        System.out.println(JSONObject.toJSONString(o));

        List<IsolationTestDO> objects = sqlSessionFactory.openSession(true).selectList("com.zjut.study.mybatis.mj.mapper.IsolationTestMapper.selectById1", "xixixi");
        System.out.println(JSONObject.toJSONString(objects));
    }
}
