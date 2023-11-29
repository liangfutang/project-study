# 核心概念
+ 库<DataBase>   
mongodb中的库就类似于传统关系型数据库中库的概念，用来通过不同库隔离不同应用数据。  
mongodb中可以建立多个数据库。每一个库都有自己的集合和权限，不同的数据库也放置在不同的文件中。默认的数据库为"test”，数据库存储在启动指定的data目录中
+ 集合<Collection>    
集合就是 MongoDB 文档组，类似于 RDBMS(关系数据库管理系统: RelationalDatabase Management System)中的表的概念。   
集合存在于数据库中，一个库中可以创建多个集合。每个集合没有固定的结构，这意味着你在对集合可以插入不同格式和类型的数据，但通常情况下我们插入集合的数据都会有一定的关联性。
+ 文档<Document>    
文档集合中一条条记录，是一组键值key-value)对(即 BSON)。MongoDB 的文档不需要设置相同的字段，并且相同的字段不需要相同的数据类型，这与关系型数据库有很大的区别，也是 MongoDB 非常突出的特点。   
一个简单的文档例子如下:
{"site":"www.baidu.com","name":"李彦宏"}