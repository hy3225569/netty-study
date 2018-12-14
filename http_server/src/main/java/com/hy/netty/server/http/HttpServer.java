package com.hy.netty.server.http;

import com.hy.netty.Constants;
import com.hy.netty.EventLoopHolder;
import com.hy.netty.server.Server;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class HttpServer implements Server {
    private static final Logger logger = LoggerFactory.getLogger(HttpServer.class);

    //一个服务端构造器，初始化服务端的配置
    private ServerBootstrap bootstrap;

    /**
     * 获取通信协议，此处用http协议
     *
     * @return
     */
    @Override
    public TRANSMISSION_PROTOCOL getTransmissionProtocol() {
        return TRANSMISSION_PROTOCOL.HTTP;
    }

    @Override
    public void startServer() {
        throw new UnsupportedOperationException("address");
    }

    /**
     * 启动netty服务
     * @param port
     */
    @Override
    public void startServer(int port) {
        try {
            this.bootstrap = new ServerBootstrap();
            //channel是netty中的核心组件,由它负责同对端进行网络通信、注册和数据操作等功能。
            //具体可参考https://blog.csdn.net/ZuoAnYinXiang/article/details/79977271
            this.bootstrap.group(EventLoopHolder.group()).
                    channel(NioServerSocketChannel.class).
                    childHandler(new HttpChannelInitializer()).
                    option(ChannelOption.SO_BACKLOG, 1024).
                    childOption(ChannelOption.SO_REUSEADDR, true);

            this.bootstrap.bind(port).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("Http server start:{}.", (Constants.NEWLINE + this.toString()));
    }

    @Override
    public void startServer(InetSocketAddress var) {
        this.startServer(var.getPort());
    }


    @Override
    public void stopServer() {
        EventLoopHolder.shutdownGracefully();
    }

    public String toString() {
        return this.bootstrap == null ? "" : this.bootstrap.toString();
    }
}
