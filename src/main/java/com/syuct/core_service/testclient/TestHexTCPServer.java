package com.syuct.core_service.testclient;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.timeout.IdleStateHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * bootstrap 类 开启线程池 加载主服务
 *
 * @author Misnearzhang
 */
/*
                   _ooOoo_
                  o8888888o
                  88" . "88
                  (| -_- |)
                  O\  =  /O
               ____/`---'\____
             .'  \\|     |//  `.
            /  \\|||  :  |||//  \
           /  _||||| -:- |||||-  \
           |   | \\\  -  /// |   |
           | \_|  ''\---/''  |   |
           \  .-\__  `-`  ___/-. /
         ___`. .'  /--.--\  `. . __
      ."" '<  `.___\_<|>_/___.'  >'"".
     | | :  `- \`.;`\ _ /`;.`/ - ` : | |
     \  \ `-.   \_ __\ /__ _/   .-` /  /
======`-.____`-.___\_____/___.-`____.-'======
                   `=---='
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
         		佛祖保佑       永无BUG
         			O0o li1
*/
public class TestHexTCPServer {

    private final Logger logger = LogManager.getLogger(TestHexTCPServer.class);

    public void bind() throws Exception {
        EventLoopGroup master = new NioEventLoopGroup();
        EventLoopGroup slaver = new NioEventLoopGroup(4);
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(master, slaver);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.option(ChannelOption.SO_BACKLOG, 1024);
            bootstrap.option(ChannelOption.WRITE_BUFFER_WATER_MARK,new WriteBufferWaterMark(100*1024*1024,1000*1024*1024));
            bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(
                            3000, 2000, 0));
                    ch.pipeline().addLast(new ByteArrayDecoder());
                    ch.pipeline().addLast(new ByteArrayEncoder());
                    ch.pipeline().addLast(new WorkerInBoundHandler());
                }
            });
            logger.info(3000);
            logger.info("server has startup successful!");
            ChannelFuture f = bootstrap.bind(3000).sync();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("server has stop !");
        } finally {
            master.shutdownGracefully();
            slaver.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception {
        new TestHexTCPServer().bind();
    }
}
