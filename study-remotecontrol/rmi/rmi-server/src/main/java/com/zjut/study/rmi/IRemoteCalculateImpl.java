package com.zjut.study.rmi;

import lombok.extern.slf4j.Slf4j;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 实现远程接口及远程方法
 * @author tlf
 */
@Slf4j
public class IRemoteCalculateImpl extends UnicastRemoteObject implements IRemoteCalculate{

    /**
     * 必须定义构造方法，即使是默认的构造方法，也必须创建出来，因为必须要抛出RemoteException异常
     */
    protected IRemoteCalculateImpl() throws RemoteException {
        super();
        // UnicastRemoteObject.exportObject(this, 0);  // 如果不继承UnicastRemoteObject，就需要手工导出
    }

    @Override
    public int add(int a, int b) throws RemoteException {
        log.info("开始计算和:a:{} + b:{}", a, b);
        return a + b;
    }

    @Override
    public int sub(int a, int b) throws RemoteException {
        log.info("开始计算差:a:{} + b:{}", a, b);
        return a - b;
    }
}
