# 运行部署
## linux
1. 打包
```shell
mvn clean install package
```

2. 构建镜像
```shell
docker buildx build --platform linux/amd64 -t boot-jna-cpp:latest .
```

3. 本地docker运行
```shell
docker run --name boot-jna-cpp --restart=always -d -p 8080:8080 -v ~/logs:/home/log -e TZ='Asia/Shanghai' boot-jna-cpp:latest
```

4. 停止容器
```shell
docker stop boot-jna-cpp
```

5. 删除容器
```shell
docker rm boot-jna-cpp
```

6. 删除镜像
```shell
docker rmi boot-jna-cpp
```

## windows
本地启动，代码读取项目运行的native文件夹中的库


# 参考示例
[JNA Examples](https://www.eshayne.com/jnaex/index.html)   
[java-native-access](https://github.com/java-native-access/jna)

# 问题点
1. Linux下部署的时候使用`eclipse-temurin:8`作为基础镜像会导致启动失败，使用`openjdk:8-jdk-alpine`的时候则能正常启动
