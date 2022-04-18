package com.zjut.study.patterns.proxy.dynamicproxy.jdk;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 通过Javassist类库逐步演示jdk内部动态代理
 *
 * Javassist ---> ASM ---> 编辑JVM指令
 */
public class JavassistProxyClient {

    /**
     * 通过类池创建创建一个类，为该类添加对应的方法属性等
     * 经实验发现，通过该池创建的对象，不需要实现接口的所有方法，添加的方法可以是接口中没有的，可以通过反射调用添加的方法
     */
    @Test
    public void createProxy01() throws NotFoundException, CannotCompileException, InstantiationException, IllegalAccessException {
        // 类池: Javassist ---> ASM ---> 编辑JVM指令
        // 1. 用类池创建一个类
        ClassPool classPool = new ClassPool();
        // 1.1 添加classLoader
        classPool.appendSystemPath();
        CtClass testServiceImpl = classPool.makeClass("TestServiceImpl");
        // 2. 为代理类添加一个接口
        testServiceImpl.addInterface(classPool.get(TestService.class.getName()));
        // 3. 为代理类创建一个方法并添加到类中, $1表示第一个方法参数
        CtMethod sayHelloMethod = CtNewMethod.make(CtClass.voidType, "sayHello", new CtClass[]{classPool.get(String.class.getName())},
                new CtClass[0], "{System.out.println(\"hello:\" + $1);}", testServiceImpl);
        testServiceImpl.addMethod(sayHelloMethod);
        // 4. 实例化对象
        Class<TestService> aClass = (Class<TestService>) testServiceImpl.toClass();
        TestService testService = aClass.newInstance();
        // 5. 调用代理对象方法
        testService.sayHello("jack");
    }
    public interface TestService {
        void sayHello(String name);
    }

    /**
     * 代理接口和代理方法通过外部传进去
     */
    @Test
    public void createProxy02() throws NotFoundException, CannotCompileException, InstantiationException, IllegalAccessException {
        TestService testService = this.createProxy02(TestService.class, "{System.out.println(\"hello:\" + $1);}");
        testService.sayHello("jack");
    }
    private <T> T createProxy02(Class<T> classInterface, String bodyStr) throws NotFoundException, CannotCompileException, InstantiationException, IllegalAccessException {
        // 1. 用类池创建一个类
        ClassPool classPool = new ClassPool();
        // 1.1 添加classLoader
        classPool.appendSystemPath();
        CtClass testServiceImpl = classPool.makeClass("TestServiceImpl");
        // 2. 为代理类添加一个接口
        testServiceImpl.addInterface(classPool.get(classInterface.getName()));
        // 3. 为代理类创建一个方法并添加到类中, $1表示第一个方法参数
        CtMethod sayHelloMethod = CtNewMethod.make(CtClass.voidType, "sayHello", new CtClass[]{classPool.get(String.class.getName())},
                new CtClass[0], bodyStr, testServiceImpl);
        testServiceImpl.addMethod(sayHelloMethod);
        // 4. 实例化对象
        Class<T> aClass = (Class<T>) testServiceImpl.toClass();
        return aClass.newInstance();
    }

    /**
     *
     * @throws Exception
     */
    @Test
    public void createProxy03() throws Exception {
        TestService03 testService = this.createProxy03(TestService03.class, new InvocationHandler() {
            @Override
            public Object invoke(String method, Object[] args) {
                if (Objects.equals(method, "sayHello")) {
                    System.out.println("hello:" + args[0]);
                }
                if (Objects.equals(method, "sayHello1")) {
                    System.out.println("hello:" + args[0] + args[1]);
                }
                return "jack";
            }
        });

        testService.sayHello("jack");
        testService.sayHello1("jack", 11);
        String jack = testService.sayHello2("jack");
    }
    public interface TestService03 {
        void sayHello(String name);
        void sayHello1(String name, int age);
        String sayHello2(String name);
    }
    private int count;
    private <T> T createProxy03(Class<T> classInterface, InvocationHandler invocationHandler) throws NotFoundException, CannotCompileException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        // 1. 用类池创建一个类
        ClassPool classPool = new ClassPool();
        classPool.appendSystemPath();
        CtClass proxy = classPool.makeClass("$proxy" + count++);
        proxy.addInterface(classPool.get(classInterface.getName()));
        // 2. 添加一个属性
        CtField field = CtField.make("public com.zjut.study.patterns.proxy.dynamicproxy.jdk.JavassistProxyClient.InvocationHandler handler = null;", proxy);
        proxy.addField(field);
        // 3. 填充所有的方法
        String methodBody = "return ($r)this.handler.invoke(\"%s\", $args);";
        String voidMethodBody = "this.handler.invoke(\"%s\", $args);";
        for (Method method : classInterface.getMethods()) {
            // 3.1 方法返回类型
            CtClass returnType = classPool.get(method.getReturnType().getName());
            String name = method.getName();
            CtClass[] parameters = this.toCtCLass(classPool, method.getParameterTypes());
            CtClass[] exceptions = this.toCtCLass(classPool, method.getExceptionTypes());
            String body = "";
            if (Void.class.equals(method.getReturnType())) {
                body = voidMethodBody;
            } else {
                body = methodBody;
            }
            CtMethod ctMethod = CtNewMethod.make(returnType, name, parameters, exceptions, String.format(body, name), proxy);
            proxy.addMethod(ctMethod);
        }
        Class<T> aClass = classPool.toClass(proxy);
        T t = aClass.newInstance();
        aClass.getField("handler").set(t, invocationHandler);
        return t;
    }
    private CtClass[] toCtCLass(ClassPool classPool, Class<?>[] classes) {
        return Arrays.stream(classes).map(c -> {
            try {
                return classPool.get(c.getName());
            } catch (NotFoundException e) {
                throw new RuntimeException(e);
            }
        }).toArray(CtClass[]::new);
    }
    public interface InvocationHandler {
        Object invoke(String method, Object[] args);
    }
//    public class InvocationHandlerImpl implements InvocationHandler{
//        @Override
//        public Object invoke(String method, Object[] args) {
//            System.out.println("hello");
//            return null;
//        }
//    }
}
