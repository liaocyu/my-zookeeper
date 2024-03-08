package com.liaocyu.zookeeper.lock;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.locks.InterProcessSemaphoreV2;
import org.apache.curator.framework.recipes.locks.Lease;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @author : create by lcy
 * @Project : liaocyu_zookeeper
 * @createTime : 2024/3/8 21:00
 * @description : 共享信号零
 */
@Component("semaphoreV2Lock")
@Slf4j
public class SemaphoreV2Lock {

    @Autowired
    private CuratorFramework client;

    String dataPath = "/ahao/data";

    public void lock() throws Exception {
        log.info("。。。。。。。。。。。。。容器初始化完毕。。。。。。。。。。。。。。");
        String lockPath = "/ahao/lock";
        TimeUnit.SECONDS.sleep(3);

        // 创建共享信号量
        InterProcessSemaphoreV2 semaphoreV2 = new InterProcessSemaphoreV2(client,lockPath,2);

        // 保证并发执行，当前时间的秒针部分为30的整数倍则结束循环
        int seconds = LocalDateTime.now().getSecond();
        while (seconds/30 != 0){
            seconds = LocalDateTime.now().getSecond();
        }

        // 创建线程争夺许可证
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                Lease acquire = null;
                try {
                    acquire = semaphoreV2.acquire(5, TimeUnit.SECONDS);
                    if (acquire != null){
                        log.info("抢到许可证，参与竞争的节点：{}",semaphoreV2.getParticipantNodes());
                        // 睡眠4秒
                        TimeUnit.SECONDS.sleep(4);
                    }else {
                        log.info("抢到许可证失败");
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }finally {
                    if (acquire != null){
                        semaphoreV2.returnLease(acquire);
                    }
                }
            }, "线程"+i).start();
        }

    }

}
