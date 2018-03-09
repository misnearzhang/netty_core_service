package com.syuct.core_service.testclient;

import com.syuct.core_service.protoc.TransMessage;
import com.syuct.core_service.support.pool.PoolUtils;

import java.util.UUID;

/**
 * Created by misnearzhang on 2017/5/9.
 */
public class Pool {
    public static void main(String[] args) {
        while(true){
            TransMessage.Message.Builder builder = PoolUtils.getMessageInstance();
            TransMessage.Head.Builder head = TransMessage.Head.newBuilder();
            head.setStatus(TransMessage.status.DECODEERR);
            head.setTime(System.currentTimeMillis());
            head.setType(TransMessage.type.HANDSHAKE);
            head.setUid(UUID.randomUUID().toString());
            builder.setHead(head);
            builder.setBody("im zhanglong");
            System.out.println(builder.build().toString());
            builder.clear();
            PoolUtils.releaseMessage(builder);

        }
    }
}
