package io.netty.example.ademo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.Date;
import java.util.UUID;

/**
 * <p>自定义Netty服务端的通道事件。
 *
 * @author yangchuan1
 * @date 2022-06-01
 */
public class NettyServerChannelHandler implements ChannelInboundHandler {
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端 —— channelRegistered。时间：" + new Date());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端 —— channelUnregistered。时间：" + new Date());
    }

    /**
     * 通道就绪事件。
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端 —— 通道就绪事件。时间：" + new Date());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端 —— channelInactive。时间：" + new Date());
    }

    /**
     * 通道读取事件。
     *
     * @param ctx 通道读取上下文对象
     * @param msg 消息
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        String resStr = byteBuf.toString(CharsetUtil.UTF_8);
        System.out.println("服务端 —— 通道读取事件。时间：" + new Date()
            + "\t 读取到客户端发送的消息：" + resStr);
    }

    /**
     * 通道读取完毕事件。
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端 —— 通道读取完毕事件。时间：" + new Date());
        String resStr = "服务端回复消息：[" + UUID.randomUUID() + " ]；时间：" + new Date();
        ctx.writeAndFlush(Unpooled.copiedBuffer(resStr, CharsetUtil.UTF_8));
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("服务端 —— userEventTriggered。时间：" + new Date());
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端 —— channelWritabilityChanged。时间：" + new Date());
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端 —— handlerAdded。时间：" + new Date());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("服务端 —— handlerRemoved。时间：" + new Date());
    }

    /**
     * 通道异常发生事件。
     *
     * @param ctx
     * @param cause
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("服务端 —— 通道异常发生事件。时间：" + new Date());
        cause.printStackTrace();
        ctx.close();
    }

}
