package com.liaocyu.zookeeper.enums;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.Watcher;

public enum CuratorEventType
{
    /**
     * Corresponds to {@link CuratorFramework#create()}
     */
    CREATE,

    /**
     * Corresponds to {@link CuratorFramework#delete()}
     */
    DELETE,

    /**
     * Corresponds to {@link CuratorFramework#checkExists()}
     */
    EXISTS,

    /**
     * Corresponds to {@link CuratorFramework#getData()}
     */
    GET_DATA,

    /**
     * Corresponds to {@link CuratorFramework#setData()}
     */
    SET_DATA,

    /**
     * Corresponds to {@link CuratorFramework#getChildren()}
     */
    CHILDREN,

    /**
     * Corresponds to {@link CuratorFramework#sync(String, Object)}
     */
    SYNC,

    /**
     * Corresponds to {@link CuratorFramework#getACL()}
     */
    GET_ACL,

    /**
     * Corresponds to {@link CuratorFramework#setACL()}
     */
    SET_ACL,

    /**
     * Corresponds to {@link CuratorFramework#transaction()}
     */
    TRANSACTION,

    /**
     * Corresponds to {@link CuratorFramework#getConfig()}
     */
    GET_CONFIG,

    /**
     * Corresponds to {@link CuratorFramework#reconfig()}
     */
    RECONFIG,

    /**
     * Corresponds to {@link Watchable#usingWatcher(Watcher)} or {@link Watchable#watched()}
     */
    WATCHED,

    /**
     * Corresponds to {@link CuratorFramework#watches()} ()}
     */
    REMOVE_WATCHES,

    /**
     * Event sent when client is being closed
     */
    CLOSING
}

