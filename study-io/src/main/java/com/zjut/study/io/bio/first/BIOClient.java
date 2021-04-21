package com.zjut.study.io.bio.first;

import lombok.extern.slf4j.Slf4j;

import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@Slf4j
public class BIOClient implements Runnable{

    private String serverHost;
    private int serverPort;

    public BIOClient(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    @Override
    public void run() {
        try (Socket socket = new Socket("127.0.0.1", 7788);
             OutputStream outputStream = socket.getOutputStream()) {
            Scanner sin = new Scanner(System.in);
            while (sin.hasNext()) {
                String content = sin.nextLine();
                outputStream.write(content.getBytes(StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            log.error("推送异常:", e);
        }
    }

    public static void main(String[] args) {
        BIOClient bioClient = new BIOClient(BIOServer.HOST, BIOServer.PORT);
        new Thread(bioClient, "一个小傻逼").start();
    }
}
