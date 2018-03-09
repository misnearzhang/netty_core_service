package com.syuct.core_service.testclient;

import com.syuct.core_service.core.container.Container;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WorkerInBoundHandler extends ChannelInboundHandlerAdapter {
    private final Logger logger = LogManager.getLogger(WorkerInBoundHandler.class);

    //private static final Publisher publisher=Publisher.newInstance();
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info(Container.getCount());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("---------------------------------------:"+msg);
        ByteBuf buf = Unpooled.copiedBuffer("".getBytes());
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        logger.info("channel read complete");
        ctx.flush();
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        logger.info("channelWritabilityChanged");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }


    /**
     * heartbeat : when WRITE_IDLE is detect then send a PING message,
     * while 3 times not receive PONG message then close this channel
     * if the client is not had a handshake so in the first WRITE_IDLE
     * then close it.
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {

    }
}
