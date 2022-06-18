[toc]

# tomcat使用日志的案例
tomcat启动时打印出的红色日志,是对java.util.log的封装，最终内部使用的还是System error流打印出的日志  
Tomcat封装的日志中VersionLoggerListener管理调用的jul
![Tomcat启动时显示的日志](./pic/tomcat日志.png)    
![Tomcat启动时显示的日志](./pic/tomcat启动的时候打印的日志.png)  
![System eror打印出的日志](./pic/System%20error打印出的显示日志.png) 

![java日志体系发展史](./pic/java日志体系发展史.png)

