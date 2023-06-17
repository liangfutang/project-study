
# 一. 接入说明
**模块说明**
```
agent-static: 静态模式探针 
agent-attach: 动态模式探针
agent-target: 目标服务模拟
```

**提供接入入口**   
`maven` 或 `META-INF/MANIFEST.MF`
```
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-jar-plugin</artifactId>
    <version>3.0.2</version>
    <configuration>
        <archive>
            <manifest>
                <addClasspath>true</addClasspath>
            </manifest>
            <manifestEntries>
                <Premain-Class>com.zjut.study.ChangeCode</Premain-Class>
                <!--     为true时表示能够重新定义class     -->
                <Can-Redefine-Classes>true</Can-Redefine-Classes>
                <!--     为true时表示能够重新转换class，实现字节码替换     -->
                <Can-Retransform-Classes>true</Can-Retransform-Classes>
            </manifestEntries>
        </archive>
    </configuration>
</plugin>
```

**准备最终资料**  
准备需要的探针业务材料，如当前业务中西要的`Fruit`修改后的类文件

## 1.1 静态
探针业务开发 [agent-static](./agent-static)
```
// 探针暴露接口中必须要实现的方法
public static void premain(String agentArgs, Instrumentation inst); // 该签名方法的优先级高
public static void premain(String agentArgs);
```

将打包好的探针jar包作为启动参数`-javaagent:F:\Workspace\agent-static.jar`加入到目标服务即可实现探针的业务。

## 1.2 动态
探针业务开发 [agent-attach](./agent-attach)
```
// 探针暴露接口中必须要实现的方法
public static void agentmain(String agentArgs, Instrumentation inst)
```

将探针jar包接入到准备探业务服务的服务中`com.zjut.study.dynamic.attach.AgentAttach`。启动完目标服务后启动探测服务获取到目标服务探测目标服务执行探针业务。
