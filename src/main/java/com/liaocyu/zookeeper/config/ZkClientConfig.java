package com.liaocyu.zookeeper.config;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @Name: ZkClientConfig
 * @Description: Curator客户端配置类
 * @Author: ahao
 * @Date: 2024/1/10 3:52 PM
 */
@Configuration
@ConfigurationProperties(prefix = "apache.zookeeper")
@Setter
@Slf4j
public class ZkClientConfig {

    // 服务器连接地址，集群模式则使用逗号分隔如：ip1:host,ip2:host
    private String connectUrl;

    // 会话超时时间：单位ms
    private Integer sessionTimeout;

    // 连接超时时间：单位ms
    private Integer connectionTimeout;

    // ACL权限控制，验证策略
    private String scheme;

    // 验证内容id
    private String authId;

    @Autowired
    private CuratorRetryPolicy curatorRetryPolicy;

    @Bean
    public CuratorFramework curatorFramework(){
        CuratorFramework curatorFramework = CuratorFrameworkFactory.builder()
                .connectString(connectUrl)
                .sessionTimeoutMs(sessionTimeout)
                .connectionTimeoutMs(connectionTimeout)
                // 权限认证
                //.authorization(scheme,authId.getBytes(StandardCharsets.UTF_8))
                // 重试策略
                .retryPolicy(new ExponentialBackoffRetry(curatorRetryPolicy.getBaseSleepTime()
                        ,curatorRetryPolicy.getMaxRetries()
                        ,curatorRetryPolicy.getMaxSleep()))
                .build();
        // 启动客户端
        curatorFramework.start();
        return curatorFramework;
    }

}

