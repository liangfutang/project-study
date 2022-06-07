package com.zjut.study.mybatis.boot.properties;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

/**
 * Druid连接池
 * @author liangfu.tang
 */
@Data
@Configuration
public class DruidProperties {
    /**
     * Enable StatViewServlet.
     */
    private boolean statViewEnabled = false;
    private String statViewUrlPattern = "/druid/*";
    private String allow;
    private String deny;
    private String loginUsername = "admin";
    private String loginPassword = "admin";
    private String resetEnable = "false";
    //数据库类型
    private String dbType = "mysql";
    //是否记录慢sql
    //慢SQL的时间定义
    private boolean logSlowSql = true;
    private long slowSqlMillis = 3000;
    //是否SQL合并配置
    private boolean mergeSql = true;
    private String dataSourceLoggerName = "sql";
    private String connectionLoggerName = "sql";
    private String statementLoggerName = "sql";
    private String resultSetLoggerName = "sql";
    private Boolean statementExecutableSqlLogEnable = true;
    //对被认为是攻击的SQL进行LOG.error输出
    private Boolean logViolation = true;
    //对被认为是攻击的SQL抛出SQLExcepton
    private Boolean throwException = true;
    private Boolean multiStatementAllow = true;



    /**
     * Enable WebStatFilter.
     */
    private boolean webStatEnabled = false;
    private String webStatUrlPattern = "/*";
    /**
     * 经常需要排除一些不必要的url，比如*.js,/jslib/*等等。配置在init-param中。比如：
     <init-param>
     <param-name>exclusions</param-name>
     <param-value>*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*</param-value>
     </init-param>
     */
    private String exclusions = "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*";
    //缺省sessionStatMaxCount是1000个。你可以按需要进行配置
    private String sessionStatMaxCount = "1000";
    //你可以关闭session统计功能
    private String sessionStatEnable = "true";
    /**
     * 你可以配置principalSessionName，使得druid能够知道当前的session的用户是谁。比如：
     <init-param>
     <param-name>principalSessionName</param-name>
     <param-value>xxx.user</param-value>
     </init-param>
     根据需要，把其中的xxx.user修改为你user信息保存在session中的sessionName。
     注意：如果你session中保存的是非string类型的对象，需要重载toString方法。
     */
    private String principalSessionName = "s_device";
    /**
     * 如果你的user信息保存在cookie中，你可以配置principalCookieName，使得druid知道当前的user是谁
     <init-param>
     <param-name>principalCookieName</param-name>
     <param-value>xxx.user</param-value>
     </init-param>
     根据需要，把其中的xxx.user修改为你user信息保存在cookie中的cookieName
     */
    private String principalCookieName = "c_device";
    /**
     * druid 0.2.7版本开始支持profile，配置profileEnable能够监控单个url调用的sql列表。
     <init-param>
     <param-name>profileEnable</param-name>
     <param-value>true</param-value>
     </init-param>
     */
    private String profileEnable = "true";
}
