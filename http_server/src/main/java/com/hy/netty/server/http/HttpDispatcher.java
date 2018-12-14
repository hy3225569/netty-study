package com.hy.netty.server.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;
import io.netty.util.internal.PlatformDependent;

/**
 * @ClassName HttpDispatcher
 * @Description Todo 请求处理程序
 * @Author holy
 * @Date 2018/12/11 15:14
 **/
public class HttpDispatcher {
    public void dispatch(ChannelHandlerContext context, RequestParameter parameter) {
        this.doResponse(context, "hello,word");
    }

    private void doResponse(ChannelHandlerContext context, String contentStr) {
        try {
            //已byte字节码方式传输
          /*  byte[] bytes = TripleDES.encrypt(contentStr);
            ByteBuf content = Unpooled.wrappedBuffer(bytes);*/
            //直接输出内容
            ByteBuf content = Unpooled.copiedBuffer(contentStr, CharsetUtil.UTF_8);
            FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set("Content-Length", content.readableBytes());
            response.headers().set("Content-Type", "text/plain; charset=utf-8");
            context.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        } catch (Exception e) {
            PlatformDependent.throwException(e);
        }
    }
}
