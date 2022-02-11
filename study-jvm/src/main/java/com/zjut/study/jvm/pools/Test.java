package com.zjut.study.jvm.pools;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Test {
    static int a;
    static int b = 16;
    static {
        int c = 9;
    }
    public Test() {
        System.out.println("jack");
    }
    public static void main(String[] args) throws IOException {
        // 1.1 简单案例一
//        String s1 = "a";
//        String s2 = "b";
//        String s3 = "ab";
//        String s4 = s1 + s2;
//        System.out.println(s3 == s4);
//        String s5 = "a" + "b";
//        System.out.println(s3 == s5);


        // 1.2 简单案例二
//        String s = new String("a") + new String("b");
////        String ab = "ab";
//        String saf = s.intern();
//        System.out.println(saf == "ab");
//        System.out.println(s == "ab");
//        System.out.println(saf == s);


          // 2.
//        System.out.println("hello word");

        // 3. 运行时加载到字符串池
//        System.out.println();
//        System.out.println("1");  // 2223
//        System.out.println("2");
//        System.out.println("3");
//        System.out.println("4");
//        System.out.println("0"); // 2227
//        System.out.println("1");
//        System.out.println("2");
//        System.out.println("3");
//        System.out.println("4");
//        System.out.println("0");

        // 4. 字符串内部的StringBuild拼接
//        String s = new String("a") + new String("b");
//        System.out.println(s);

        // 5. 字符串常量拼接的原理是编译期优化
//        String s3 = "ab";
//        String s5 = "a" + "b";
//        System.out.println(s3 == s5);

        // 6. intern方法将字符串放入到字符串池(1.8)
//        String s1 = "a";
//        String s2 = "b";
//        String s4 = s1 + s2;
//        String s5 = "ab";
//        String s6 = s4.intern();
////        String s5 = "ab";
//        System.out.println(s5 == s4);
//        System.out.println(s5 == s6);


        // 7. 通过内存溢出查看StringTable位置
        // -Xmx10m -XX:-UseGCOverheadLimit
//        List<String> list = new ArrayList<>();
//        int i = 0;
//        try {
//            for (int j=0; j<5000000; j++) {
//                list.add(String.valueOf(j).intern());
//                i++;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            System.out.println(i);
//        }


        // 8. StaringTable的垃圾回收
        // -Xmx10m -XX:+PrintStringTableStatistics -XX:+PrintGCDetails -verbose:gc
        // 8.1
//        try {
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//        }

        // 8.2
//        int i = 0;
//        try {
//            for (int j=0; j<100; j++) {
//                String.valueOf(j).intern();
//                i++;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            System.out.println(i);
//        }

        // 8.3
//        int i = 0;
//        try {
//            for (int j=0; j<1000; j++) {
//                String.valueOf(j).intern();
//                i++;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            System.out.println(i);
//        }


        // 9. 通过修改StringTable桶的个数
        // -XX:+PrintStringTableStatistics -XX:+PrintGCDetails -verbose:gc -XX:StringTableSize=20000
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\WEIYI\\Desktop\\射雕英雄传.txt"), "GB2312"))) {
//            String line = null;
//            long start = System.nanoTime();
//            while (true) {
//                line = reader.readLine();
//                if (line == null) break;
//                line.intern();
//            }
//            System.out.println("cost:" + (System.nanoTime()-start)/1000000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        // 10. 通过jvisualvm观察空间对比
//        List<String> address = new ArrayList<>();
//        System.in.read();
//        for (int i=0; i<30; i++) {
//            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\WEIYI\\Desktop\\射雕英雄传.txt"), "GB2312"))) {
//                String line = null;
//                long start = System.nanoTime();
//                while (true) {
//                    line = reader.readLine();
//                    if (line == null) break;
//                    address.add(line);
////                    address.add(line.intern());
//                }
//                System.out.println("cost:" + (System.nanoTime()-start)/1000000);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        System.in.read();
//        System.out.println("结束");

    }
}
