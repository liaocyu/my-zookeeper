

package com.liaocyu.zookeeper.curatorapi.putnode;

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
 * @Description:
 * @Author: ahao
 * @Date: 2024/1/10 3:29 PM
 */


@Slf4j
@Component("putZNode")
public class PutZNode{
    @Autowired
    private CuratorFramework client;


    /**
     * 更新节点
     *
     */

    public void putZNode() throws Exception {
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
                .forPath("/liaocyu/test222222","this is a book".getBytes(StandardCharsets.UTF_8));

        // 睡眠1s
        TimeUnit.SECONDS.sleep(1);

        // 读取节点的数据内容
        byte[] bytes = client.getData().forPath("/liaocyu/test222222");
        String s = new String(bytes,StandardCharsets.UTF_8);
        log.info("读取到的数据内容：{}",s);

        // 更新节点
        client.setData().forPath("/liaocyu/test222222","这是一本书".getBytes(StandardCharsets.UTF_8));

        // 再次读取节点的数据内容
        byte[] bytes2 = client.getData().forPath("/liaocyu/test222222");
        String s2 = new String(bytes2,StandardCharsets.UTF_8);
        log.info("读取到的数据内容：{}",s2);
    }

}



