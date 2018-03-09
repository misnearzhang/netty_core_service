package com.syuct.core_service.core.server;

import com.syuct.core_service.core.server.handler.UdpHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

public class UdpServer {
    public void run(int port) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new UdpHandler());
        b.bind(port).sync().channel().closeFuture().await();
    }

    public static void main(String [] args) throws Exception{
        int port = 8080;
        new UdpServer().run(port);
    }
}
