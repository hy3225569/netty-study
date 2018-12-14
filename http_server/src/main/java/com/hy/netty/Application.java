package com.hy.netty;

import com.hy.netty.server.http.HttpServer;
import com.hy.netty.util.StackTraceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Hello world!
 */
public class Application {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);
    public static void main(String[] args) {
        HttpServer httpServer = new HttpServer();
        try {
            httpServer.startServer(18092);
            Runtime.getRuntime().addShutdownHook(new ShutdownHookThread(httpServer));
        } catch (Throwable e) {
            LOG.error("Main error:{}", StackTraceUtils.stackTrace(e));
            try {
                httpServer.stopServer();
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    }
}
