package com.liaocyu.zookeeper.curatorapi.demo;

import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * @Name: CuratorDemo
 * @Description:
 * @Author: ahao
 * @Date: 2024/1/10 3:29 PM
 */
@Slf4j
@Component("curatorDemo")
public class CuratorDemo {

    @Autowired
    private CuratorFramework client;


    public void curatorDemo() throws Exception {
        log.info("。。。。。。。。。。。。。容器初始化完毕。。。。。。。。。。。。。。");
        TimeUnit.SECONDS.sleep(1);

        // 创建节点。如果没有设置节点属性，节点创建模式默认为持久化节点，内容默认为空
        client.create()
                // 如果需要，递归创建节点
                .creatingParentsIfNeeded()
                // 指定创建节点类型
                .withMode(CreateMode.EPHEMERAL)
                // 节点路径和数据
                .forPath("/dogliao","this is a book".getBytes(StandardCharsets.UTF_8));
    }

}

