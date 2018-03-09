package com.syuct.core_service.core.define;

import com.syuct.core_service.core.server.ThreadPool;
import com.syuct.core_service.protoc.TransMessage;
import io.netty.channel.Channel;

/**
 * Created by Misnearzhang on 2017/4/12.
 */
public abstract class AbstractParse implements Parse , Runnable{

    private TransMessage.Message message;
    private Channel channel;
    public ThreadPool threadPool;

    @Override
    public void run() {
        parse(message , channel);
    }
    public abstract void parse(TransMessage.Message object , Channel channel);

    public void initData(TransMessage.Message message, Channel channel, ThreadPool threadPool){
        this.message = message;
        this.channel = channel;
        this.threadPool = threadPool;
    }
}
