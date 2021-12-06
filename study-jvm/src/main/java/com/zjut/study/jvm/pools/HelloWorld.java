package com.zjut.study.jvm.pools;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class HelloWorld {
    public static void main(String[] args) throws IOException {
//1.         System.out.println("hello world");
//        String s1 = "a";
//        String s2 = "b";
//        String s3 = "ab";
//        String s4 = s1 + s2;
//        System.out.println(s3 == s4);
//        String s5 = "a" + "b";
//        System.out.println(s3 == s5);


        // 2. 串池的延迟加载
//        int length = args.length;
//
//        System.out.println();  // 2220
//        System.out.println("1");  // 2221
//        System.out.println("2");  // 2222
//        System.out.println("3");
//        System.out.println("4");
//        System.out.println("0");  // 2226
//        System.out.println("1");
//        System.out.println("2");
//        System.out.println("3");
//        System.out.println("4");
//        System.out.println("0");  // 2226

        // 3.
//        String s = new String("a") + new String("b");  // new String("ab")仅仅只是在堆中
//        String ab = "ab";
//
//        String saf = s.intern();
//
//        System.out.println(saf == "ab");
//        System.out.println(s == "ab");
//        System.out.println(saf == s);

        // 4.
//        String s1 = "a";
//        String s2 = "b";
//        String s3 = "a" + "b";
//        String s4 = s1 + s2;
//        String s5 = "ab";
//        String s6 = s4.intern();
//
//        System.out.println(s3 == s4);  // false
//        System.out.println(s3 == s5);  // true
//        System.out.println(s3 == s6);  // true
//
//        String x2 = new String("c") + new String("d");
//        String x1 = "cd";
//        x2.intern();
//        System.out.println(x1 == x2);

        // 5. 通过内存溢出查看StringTable位置
        // 如果是1.6的话会爆出 OOM:PermGen space
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

        // 6. StaringTable的垃圾回收
        // -Xmx10m -XX:+PrintStringTableStatistics -XX:+PrintGCDetails -verbose:gc
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

        // 7. 优化
        // 7.1 通过调整StringTable桶的数量，桶少会增加碰撞的概率，加大了查询的效率 -XX:StringTableSize=20000,减小桶个数，整个时间变大
//        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\WEIYI\\Desktop\\射雕英雄传.txt"), "utf-8"))) {
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

        // 7.2 空间观察,jvisure  -XX:NewSize=200m
//        List<String> address = new ArrayList<>();
//        System.in.read();
//        for (int i=0; i<10; i++) {
//            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\WEIYI\\Desktop\\射雕英雄传.txt"), "utf-8"))) {
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

    }
}
