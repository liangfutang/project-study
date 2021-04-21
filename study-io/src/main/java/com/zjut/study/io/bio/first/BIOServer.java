package com.zjut.study.io.bio.first;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class BIOServer {

    public final static String HOST = "127.0.0.1";
    public final static int PORT = 7788;

    public static void main(String[] args) {

        ThreadPoolExecutor pool = new ThreadPoolExecutor(4, 8, 1, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>(20));

        try (ServerSocket serverSocket = new ServerSocket()) {
            serverSocket.bind(new InetSocketAddress(HOST, PORT));
            while (true) {
                Socket socket = serverSocket.accept();
                // 对接收到的客户端做处理
                pool.execute(new BIOServerRead(socket));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
