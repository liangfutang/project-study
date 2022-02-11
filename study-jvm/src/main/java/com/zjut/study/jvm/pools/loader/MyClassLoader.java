package com.zjut.study.jvm.pools.loader;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 自定义类加载器
 */
public class MyClassLoader extends ClassLoader {

    private String claasFilePath;
    public MyClassLoader(String claasFilePath) {
        this.claasFilePath = claasFilePath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        // 获取class字节码数组
        byte[] classData = getDataByClass();

        Class<?> myClass = null;
        if (classData != null) {
            // 将class的字节码数组转换成Class类的实例
            myClass = defineClass(name, classData, 0, classData.length);
            System.out.println("成功将二进制类文件转为class文件");
        }
        return myClass;
    }

    /**
     * 将class文件转为字节码数组
     * @return
     */
    private byte[] getDataByClass() {

        File file = new File(claasFilePath);
        if (file.exists()) {
            try (FileInputStream in = new FileInputStream(file);
                 ByteArrayOutputStream out =new ByteArrayOutputStream();
            ) {
                byte[] buffer = new byte[1024];
                int size = 0;
                while ((size = in.read(buffer)) != -1) {
                    out.write(buffer, 0, size);
                }
                System.out.println("成功获取到类文件");
                return out.toByteArray();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        String classFilePath = "D:\\workspace\\public\\project-study\\study-jvm\\target\\classes\\com\\zjut\\study\\jvm\\pools\\loader\\TestCLassFile.class";
        MyClassLoader myClassLoader = new MyClassLoader(classFilePath);
        Class<?> aClass = myClassLoader.loadClass("com.zjut.study.jvm.pools.loader.TestCLassFile");
        System.out.println("aClass使用的类加载器是:" + aClass.getClassLoader());
        Method test = aClass.getDeclaredMethod("test", null);
        Object o = aClass.newInstance();
        test.invoke(o, null);
    }
}

class TestCLassFile {
    public void  test() {
        System.out.println("ha ha");
    }
}
