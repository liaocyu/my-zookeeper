package com.liaocyu.zookeeper.lock;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.recipes.locks.InterProcessReadWriteLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author : create by lcy
 * @Project : liaocyu_zookeeper
 * @createTime : 2024/3/8 20:41
 * @description :
 */
@Component("reentrantReadWriteLock")
@Slf4j
public class ReentrantReadWriteLock {

    @Autowired
    private CuratorFramework client;

    String dataPath = "/ahao/data";

    public void lock() throws InterruptedException {
        log.info("。。。。。。。。。。。。。容器初始化完毕。。。。。。。。。。。。。。");

        String lockPath = "/ahao/lock";
        TimeUnit.SECONDS.sleep(3);

        InterProcessReadWriteLock readWriteLock = new InterProcessReadWriteLock(client, lockPath);
        // 获取读锁
        InterProcessMutex readLock = readWriteLock.readLock();
        // 获取写锁
        InterProcessMutex writeLock = readWriteLock.writeLock();

        // 保证并发执行，当前时间的秒针部分为30的整数倍则结束循环
        int seconds = LocalDateTime.now().getSecond();
        while (seconds/30 != 0){
            seconds = LocalDateTime.now().getSecond();
        }

        for (int j = 0; j < 2; j++) {
            // 读线程
            new Thread(() -> {
                for (int i = 0; i < 10; i++) {
                    try {
                        // 加锁
                        if (readLock.acquire(3, TimeUnit.SECONDS)) {
                            doLock(false);
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    } finally {
                        // 释放锁
                        try {
                            readLock.release();
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }, "读线程"+j).start();
        }

        // 写线程
        new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                try {
                    // 加锁
                    if (writeLock.acquire(3, TimeUnit.SECONDS)) {
                        doLock(true);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    // 释放锁
                    try {
                        writeLock.release();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }, "写线程").start();

    }

    /**
     * 加操作
     * @param isAdd true 表示加1，false 表示不加，查询数据
     * @return
     * @throws Exception
     */
    public void doLock(boolean isAdd) throws Exception {
        // 获取数据节点中的值
        byte[] bytes = client.getData().forPath(dataPath);
        Integer integer = Integer.valueOf(new String(bytes));
        if (isAdd) {
            // 设置新值
            client.setData().forPath(dataPath, String.valueOf(integer + 1).getBytes(StandardCharsets.UTF_8));
            log.info("加1操作后：{}", integer);
        } else {
            log.info("查询数据：{}", integer);
        }
    }
}
