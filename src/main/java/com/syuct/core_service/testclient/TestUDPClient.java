package com.syuct.core_service.testclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;

public class TestUDPClient {
	public void run(int port){
		EventLoopGroup group=new NioEventLoopGroup();
		try {
			Bootstrap boot=new Bootstrap();
			boot.group(group).channel(NioDatagramChannel.class);
			boot.option(ChannelOption.SO_BROADCAST, true);
			boot.handler(new TestUDPClientHandler());
			Channel channel=boot.bind(0).sync().channel();
			
			InetSocketAddress recipient=new InetSocketAddress("127.0.0.1", port);
			DatagramPacket pack=new DatagramPacket(Unpooled.copiedBuffer("1111111111111".getBytes()), recipient);
			channel.writeAndFlush(pack);
			channel.read();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			group.shutdownGracefully();
		}
	}
	
	public static void main(String[] args) {
		new TestUDPClient().run(1080);
	}
}
