package com.syuct.core_service.core.server;

import com.syuct.core_service.core.server.handler.UdpHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

public class UdpServer {
    public void run(int port) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group)
                //.channel(NioServerSocketChannel.class)
                .channel(NioDatagramChannel.class)
                .option(ChannelOption.SO_BROADCAST, true)
                .handler(new ChannelInitializer<NioDatagramChannel>() {
                    @Override
                    protected void initChannel(NioDatagramChannel nioDatagramChannel) throws Exception {
                        ChannelPipeline p = nioDatagramChannel.pipeline();
                        p.addLast("idleStateHandler", new IdleStateHandler(
                                3, 3, 0));
                        p.addLast(new UdpHandler());
                    }
                });
        b.bind(port).sync().channel().closeFuture().await();
    }

    public static void main(String [] args) throws Exception{
        int port = 8080;
        new UdpServer().run(port);
    }
}
