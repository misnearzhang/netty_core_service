package com.syuct.core_service.testclient.client;

import com.syuct.core_service.protoc.TransMessage;
import com.syuct.core_service.support.pool.PoolUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.util.UUID;

/**
 * Created by Misnearzhang on 2017/4/25.
 */
public class TestTCPHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof TransMessage.Message) {
            TransMessage.Message message = (TransMessage.Message) msg;
            TransMessage.type type = message.getHead().getType();
            switch (type) {
                case PING:
                    //客户端心跳 响应一个PONG
                    TransMessage.Message.Builder response = TransMessage.Message.newBuilder();
                    TransMessage.Head.Builder head1 = TransMessage.Head.newBuilder();
                    head1.setTime(System.currentTimeMillis());
                    head1.setUid(message.getHead().getUid());
                    head1.setStatus(TransMessage.status.OK);
                    head1.setType(TransMessage.type.PONG);
                    response.setHead(head1);
                    ctx.writeAndFlush(response);
                    break;
                case PONG:
                    System.out.println(message.toString());
                    break;
                case RESPONSE:
                    System.out.println("收到响应!");
                    System.out.println(message.toString());
                    break;
                case USER:
                    TransMessage.Message.Builder builder = PoolUtils.getMessageInstance();
                    TransMessage.Head.Builder head = TransMessage.Head.newBuilder();
                    head.setStatus(TransMessage.status.OK);
                    head.setTime(System.currentTimeMillis());
                    head.setType(TransMessage.type.RESPONSE);
                    head.setUid(message.getHead().getUid());
                    builder.setHead(head);
                    ctx.writeAndFlush(builder.build());
                    System.out.println(builder.toString());
                    break;
                default:
                    System.out.println(message.toString());
            }
        }
        super.channelRead(ctx, msg);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent idle = (IdleStateEvent) evt;
            if (idle.state().equals(IdleState.WRITER_IDLE)) {
                TransMessage.Message.Builder message_builder = TransMessage.Message.newBuilder();
                TransMessage.Head.Builder head_builder = TransMessage.Head.newBuilder();
                head_builder.setType(TransMessage.type.PING);
                head_builder.setStatus(TransMessage.status.REQ);
                head_builder.setUid(UUID.randomUUID().toString());
                head_builder.setTime(System.currentTimeMillis());
                message_builder.setHead(head_builder);
                ctx.channel().writeAndFlush(message_builder.build());
            } else if (idle.state().equals(IdleState.READER_IDLE)) {
                //读超时  关闭
                ctx.channel().close();
            }
        }
        super.userEventTriggered(ctx, evt);
    }
}
