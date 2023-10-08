package com.zjut.study.rmi;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * 启动RMI注册服务并进行对象的注册
 * @author tlf
 */
public class CalculateServer {
    public static void main(String[] args) {
        try {
            //通过LocateRegistry.createRegistry(1099)(默认端口)方式启动RMI注册服务
            Registry registry = LocateRegistry.createRegistry(1099);

            //创建远程对象的一个或多个实例
            IRemoteCalculate iRemoteCalculate = new IRemoteCalculateImpl();

            //将c1对象注册到RMI注册服务器上，并且命名为cal（也就是map中的key）
            registry.rebind("cal", iRemoteCalculate);

//            如果要把cal对象注册到另外一台启动了RMI注册服务的机器上，则写上ip地址
//            Naming.rebind("rmi://127.0.0.1:1099/cal", iRemoteCalculate);
            System.out.println("注册服务已经准备好了！");

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
