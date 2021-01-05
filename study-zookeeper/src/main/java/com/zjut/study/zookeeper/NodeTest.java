package com.zjut.study.zookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.ACL;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

public class NodeTest {

    private final String connectionURL = "localhost:2181";
    private final int sessionTimeout  = 5000;
    private final int connectionTimeout = 5000;

    /**
     * 使用原始的zookeeper api连接
     * @throws IOException
     * @throws InterruptedException
     * @throws KeeperException
     */
    @Test
    public void connection01() throws IOException, InterruptedException, KeeperException {
        CountDownLatch latch = new CountDownLatch(1);
        ZooKeeper zooKeeper = new ZooKeeper(connectionURL, sessionTimeout, watchedEvent -> {
            if (Watcher.Event.KeeperState.SyncConnected == watchedEvent.getState()) {
                // 监视器监视到已经连接完毕再进行后面的操作
                latch.countDown();
                System.out.println("已经获得到连接了");
            }
        });
        // 在连接完成之前不做任何操作
        latch.await();
        System.out.println("终于是连接完成啦");
        ZooKeeper.States ephemerals = zooKeeper.getState();
        System.out.println("打印接收到的结果:" + ephemerals);
    }

    /**
     * 使用ZkClient连接
     */
    @Test
    public void connection02() {
        ZkClient zkClient = new ZkClient(connectionURL, sessionTimeout, connectionTimeout);
        List<String> children = zkClient.getChildren("/");
        Map.Entry<List<ACL>, Stat> acl = zkClient.getAcl("/");

        System.out.println("打印出所有的结点:" + children);
        System.out.println("打印出所有的acl:" + acl);
    }
}
