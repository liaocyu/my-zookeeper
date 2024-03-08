package com.liaocyu.zookeeper.lock;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessLock;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author : create by lcy
 * @Project : liaocyu_zookeeper
 * @createTime : 2024/3/8 20:14
 * @description : 重入式抢占锁
 * 注：在 zkCli 客户端 输入： create /ahao/data 40
 */
@Component("reentrantAcquireLock")
@Slf4j
public class ReentrantAcquireLock {

    @Autowired
    private CuratorFramework client;

    final String dataPath = "/ahao/data";

    public void lock() throws InterruptedException {
        log.info("。。。。。。。。。。。。。容器初始化完毕。。。。。。。。。。。。。。");

        String lockPath = "/ahao/lock";
        TimeUnit.SECONDS.sleep(3);

        // 创建可重入锁
        InterProcessMutex mutex = new InterProcessMutex(client,lockPath);
        // 创建一个线程池
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // 保证并发执行，当前时间的秒针部分为0则结束循环
        int seconds = LocalDateTime.now().getSecond();
        while (seconds != 0){
            seconds = LocalDateTime.now().getSecond();
        }

        // 提交两个任务
        for (int i = 0; i < 2; i++) {
            executorService.submit(() -> {
                // 循环执行
                while (share(mutex,1)) {
                    try {
                        // 睡眠0.5秒
                        TimeUnit.MILLISECONDS.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }

    }

    /**
     * 用来模拟临界资源的方法
     */
    public boolean share(final InterProcessLock mutex, int n){
        boolean b = true;
        try {
            // 获取锁
            if (mutex.acquire(3, TimeUnit.SECONDS)) {
                // 减1操作
                b = doLock(n);
                // 最多执行三次
                if (b && n < 3){
                    b = share(mutex,n+1);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                mutex.release();
                return b;
            } catch (Exception e) {
                log.error("释放锁失败");
                throw new RuntimeException(e);
            }
        }
    }

    // 减1操作
    private boolean doLock(int n) throws Exception {
        // 获取数据节点中的值
        byte[] bytes = client.getData().forPath(dataPath);
        String s = new String(bytes);
        Integer integer = Integer.valueOf(s);
        // 判断是否为0
        if(integer > 0){
            // 设置新值
            client.setData().forPath(dataPath,String.valueOf(integer-1).getBytes(StandardCharsets.UTF_8));
            log.info("第{}次加锁当前值：{}",n,integer);
            return true;
        }else {
            log.info("任务已完成。。。。");
            return false;
        }
    }

}
