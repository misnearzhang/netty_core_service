package com.syuct.core_service.core.define;

import com.syuct.core_service.protoc.TransMessage;
import io.netty.channel.Channel;

/**
 * define parse
 * Created by Misnearzhang on 2017/4/12.
 */
public interface Parse {

    /**
     * 解析消息
     */
    void parse(TransMessage.Message message, Channel channel);
}
