package com.liaocyu.zookeeper.callbak;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.CuratorEvent;

public interface BackgroundCallback
{
    /**
     * Called when the async background operation completes
     *
     * @param client 当前客户端实例
     * @param event operation result details 服务端事件操作结果，包含事件类型和响应码
     * @throws Exception errors
     */
    public void processResult(CuratorFramework client, CuratorEvent event) throws Exception;
}
