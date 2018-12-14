package com.hy.netty.server.http;

import com.hy.netty.server.http.handler.HttpMessageReceivedHandler;
import com.hy.netty.server.http.handler.HttpReadWriteLogHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;

/**
 * @ClassName HttpChannelInitializer
 * @Description Todo channel初始化，加入需要pipeline来处理的逻辑
 * @Author holy
 * @Date 2018/12/11 15:07
 **/
public class HttpChannelInitializer extends ChannelInitializer<SocketChannel> {

    private HttpMessageReceivedHandler httpMessageReceivedHandler = new HttpMessageReceivedHandler();
    private HttpReadWriteLogHandler httpReadWriteLogHandler = new HttpReadWriteLogHandler();

    /**
     * This method will be called once the Channel was registered.
     * @param socketChannel
     */
    @Override
    protected void initChannel(SocketChannel socketChannel) {
        //这里的pipeline其实用了责任链设计模式，顺序执行所有操作
        socketChannel.pipeline().addLast(this.httpReadWriteLogHandler, new HttpRequestDecoder(), new HttpContentDecompressor(), new HttpObjectAggregator(1048576), new HttpResponseEncoder(), new HttpContentCompressor(), this.httpMessageReceivedHandler);
    }
}
