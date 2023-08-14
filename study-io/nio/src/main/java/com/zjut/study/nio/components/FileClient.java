package com.zjut.study.nio.components;

import cn.hutool.core.date.StopWatch;
import com.zjut.study.common.junit.CommonJunitFilter;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * File及Channel相关
 * @author tlf
 */
@Slf4j
public class FileClient extends CommonJunitFilter {

    @Test
    public void fileChannelTransferTo() {
//        FileChannel from = new FileInputStream("data.txt").getChannel();
//        FileChannel to = new FileOutputStream("to.txt").getChannel();
        try (
                FileChannel from = new RandomAccessFile("C:\\Users\\tlf\\Desktop\\code\\words2.txt", "r").getChannel();
                FileChannel to = new RandomAccessFile("C:\\Users\\tlf\\Desktop\\code\\to.txt", "rw").getChannel();
        ) {
            // 效率高，底层会利用操作系统的零拷贝进行优化, 一次最多 2g 数据
            long size = from.size();
            // left 变量代表还剩余多少字节
            for (long left=size; left>0;) {
                log.info("position:{},left:{}", (size-left), left);
                left -= from.transferTo(size-left, left, to);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void fileCopy() throws IOException {
        StopWatch watch = new StopWatch();
        watch.start();
        String source = "C:\\Users\\tlf\\Desktop\\code";
        String target = "C:\\Users\\tlf\\Desktop\\codeaaa";
        Files.walk(Paths.get(source)).forEach(path -> {
            try {
                String targetName = path.toString().replace(source, target);
                // 如果被复制的是文件夹，创建文件夹
                if (Files.isDirectory(path)) {
                    Files.createDirectory(Paths.get(targetName));
                }
                // 普通文件
                else if (Files.isRegularFile(path)) {
                    Files.copy(path, Paths.get(targetName));
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        watch.stop();
        System.out.println("耗时:" + watch.getTotalTimeMillis());
    }

    @Test
    public void fileCount() throws IOException {
        AtomicInteger count = new AtomicInteger();
        Files.walkFileTree(Paths.get("D:\\java"), new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith("jar")) {
                    count.incrementAndGet();
                }
                return super.visitFile(file, attrs);
            }
        });
        System.out.println("jar文件数量:" + count);
    }

    @Test
    public void fileAndDirectCount() throws IOException {
        AtomicInteger fileCount = new AtomicInteger();
        AtomicInteger dirCount = new AtomicInteger();
        Files.walkFileTree(Paths.get("D:\\java"), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println(file);
                fileCount.incrementAndGet();
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("====>"+dir);
                dirCount.incrementAndGet();
                return super.preVisitDirectory(dir, attrs);
            }
        });
        System.out.println("文件夹数量:" + dirCount);
        System.out.println("文件数量:" + fileCount);
    }

    @Test
    public void fileDelete() throws IOException {
        String dir = "C:\\Users\\tlf\\Desktop\\code - 副本";
        // 如果直接删除，因为里面还有文件，所以会报‘DirectoryNotEmptyException’异常
//        Files.delete(Paths.get(dir));
        Files.walkFileTree(Paths.get(dir), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return super.postVisitDirectory(dir, exc);
            }
        });
    }
}
