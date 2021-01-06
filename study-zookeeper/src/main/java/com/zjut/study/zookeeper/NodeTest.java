package com.zjut.study.zookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.GetChildrenBuilder;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.*;
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

    @Test
    public void connection03() throws Exception {
        /**
         * baseSleepTimeMs：初始的重试等待时间
         * maxRetries：最多重试次数
         *
         *
         * ExponentialBackoffRetry：重试一定次数，每次重试时间依次递增
         * RetryNTimes：重试N次
         * RetryOneTime：重试一次
         * RetryUntilElapsed：重试一定时间
         */
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        CuratorFramework client = CuratorFrameworkFactory.builder()
                        .connectString(connectionURL)
                        .sessionTimeoutMs(sessionTimeout)
                        .connectionTimeoutMs(connectionTimeout)
                        .retryPolicy(retryPolicy)
                        .namespace("base")
                        .build();
        client.start();

        List<String> children = client.getChildren().forPath("services");
    }

    ZooKeeper zooKeeper = null;
    @Test
    public void watch01() throws IOException, InterruptedException {
        zooKeeper = new ZooKeeper(connectionURL, sessionTimeout, watchedEvent -> {
            List<String> children = null;
            try {
                children = zooKeeper.getChildren("/", true);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (CollectionUtils.isNotEmpty(children)) {
                System.out.println("==================start======================");
                children.forEach(System.out::println);
                System.out.println("==================end======================");
            }
        });

        // 新增一个节点
        new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                zooKeeper.create("/jack", "make".getBytes(), ZooDefs.Ids.READ_ACL_UNSAFE, CreateMode.EPHEMERAL);
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        // 删除一个节点
        new Thread(() -> {
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            try {
                Stat stat = new Stat();
                zooKeeper.getData("/jack", false, stat);
                zooKeeper.delete("/jack", stat.getVersion());
            } catch (KeeperException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();

        Thread.sleep(30000);
    }
}
