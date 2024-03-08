package com.liaocyu.zookeeper.lock;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMultiLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author : create by lcy
 * @Project : liaocyu_zookeeper
 * @createTime : 2024/3/8 21:03
 * @description :
 */
@Component("mutiShareLock")
@Slf4j
public class MutiShareLock {
    @Autowired
    private CuratorFramework client;

    // 存储数据节点的路径
    final String dataPath = "/ahao/data";


    public void lock() throws Exception {
        log.info("。。。。。。。。。。。。。容器初始化完毕。。。。。。。。。。。。。。");
        String lockPath = "/ahao/lock";
        String lockPath2 = "/ahao/lock2";
        TimeUnit.SECONDS.sleep(3);

        // 创建锁1
        InterProcessMutex mutex = new InterProcessMutex(client,lockPath);
        // 创建锁2
        InterProcessMutex mutex2 = new InterProcessMutex(client,lockPath2);
        List<InterProcessLock> arr = new ArrayList<>();
        arr.add(mutex);
        arr.add(mutex2);
        // 创建共享锁
        InterProcessMultiLock multiLock = new InterProcessMultiLock(arr);

        for (int i = 0; i < 3; i++) {
            new Thread(()->{
                try {
                    if (multiLock.acquire(5, TimeUnit.SECONDS)) {
                        log.info("获取到锁");
                        TimeUnit.SECONDS.sleep(3);
                    }else {
                        log.info("获取失败");
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    try {
                        multiLock.release();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            },"线程"+i).start();
        }

    }

}
