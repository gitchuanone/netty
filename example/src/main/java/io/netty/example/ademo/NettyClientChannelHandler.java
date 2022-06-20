package io.netty.example.ademo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.Date;
import java.util.UUID;

/**
 * <p>自定义Netty客户端的通道事件。
 *
 * @author yangchuan1
 * @date 2022-06-01
 */
public class NettyClientChannelHandler implements ChannelInboundHandler {
    /**
     * 通道注册事件。
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端 —— 通道注册事件。时间：" + new Date());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端 —— channelUnregistered。时间：" + new Date());
    }

    /**
     * 通道就绪事件。
     *
     * @param ctx
     * @throws Exception
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Date date = new Date();
        System.out.println("客户端 —— 通道就绪事件。时间：" + date);
        String resStr = "客户端发送消息：" + UUID.randomUUID() + " ；时间：" + date;
        ChannelFuture channelFuture = ctx.writeAndFlush(Unpooled.copiedBuffer(resStr, CharsetUtil.UTF_8));
        if (channelFuture.isSuccess()) {
            System.out.println("客户端发送消息成功。");
        } else {
            System.out.println("客户端发送消息失败。");
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端 —— channelInactive。时间：" + new Date());
    }

    /**
     * 通道读取事件。
     *
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        String resStr = byteBuf.toString(CharsetUtil.UTF_8);
        System.out.println("客户端 —— 通道读取事件。时间：" + new Date()
                + "\t 读取到服务端回复的消息：" + resStr);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端 —— channelReadComplete。时间：" + new Date());
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        System.out.println("客户端 —— userEventTriggered。时间：" + new Date());
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端 —— channelWritabilityChanged。时间：" + new Date());
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端 —— handlerAdded。时间：" + new Date());
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println("客户端 —— handlerRemoved。时间：" + new Date());
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
        System.out.println("客户端 —— 通道就绪事件。时间：" + new Date());
        cause.printStackTrace();
        ctx.close();
    }

}
