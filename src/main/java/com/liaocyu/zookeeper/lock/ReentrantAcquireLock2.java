package com.liaocyu.zookeeper.lock;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author : create by lcy
 * @Project : liaocyu_zookeeper
 * @createTime : 2024/3/8 20:30
 * @description : 分布式非可重入锁
 *
 */
@Component("reentrantAcquireLock2")
@Slf4j
public class ReentrantAcquireLock2 {

    @Autowired
    private CuratorFramework client;

    // 存储数据节点的路径
    final String dataPath = "/ahao/data";

    public void lock() throws InterruptedException {
        log.info("。。。。。。。。。。。。。容器初始化完毕。。。。。。。。。。。。。。");

        String lockPath = "/ahao/lock";
        TimeUnit.SECONDS.sleep(3);

        // 创建非可重入锁
        InterProcessSemaphoreMutex mutex = new InterProcessSemaphoreMutex(client, lockPath);
        // 调用测试方法
        share(mutex);

    }


    /**
     * 用来模拟临界资源的方法
     */
    public void share(final InterProcessLock mutex) {
        try {
            // 获取锁
            if (mutex.acquire(5, TimeUnit.SECONDS)) {
                log.info("第一次加锁成功");
            }
            // 再次获取锁
            if (!mutex.acquire(5, TimeUnit.SECONDS)) {
                log.info("第二次加锁失败了");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                // 获取多少次锁就要释放多少次
                mutex.release();
                mutex.release();
            } catch (Exception e) {
                log.error("释放锁失败：{}", e.getStackTrace());
                throw new RuntimeException(e);
            }
        }
    }

}


