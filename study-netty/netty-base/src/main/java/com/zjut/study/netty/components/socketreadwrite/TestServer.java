package com.zjut.study.netty.components.socketreadwrite;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务端
 */
public class TestServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8080);
        Socket accept = serverSocket.accept();

        // 读线程
        new Thread(() -> {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(accept.getInputStream()));
                while (true) {
                    System.out.println(reader.readLine());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        // 写线程
        new Thread(() -> {
            try {
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(accept.getOutputStream()));
                for (int i=0; i< 100; i++) {
                    writer.write(String.valueOf(i));
                    writer.newLine();
                    writer.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}
