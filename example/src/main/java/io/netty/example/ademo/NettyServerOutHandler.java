package io.netty.example.ademo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;

/**
 * <p>
 *
 * @author yangchuan1
 * @date 2022-06-25
 */
public class NettyServerOutHandler extends ChannelOutboundHandlerAdapter {
    @Override
    public void read(ChannelHandlerContext ctx) throws Exception {
        System.out.println("消息出站-- read ");
        // 此处debug，跟踪出站流程。
        super.read(ctx);
    }

    @Override
    public void flush(ChannelHandlerContext ctx) throws Exception {
        System.out.println("消息出站-- flush ");
        // 此处debug，跟踪出站流程。
        super.flush(ctx);
    }
}
