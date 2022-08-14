[toc]

# 一. mybatis
## 1.1. JDBC
[jdbc测试使用类](src/main/java/com/zjut/study/mybatis/jdbc/JdbcClient.java)

![JDBC基本操作流程](pic/JDBC操作流程.png)

## 1.2. mybatis
### 1.2.1 执行器Executor
![mybatis基本执行体](pic/mybatis执行体系.png)   
![mybatis执行器抽象类执行过程](pic/mybatis执行器抽象类执行过程.png)

### 1.2.2 一级缓存
![mybatis一级缓存命中场景](pic/mybatis一级缓存命中场景.png)  
![mybatis一级缓存中key的内容](pic/mybatis一级缓存中key的内容.png)  
![mybatis一级缓存源码流程1](pic/mybatis一级缓存源码流程1.png)  
![mybatis一级缓存源码流程2](pic/mybatis一级缓存源码流程2.png)  
![Spring整合mybatis后源码流程图](pic/Spring整合mybatis后源码流程图.png)  
spring中的Mapper代理类中用的是会话模板SqlSessionTemplate，该模板也是继承的SQLSession，创建该模板的时候内部使用SqlSessionInterceptor生成代理方便拦截(因为spring中直接掉的是模板。所以模板中的方法需要再代理一下，最终目的是要实现事务)，该拦截器中在获取SQLSession的时候会用SqlSessionHolder(ThreadLocal)判断当前线程中是否存在SQLSession，存在则用现有的    
![mybatis一级缓存总结](pic/mybatis一级缓存总结.png)

### 1.2.3 二级缓存
![mybatis缓存体系](pic/mybatis缓存体系.png)    
![mybatis二级缓存定义](pic/mybatis二级缓存定义.png)    
![mybatis二级缓存扩展性需求](pic/mybatis二级缓存扩展性需求.png)    

![mybatis二级缓存组织结构(装饰器模式+责任链模式)](pic/mybatis二级缓存组织结构(装饰器模式+责任链模式).png)   
![mybatis二级缓存命中](pic/mybatis二级缓存命中.png)   
![mybatis二级缓存配置表](pic/mybatis二级缓存配置表.png)   

![mybatis二级缓存需要提交后查找才能命中缓存](pic/mybatis二级缓存需要提交后查找才能命中缓存.png)   
暂存区需要commit后才能存储都缓存区供其他缓存查询
![mybatis二级缓存结构关系](pic/mybatis二级缓存结构关系.png)  
![mybatis二级缓存源码中结构关系](pic/mybatis二级缓存源码中结构关系.png)  
![mybatis二级缓存执行流程](pic/mybatis二级缓存执行流程.png)  
![mybatis执行过程总结](pic/mybatis执行过程总结.png)  

### 1.2.4 StatementHandler
![StatementHandler定义](pic/StatementHandler定义.png)    
![StatementHandler继承关系](pic/StatementHandler继承关系.png)    
![StatementHandler执行流程](pic/StatementHandler执行流程.png) 


### 1.2.5 参数映射处理
基于映射进行具体的参数赋值处理。
![参数映射处理](pic/参数映射处理.png) 


### 1.2.6 结果集处理
![参数映射处理](pic/结果集处理流程.png)  

### 1.2.7 MetaObject
[MetaObject测试类](src/main/java/com/zjut/study/mybatis/MetaObjectClient.java)

![MetaObject原理结构示意图](pic/MetaObject原理结构示意图.png)  
![以获取博客某条评论下评论者为案例说明MetaObject内部原理流程](pic/以获取博客某条评论下评论者为案例说明MetaObject内部原理流程.png)  


### 1.2.8 ResultMap结果集映射
![手动映射](pic/手动映射.png)  
![自动映射](pic/自动映射.png)  

### 1.2.9 循环依赖
![自动映射](pic/循环依赖流程解析.png)  

# 二. spring-mybatis

