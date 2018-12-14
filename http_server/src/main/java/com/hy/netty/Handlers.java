package com.hy.netty;

import com.hy.netty.util.ReflectionUtils;
import com.hy.netty.util.StackTraceUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringWriter;
import java.net.SocketTimeoutException;

/**
 * @ClassName Handlers
 * @Description Todo
 * @Author holy
 * @Date 2018/12/11 15:54
 **/
public class Handlers {
    private static final Logger LOG = LoggerFactory.getLogger(Handlers.class);

    public static ChannelFuture handleException(ChannelHandlerContext ctx, Throwable t) {
        ChannelFuture future;
        block8 : {
            Channel channel = ctx.channel();
            future = null;
            try {
               if (t instanceof SocketTimeoutException) {
                    LOG.error("Socket timeout:{}.", t.getMessage());
                    future = channel.close();
                } else {
                    future = ctx.close();
                }
            }
            catch (Throwable e) {
                LOG.error("Error caught when handle exception:{}.", StackTraceUtils.stackTrace(e));
                if (channel == null) break block8;
                future = channel.close();
            }
        }
        return future;
    }
    public static void handleWritabilityChanged(ChannelHandlerContext ctx, Object who) {
        String name = ReflectionUtils.simpleClassName(who);
        if (!ctx.channel().isWritable()) {
            if (ctx.channel().unsafe().outboundBuffer().size() > 32) {
                LOG.error("{}, channel:{}, is not writable. Going to close channel.", name, ctx.channel());
                ctx.close();
            } else {
                LOG.warn("{}, channel:{}, is not writable.", name, ctx.channel());
            }
        } else {
            LOG.info("{}, channel:{}, is writable.", name, ctx.channel());
        }
    }
}
