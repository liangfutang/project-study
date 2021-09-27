package com.zjut.study.netty.nio.c4;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static com.zjut.study.netty.ByteBufferUtil.debugAll;

/**
 * 异步操作
 */
@Slf4j
public class AioFileChannel {

    @Test
    public void test01() throws IOException {
        try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get("src/main/java/com/zjut/study/netty/data/test1.txt"), StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocate(16);
            log.info("read begin...");
            channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    log.info("接收:{}", result);
                    attachment.flip(); // 切换读模式
                    debugAll(attachment);
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    log.error("失败:", exc);
                    attachment.flip();
                    debugAll(attachment);
                }
            });
            log.info("read begin...");
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.in.read();
    }
}
