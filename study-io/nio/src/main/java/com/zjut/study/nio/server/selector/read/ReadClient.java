package com.zjut.study.nio.server.selector.read;

import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ReadClient {
    public static void main(String[] args) {
        try (Socket socket =new Socket("localhost", 8080)) {
            System.out.println("建立连接: " + socket);
            socket.getOutputStream().write("hello".getBytes(StandardCharsets.UTF_8));
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
