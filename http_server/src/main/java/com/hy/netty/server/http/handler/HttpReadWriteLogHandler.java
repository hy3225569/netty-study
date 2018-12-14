package com.hy.netty.server.http.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName HttpReadWriteLogHandler   ChannelDuplexHandler继承自ChannelInboundHandlerAdapter  全双工模式
 * @Description Todo 读写记录日志
 * @Author holy
 * @Date 2018/12/11 15:09
 **/

@ChannelHandler.Sharable
public class HttpReadWriteLogHandler extends ChannelDuplexHandler {
    private static final Logger LOG = LoggerFactory.getLogger("http IO Log");

    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof ByteBuf) {
            LOG.info("Read {} bytes, from:[{}].", ((ByteBuf) msg).readableBytes(), ctx.channel().remoteAddress());
        }
        super.channelRead(ctx, msg);
    }

    /**
     * Calls ChannelOutboundInvoker.write(Object, ChannelPromise)
     * to forward to the next ChannelOutboundHandler in the ChannelPipeline.
     *
     * @param ctx
     * @param msg
     * @param promise
     * @throws Exception
     */
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        LOG.info("DuplexHandler---------------------");
        int bufSize;
        if (msg instanceof ByteBuf && (bufSize = ((ByteBuf) msg).readableBytes()) > 0) {
            promise.addListener(future -> LOG.info("To:[{}], the amount of data written:{} bytes.", ctx.channel().remoteAddress(), bufSize));
        }
        super.write(ctx, msg, promise);
    }
}
