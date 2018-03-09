package com.syuct.core_service.core.define;

import com.syuct.core_service.core.container.Container;
import com.syuct.core_service.protoc.TransMessage;
import io.netty.channel.Channel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 解析报文 并做处理  用户消息则将消息处理了转发给网络发送线程 系统消息则根据消息类型处理
 * Created by zhanglong on 17-2-25.
 */

public abstract class ParseReal extends AbstractParse {
    private final Logger logger = LogManager.getLogger(ParseReal.class);


    public abstract void fromUser(TransMessage.Message message , Channel channel);
    public abstract void fromSystem(TransMessage.Message message , Channel channel);
    public abstract void handShake(TransMessage.Message message , Channel channel);
    @Override
    public void parse(TransMessage.Message message , Channel channel) {
        try {
            TransMessage.Head head = message.getHead();
            TransMessage.type type = head.getType();
            switch (type) {
                case USER:
                    fromUser(message,channel);
                    break;
                case SYSTEM:
                    fromSystem(message,channel);
                    break;
                case HANDSHAKE:
                    handShake(message,channel);
                    break;
                case RESPONSE:
                    //收到响应  判断响应类型  消息响应和心跳响应
                    //retransConcurrentHashMap.remove(header.getUid());
                    logger.info("receive response , remove retrans task");
                    threadPool.removeFuture(head.getUid());
                    break;
                case PONG:
                    //心跳响应  不做任何事
                    //Container.pingPongRest(ctx.channel().id());
                    logger.info("receive a PONG");
                    break;
                case PING:
                    //客户端心跳 响应一个PONG
                    TransMessage.Message.Builder response = TransMessage.Message.newBuilder();
                    TransMessage.Head.Builder head1= TransMessage.Head.newBuilder();
                    head1.setTime(System.currentTimeMillis());
                    head1.setUid(head.getUid());
                    head1.setStatus(TransMessage.status.OK);
                    head1.setType(TransMessage.type.PONG);
                    response.setHead(head1);
                    Container.sendHeartBeat(response.build(),channel.id());
                    logger.info("发送心跳完成");
                    break;
                default:
                    //不支持该协议
            }
        } catch (NullPointerException nullpoint) {
            logger.error("null message");
            //Container.send(CommUtil.createResponse(MessageEnum.status.ERROR.getCode(),null), channel.id());//空消息
            nullpoint.printStackTrace();
        }
    }
}
