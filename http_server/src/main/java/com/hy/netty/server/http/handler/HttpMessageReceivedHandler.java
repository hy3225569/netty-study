package com.hy.netty.server.http.handler;

import com.hy.netty.Constants;
import com.hy.netty.Handlers;
import com.hy.netty.server.http.HttpDispatcher;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @ClassName HttpMessageReceivedHandler
 * @Description Todo message的读写操作
 * @Author holy
 * @Date 2018/12/11 15:08
 **/
@ChannelHandler.Sharable
public class HttpMessageReceivedHandler extends ChannelInboundHandlerAdapter {
    private static final Logger LOG = LoggerFactory.getLogger(HttpMessageReceivedHandler.class);
    //连接数
    private static final AtomicInteger CONN_COUNTER = new AtomicInteger(0);
    private HttpDispatcher dispatcher = new HttpDispatcher();

    /**
     * Invoked when the current Channel has read a message from the peer.
     * 当前Channel从peer中读到数据时调用此方法(peer是什么鬼)
     * @param context
     * @param message
     */
    public void channelRead(ChannelHandlerContext context, Object message) {
        //这里的FullHttpRequest,大概是 Combine the HttpRequest and FullHttpMessage,
        // so the request is a complete HTTP request.
        if (message instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) message;
            try {
                ByteBuf buf = request.content();
                String paramString = buf.toString(Constants.UTF8);
                LOG.info("message request : {}", paramString);
               /* RequestParameter parameter = JSON.parseObject(paramString, RequestParameter.class);
                parameter.setRemoteAddress(context.channel().remoteAddress().toString());*/
                this.dispatcher.dispatch(context, null);
            } finally {
                request.release();
            }
        } else {
            LOG.warn("Unexpected msg type received:{}.", message.getClass());
            ReferenceCountUtil.release(message);
        }
    }

    /**
     * Gets called once the writable state of a Channel changed.
     * 在一个Channel的可写状态改变时的通知方法
     * @param ctx
     */
    public void channelWritabilityChanged(ChannelHandlerContext ctx) {
        Handlers.handleWritabilityChanged(ctx, this);
    }

    /**
     * 异常通知
     * @param ctx
     * @param cause
     */
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Handlers.handleException(ctx, cause);
    }

    /**
     * The Channel of the ChannelHandlerContext is now active
     * 如果Channel处于活跃状态调用此方法  Channel有四种状态 活跃，非活跃，已注册，非注册
     * @param ctx
     * @throws Exception
     */
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int count = CONN_COUNTER.incrementAndGet();
        LOG.info("Connected http channel with:[{}] as the {}th connect channel.", ctx.channel().remoteAddress(), count);
        super.channelActive(ctx);
    }

    /**
     * The Channel of the ChannelHandlerContext was registered is now inactive and reached its end of lifetime.
     * 已注册的Channel现在处于非活动状态，并且达到其生命周期的末尾时,调用此方法
     * @param ctx
     * @throws Exception
     */
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        int count = CONN_COUNTER.getAndDecrement();
        LOG.info("Disconnected http channel with:[{}] as the {}th connect channel.", ctx.channel().remoteAddress(), count);
        super.channelInactive(ctx);
    }
}
