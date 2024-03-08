

package com.liaocyu.zookeeper.curatorapi.operaasyncznode;

import com.liaocyu.zookeeper.callbak.BackgroundCallback;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;



/**
 * @Name: CuratorDemo
 * @Description: 异步调用
 * @Author: ahao
 * @Date: 2024/1/10 3:29 PM
 */


@Slf4j
@Component("operaAsyncZNode")
public class OperaAsyncZNode{

    @Autowired
    private CuratorFramework client;


    /**
     * 异步调用
     */

    public void operaAsyncZNode() throws Exception {
        log.info("。。。。。。。。。。。。。容器初始化完毕。。。。。。。。。。。。。。");
        TimeUnit.SECONDS.sleep(3);

        log.info("新增节点");
        // 创建节点
        client.create()
                // 如果需要，递归创建节点
                .creatingParentsIfNeeded()
                // 指定创建节点类型
                .withMode(CreateMode.EPHEMERAL)
                // 节点路径和数据
                .forPath("/liaocyu/test11111","this is a book".getBytes(StandardCharsets.UTF_8));

        // 睡眠1s
        TimeUnit.SECONDS.sleep(1);

        // 异步回调
        BackgroundCallback callback = new BackgroundCallback() {
            @Override
            public void processResult(CuratorFramework client, CuratorEvent event) throws Exception {
                log.info("时间类型：{}",event.getType());
            }
        };
        client.delete().deletingChildrenIfNeeded().inBackground(callback).forPath("/liaocyu/test11111");

    }


}



