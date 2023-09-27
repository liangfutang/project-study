package com.zjut.study.netty.components.socketreadwrite;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * 客户端
 */
public class TestClient {
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("localhost", 8080);

        // 读线程
        new Thread(() -> {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
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
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                for (int i=0; i<100; i++) {
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
