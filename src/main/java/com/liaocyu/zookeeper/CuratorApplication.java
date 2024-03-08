package com.liaocyu.zookeeper;

import com.liaocyu.zookeeper.curatorapi.addnode.AddZNode;
import com.liaocyu.zookeeper.curatorapi.delnode.DelZNode;
import com.liaocyu.zookeeper.curatorapi.demo.CuratorDemo;
import com.liaocyu.zookeeper.curatorapi.operaasyncznode.OperaAsyncZNode;
import com.liaocyu.zookeeper.curatorapi.putnode.PutZNode;
import com.liaocyu.zookeeper.lock.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : create by lcy
 * @Project : liaocyu_zookeeper
 * @createTime : 2024/3/8 17:04
 * @description :
 */
@SpringBootApplication
public class CuratorApplication implements ApplicationRunner {

    private static CuratorDemo curatorDemo;
    private static AddZNode addZNode;
    private static DelZNode delZNode;
    private static OperaAsyncZNode operaAsyncZNode;
    private static NotReentrantAcquireLock notReentrantAcquireLock;
    private static PutZNode putZNode;
    private static ReentrantAcquireLock reentrantAcquireLock;
    private static ReentrantAcquireLock2 reentrantAcquireLock2;
    private static ReentrantReadWriteLock reentrantReadWriteLock;
    private static SemaphoreV2Lock semaphoreV2Lock;
    private static MutiShareLock mutiShareLock;

    @Autowired
    public CuratorApplication(CuratorDemo curatorDemo , AddZNode addZNode , DelZNode delZNode ,
                              OperaAsyncZNode operaAsyncZNode , PutZNode putZNode ,
                              NotReentrantAcquireLock notReentrantAcquireLock , ReentrantAcquireLock reentrantAcquireLock ,
                              ReentrantAcquireLock2 reentrantAcquireLock2 , ReentrantReadWriteLock reentrantReadWriteLock ,
                              SemaphoreV2Lock semaphoreV2Lock , MutiShareLock mutiShareLock) {
        this.curatorDemo = curatorDemo;
        this.addZNode = addZNode;
        this.delZNode = delZNode;
        this.operaAsyncZNode = operaAsyncZNode;
        this.putZNode = putZNode;
        this.notReentrantAcquireLock = notReentrantAcquireLock;
        this.reentrantAcquireLock = reentrantAcquireLock;
        this.reentrantAcquireLock2 = reentrantAcquireLock2;
        this.reentrantReadWriteLock = reentrantReadWriteLock;
        this.semaphoreV2Lock = semaphoreV2Lock;
        this.mutiShareLock = mutiShareLock;
    }
    public static void main(String[] args) throws Exception {

        SpringApplication.run(CuratorApplication.class , args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        mutiShareLock.lock();
    }
}
