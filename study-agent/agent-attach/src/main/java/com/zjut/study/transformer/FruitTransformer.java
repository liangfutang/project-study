package com.zjut.study.transformer;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.nio.file.Files;
import java.security.ProtectionDomain;

/**
 * 转换器
 * ClassFileTransformer是一个接口，只有一个transform方法，它在主程序的main方法执行前，装载的每个类都要经过transform执行一次
 * @author tlf
 */
public class FruitTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        // 要修改的类名
        if (!className.equals("com/zjut/study/model/Fruit")) {
            return classfileBuffer;
        }
        // 要修改的目标类
        String fileName="D:\\workspace\\public-project\\project-study\\study-agent\\agent-target\\Fruit.class";
        return getClassBytes(fileName);
    }

    /**
     * 根据输入的路径获读取到该类的字节码
     * @param fileName 待修改的类所在
     * @return 类
     */
    public static byte[] getClassBytes(String fileName){
        File file = new File(fileName);
        try(InputStream is = Files.newInputStream(file.toPath());
            ByteArrayOutputStream bs = new ByteArrayOutputStream()){
            long length = file.length();
            byte[] bytes = new byte[(int) length];

            int n;
            while ((n = is.read(bytes)) != -1) {
                bs.write(bytes, 0, n);
            }
            return bytes;
        }catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
