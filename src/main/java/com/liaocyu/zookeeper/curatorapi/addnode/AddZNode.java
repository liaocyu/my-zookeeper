
package com.liaocyu.zookeeper.curatorapi.addnode;

import com.liaocyu.zookeeper.callbak.BackgroundCallback;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * @Name: CuratorDemo
 * @Description:
 * @Author: ahao
 * @Date: 2024/1/10 3:29 PM
 */

@Slf4j
@Component("addZNode")
public class AddZNode {

    @Autowired
    private CuratorFramework client;
    /**
     * 新增节点
     */
    public void addZNode() throws Exception {
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
                .forPath("/liaocyu/dog","this is a book".getBytes(StandardCharsets.UTF_8));

        // 睡眠1s
        TimeUnit.SECONDS.sleep(1);

        // 读取节点的数据内容
        byte[] bytes = client.getData().forPath("/liaocyu/dog");
        String s = new String(bytes,StandardCharsets.UTF_8);
        log.info("读取到的数据内容：{}",s);

        // 判断节点是否存在并返回节点状态信息
        Stat stat = client.checkExists().forPath("/liaocyu/dog");
        log.info("读取节点状态信息：{}", stat);

        // 获取子节点
        List<String> list = client.getChildren().forPath("/liaocyu");
        log.info("读取子节点：{}", list);
    }

}


