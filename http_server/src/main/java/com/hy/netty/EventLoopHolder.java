package com.hy.netty;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.util.concurrent.atomic.AtomicInteger;

public class EventLoopHolder {

    //定义这个静态变量不明所以，初步判断是个计数器，在高并发环境下保证原子性
    private static AtomicInteger refcount = new AtomicInteger(0);

    /**
     * 创建EventLoopGroup
     * @return
     */
    public static EventLoopGroup group() {
        refcount.incrementAndGet();
        return SingletonHolder.GROUP;
    }

    /**
     * 优雅关闭netty服务
     * @return
     */
    public static int shutdownGracefully() {
        int c = refcount.decrementAndGet();
        if (c == 0) {
            SingletonHolder.GROUP.shutdownGracefully();
        }
        return c;
    }

    //如果一个类要被声明为static的，只有一种情况，就是静态内部类
    //静态内部类使用场景一般是当外部类需要使用内部类，而内部类无需外部类资源，
    // 并且内部类可以单独创建的时候会考虑采用静态内部类的设计
    //此处作为外部类EventLoopHolder调用的,为EventLoopHolder独有的类
    private static class SingletonHolder {
        //netty 的 EventloopGroup 其实就是线程池， 通过它来配置 接收连接 和 处理连接读写 的线程池大小。
        static final EventLoopGroup GROUP = new NioEventLoopGroup(Constants.AVAILABLE_PROCESSORS * 8,
                new DefaultThreadFactory("Common worker"));
    }
}
