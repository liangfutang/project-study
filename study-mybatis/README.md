[toc]

# 一. JDBC
[jdbc测试使用类](src/main/java/com/zjut/study/mybatis/jdbc/JdbcClient.java)

![JDBC基本操作流程](pic/JDBC操作流程.png)

# 二. mybatis
## 2.1 执行器Executor
![mybatis基本执行体](pic/mybatis执行体系.png)   
![mybatis执行器抽象类执行过程](pic/mybatis执行器抽象类执行过程.png)

## 2.2 一级缓存
![mybatis一级缓存命中场景](pic/mybatis一级缓存命中场景.png)  
![mybatis一级缓存中key的内容](pic/mybatis一级缓存中key的内容.png)  
![mybatis一级缓存源码流程1](pic/mybatis一级缓存源码流程1.png)  
![mybatis一级缓存源码流程2](pic/mybatis一级缓存源码流程2.png)  
![Spring整合mybatis后源码流程图](pic/Spring整合mybatis后源码流程图.png)  
![mybatis一级缓存总结](pic/mybatis一级缓存总结.png)

## 2.3 二级缓存
![mybatis缓存体系](pic/mybatis缓存体系.png)    
![mybatis二级缓存定义](pic/mybatis二级缓存定义.png)    
![mybatis二级缓存扩展性需求](pic/mybatis二级缓存扩展性需求.png)    

![mybatis二级缓存组织结构(装饰器模式+责任链模式)](pic/mybatis二级缓存组织结构(装饰器模式+责任链模式).png)   
![mybatis二级缓存命中](pic/mybatis二级缓存命中.png)   
![mybatis二级缓存配置表](pic/mybatis二级缓存配置表.png)   
