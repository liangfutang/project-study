package com.zjut.study.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * 创建远程接口，并声明远程方法
 * 提供一些远程计算方法
 * @author tlf
 */
public interface IRemoteCalculate extends Remote {

    /**
     * 远程接口方法必须抛出RemoteException异常
     */
    int add(int a,int b) throws RemoteException;
    int sub(int a,int b) throws RemoteException;
}
