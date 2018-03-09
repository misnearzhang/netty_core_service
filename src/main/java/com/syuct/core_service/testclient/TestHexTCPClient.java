package com.syuct.core_service.testclient;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CountDownLatch;

public class TestHexTCPClient implements Runnable{
	private final Logger logger = LogManager.getLogger(TestHexTCPClient.class);
	public Channel channel;
	public static final CountDownLatch count = new CountDownLatch(1);
	public void run(){
		EventLoopGroup group=new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group);
			b.channel(NioSocketChannel.class);
			b.option(ChannelOption.TCP_NODELAY, true);
			b.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel ch) throws Exception {
					ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(
							1000, 999, 0));
					ch.pipeline().addLast(new TestTCPHandler());
				}
			});

			ChannelFuture f=b.connect("192.168.2.23", 3000).sync();
			channel = f.channel();
			if(channel!=null&&channel.isActive()){
				logger.debug("connecting successful");
				count.countDown();
			}
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			group.shutdownGracefully();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		TestHexTCPClient test = new TestHexTCPClient();
		Thread thraed = new Thread(test);
		thraed.start();
		count.await();
		if(test.channel.isActive()){
			System.out.println("jel");
			test.channel.write("helo".getBytes());
		}
	}
}
