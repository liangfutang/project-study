package com.zjut.study.io.bio.first;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

@Slf4j
public class BIOServerRead implements Runnable{

    private final Socket socket;

    public BIOServerRead(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();

            byte[] buffer = new byte[1024];
            while ((inputStream.read(buffer)) > 0) {
                System.out.println(new String(buffer, StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            log.error("接收异常:", e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
