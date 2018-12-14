/*
 * Decompiled with CFR 0_134.
 * 
 * Could not load the following classes:
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package com.hy.netty;

import com.hy.netty.server.Server;
import com.hy.netty.util.StackTraceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShutdownHookThread
extends Thread {
    private static final Logger LOG = LoggerFactory.getLogger(ShutdownHookThread.class);
    private final Server[] servers;

    public  ShutdownHookThread(Server ... servers) {
        this.servers = servers;
    }

    @Override
    public void run() {
        for (Server s : this.servers) {
            try {
                s.stopServer();
                LOG.warn("Server[{}] stop.", s);
            }
            catch (Exception e) {
                LOG.error("Stop server failed:{}.", StackTraceUtils.stackTrace(e));
            }
        }
    }
}

