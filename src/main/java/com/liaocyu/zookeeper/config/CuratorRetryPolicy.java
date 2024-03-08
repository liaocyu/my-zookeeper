package com.liaocyu.zookeeper.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Name: CuratorRetryPolicy
 * @Description: 重试策略参数
 * @Author: ahao
 * @Date: 2024/1/10 6:23 PM
 */
@ConfigurationProperties(prefix = "apache.retry-policy")
@Configuration
@Getter
@Setter
public class CuratorRetryPolicy {

    // 初始化间隔时间
    private Integer baseSleepTime;

    // 最大重试次数
    private Integer maxRetries;

    // 最大重试间隔时间
    private Integer maxSleep;

}

