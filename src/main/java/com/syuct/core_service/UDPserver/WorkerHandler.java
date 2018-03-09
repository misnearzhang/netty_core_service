package com.syuct.core_service.UDPserver;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

/**
 * 
 * @author Administrator
 *
 */
public class WorkerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) {
		// TODO Auto-generated method stub
		ByteBuf bug = msg.content();
		byte[] b = new byte[bug.readableBytes()];
		bug.readBytes(b);
		System.out.println("form:" + new String(b));
		bug.clear();
		bug= Unpooled.copiedBuffer("to".getBytes());

		DatagramPacket pack = new DatagramPacket(bug, msg.sender());
		try {
			ctx.writeAndFlush(pack);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public boolean acceptInboundMessage(Object msg) throws Exception {
		return super.acceptInboundMessage(msg);

	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelActive(ctx);
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelReadComplete(ctx);
	}

	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelWritabilityChanged(ctx);
		ctx.write("yes");
	}

}
