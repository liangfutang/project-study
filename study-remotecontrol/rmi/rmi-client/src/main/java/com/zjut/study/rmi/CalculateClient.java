package com.zjut.study.rmi;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 * 客户端查找远程对象，并调用远程方法
 * @author tlf
 */
public class CalculateClient {
    public static void main(String[] args) {
        try {
            //查找远程对象
            IRemoteCalculate c1 = (IRemoteCalculate) Naming.lookup("//127.0.0.1/cal");

//            Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1099);
//            IRemoteCalculate c1 = (IRemoteCalculate) registry.lookup("cal");

            //调用远程方法
            //结果会得到这个方法返回回来的数据，
            //而实际执行还是在另一端执行的
            System.out.println(c1.add(6, 3));
            System.out.println(c1.sub(5, 2));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
