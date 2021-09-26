package com.zjut.study.netty.nio.c2;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class TestFilesCopy {

    @Test
    public void test01() throws IOException {
        String source = "src/main/java/com/zjut/study/netty/data/test1.txt";
        String to = "src/main/java/com/zjut/study/netty/data/to.txt";

        Files.walk(Paths.get(source)).forEach(path -> {
            try {
                String targetName = path.toString().replace(source, to);
                // 是目录
                if (Files.isDirectory(path)) {
                    Files.createDirectory(Paths.get(targetName));
                }
                // 是普通文件
                else if (Files.isRegularFile(path)) {
                    Files.copy(path, Paths.get(targetName));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
