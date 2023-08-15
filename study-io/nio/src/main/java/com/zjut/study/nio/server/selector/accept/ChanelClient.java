package com.zjut.study.nio.server.selector.accept;

import java.net.Socket;

public class ChanelClient {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8080)) {
            System.out.println(socket);
            socket.getOutputStream().write("hello".getBytes());
            System.in.read();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
