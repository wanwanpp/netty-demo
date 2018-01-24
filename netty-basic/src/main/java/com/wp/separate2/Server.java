package com.wp.separate2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class Server {

    public static void main(String[] args) throws Exception {

        EventLoopGroup pGroup = new NioEventLoopGroup();
        EventLoopGroup cGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();
        b.group(pGroup, cGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .option(ChannelOption.SO_SNDBUF, 32 * 1024)
                .option(ChannelOption.SO_RCVBUF, 32 * 1024)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        //设置定长字符串接收
                        sc.pipeline().addLast(new FixedLengthFrameDecoder(5));
                        //设置字符串形式的解码
                        sc.pipeline().addLast(new StringDecoder());
                        sc.pipeline().addLast(new ServerHandler());
                    }
                });

        ChannelFuture cf = b.bind(8765).sync();
        cf.channel().closeFuture().sync();
        pGroup.shutdownGracefully();
        cGroup.shutdownGracefully();
    }
}
