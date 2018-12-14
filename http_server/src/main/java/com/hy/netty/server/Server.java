package com.hy.netty.server;

import java.net.InetSocketAddress;

public interface Server {

    /**
     * 获取通信协议
     *
     * @return
     */
    TRANSMISSION_PROTOCOL getTransmissionProtocol();

    void startServer();

    void startServer(int var1);

    void startServer(InetSocketAddress var1) throws Exception;

    void stopServer();

    enum TRANSMISSION_PROTOCOL {
        TCP, UDP, HTTP;

        TRANSMISSION_PROTOCOL() {

        }
    }
}
