
# 一. 服务结构说明
只是dubbo的相关功能测试，故只是注册了dubbo接口，也没有用配置中心
```
提供者: dubbo-provider
消费者: dubbo-consumer
注册中心: nacos
```
模块间架构关系如下:
![模块间架构关系](pic/模块间架构关系.png)

## 1.1 整合过程说明
依赖jar包如下
```
<dependency>
    <artifactId>dubbo</artifactId>
    <groupId>org.apache.dubbo</groupId>
</dependency>
<dependency>
    <artifactId>nacos-discovery-spring-boot-starter</artifactId>
    <groupId>com.alibaba.boot</groupId>
</dependency>
```
application.properties、启动参数、配置中心中配置服务发现相关配置参数：
```
nacos.discovery.server-addr = 127.0.0.1:8848
nacos.discovery.namespace = 
```
剩下一些dubbo的配置bean和扫描注解之类

## 1.2 当前有疑问的点
+ 将当前应用注册到nacos中时一定需要`NacosRegisterConfiguration`代码注入么
+ 将dubbo包扫描替换成代码方式
