package com.syuct.core_service.core.server.handler;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import java.net.InetAddress;
import java.net.InetSocketAddress;

public class UdpHandler extends SimpleChannelInboundHandler<DatagramPacket>{

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
        ByteBuf content = datagramPacket.content();
        System.out.println("as"+content);
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        InetAddress addr = InetAddress.getByName("127.0.0.1");
        InetSocketAddress inetSocketAddress = new InetSocketAddress(addr, 10000);
        DatagramPacket datagramPacket = new DatagramPacket(Unpooled.copiedBuffer("test".getBytes()), inetSocketAddress);
        ctx.channel().writeAndFlush(datagramPacket);
    }
}
