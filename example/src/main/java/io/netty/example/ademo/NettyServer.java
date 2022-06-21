package io.netty.example.ademo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * <p>自定义Netty服务端。
 *
 * @author yangchuan1
 * @date 2022-06-01
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        // 1. 创建bossGroup线程组: 处理网络事件--连接事件；线程数默认为: 2 * 处理器线程数。断点进入调试。
        EventLoopGroup boosGroup = new NioEventLoopGroup(1);
        // 2. 创建workerGroup线程组: 处理网络事件--读写事件，线程数默认为: 2 * 处理器线程数
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        // 3. 创建服务端启动助手
        ServerBootstrap serverBootstrap = new ServerBootstrap()
                // 4. 设置bossGroup线程组和workerGroup线程组
                .group(boosGroup, workerGroup)
                // 5. 设置服务端通道实现为NIO
                .channel(NioServerSocketChannel.class)
                // 6. 参数设置。服务端-设置线程队列中等待连接个数
                .option(ChannelOption.SO_BACKLOG, 10)
                // 6. 参数设置。客户端-设置活跃状态，child是设置workerGroup
                .childOption(ChannelOption.SO_KEEPALIVE, Boolean.TRUE)
                // 7. 创建一个通道初始化对象
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        // 8. 向pipeline中添加自定义业务处理handler
                        ch.pipeline().addLast(new NettyServerChannelHandler());
                    }
                });

        // 9. 启动服务端并绑定端口,同时将异步改为同步。断点进入启动流程。
//        ChannelFuture channelFuture = serverBootstrap.bind(9999).sync();
        // 由同步改为异步，获取回调的结果。
        ChannelFuture channelFuture = serverBootstrap.bind(9999);
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("服务端端口绑定成功。");
                } else {
                    System.out.println("服务端端口绑定失败。");
                }
            }
        });
        System.out.println("服务器启动了。。。");
        // 10. 关闭通道（并不是真正意义上的关闭,而是监听通道关闭状态）和关闭连接池
        channelFuture.channel().closeFuture().sync();
        boosGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

}
