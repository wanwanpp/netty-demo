package com.wp.separate;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class Client {

    public static void main(String[] args) throws Exception {

        EventLoopGroup group = new NioEventLoopGroup();

        Bootstrap b = new Bootstrap();
        b.group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        ByteBuf buf = Unpooled.copiedBuffer("$_".getBytes());
                        //以“$_”分隔消息
                        sc.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, buf));
                        sc.pipeline().addLast(new StringDecoder());
                        sc.pipeline().addLast(new ClientHandler());
                    }
                });

        ChannelFuture cf = b.connect("127.0.0.1", 8765).sync();

//        cf.channel().writeAndFlush(Unpooled.wrappedBuffer("bbbb$_".getBytes()));
//        cf.channel().writeAndFlush(Unpooled.wrappedBuffer("cccc$_".getBytes()));
        cf.channel().writeAndFlush(Unpooled.copiedBuffer("bbbb$_".getBytes()));
        cf.channel().writeAndFlush(Unpooled.copiedBuffer("ccccc$_".getBytes()));

        //等待客户端端口关闭
        cf.channel().closeFuture().sync();
        group.shutdownGracefully();
    }
}
