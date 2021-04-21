package com.zjut.study.io.nio.first;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

@Slf4j
public class NIOServerRead implements Runnable{

    // 不要虚
    private SocketChannel socketChannel;

    public NIOServerRead(SelectionKey selectionKey) {
        this.socketChannel = (SocketChannel) selectionKey.channel();
    }

    @Override
    public void run() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        try {
            int len;
            while ((len=socketChannel.read(buffer)) > 0) {
                buffer.flip();
                System.out.println(new String(buffer.array(), 0, len));
                buffer.clear();
            }
        }catch (Exception e) {
            log.error("读取操作异常:", e);
        }
    }
}
