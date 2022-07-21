package com.zjut.study.boot;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultSingletonBeanRegistry;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * @author jack
 */
@SpringBootApplication
@RestController
public class StudySpringBootApplication {
    private static ConfigurableApplicationContext context;
    public static void main(String[] args) {
        context = SpringApplication.run(StudySpringBootApplication.class, args);

        // 添加一个钩子程序，在jvm最终关闭之前执行最后的动作
        Runtime.getRuntime().addShutdownHook(new Thread(() ->{
            System.out.println("最终还是钩子抗下了所有");
        }));

        // beanFactory相关
        try {
            beanFactory(context);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 查看BeanFactory中相关内容
     * @param context
     * @throws NoSuchFieldException
     */
    private static void beanFactory(ConfigurableApplicationContext context) throws NoSuchFieldException, IllegalAccessException {
        // 通过反射获取单例池
        Field singletonObjects = DefaultSingletonBeanRegistry.class.getDeclaredField("singletonObjects");
        singletonObjects.setAccessible(true);
        ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
        Map<String, Object> map = (Map<String, Object>) singletonObjects.get(beanFactory);
        System.out.printf("单例池中有%d个bean%n", map.size());
    }


    /**
     * 优雅下线服务的方式(kill -9 会导致当前线程及子线程任务不能执行完)：
     * 1. 添加spring-boot-starter-actuator依赖，通过/actuator/shutdown接口关闭
     * 2. 本接口中方法，关闭主程序启动时的context
     * 3. 4. 方案如下演示
     * 5. springboot启动时会将pid写入一个app.pid文件，路径可指定，可通过命令 cat /Users/xxx/app.id | xargs kill 命令直接停止服务, 可写一个停止程序将服务停止。
     *      SpringApplication application = new SpringApplication(StudyApplication.class);
     *      application.addListeners(new ApplicationPidFileWriter("/Users/xxx/app.pid"));
     *      application.run();
     */
    @GetMapping("/close")
    public String closeApplication() {
        // 3. 直接关闭主线程启动context
        // 异步，否则拿不到接口结果
        CompletableFuture.runAsync(()-> context.close());

        // 4. 获取退出码，系统调用正常退出码关闭jvm
//        int exitCode = SpringApplication.exit(context, (ExitCodeGenerator) () -> 0);
//        CompletableFuture.runAsync(()-> System.exit(exitCode));
        return "success";
    }
}
