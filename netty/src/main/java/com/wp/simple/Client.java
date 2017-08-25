package com.wp.simple;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class Client {

    public static void main(String[] args) throws Exception {

        EventLoopGroup workgroup = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(workgroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        sc.pipeline().addLast(new ClientHandler());
                    }
                });

        ChannelFuture cf1 = b.connect("127.0.0.1", 8765).sync();

        cf1.channel().write(Unpooled.copiedBuffer("777".getBytes()));
//        Thread.sleep(1000);
        cf1.channel().write(Unpooled.copiedBuffer("666".getBytes()));
//        Thread.sleep(1000);
        cf1.channel().write(Unpooled.copiedBuffer("555".getBytes()));
        cf1.channel().flush();  //粘包，，，这里flush后后面的数据可能和本次flush的内容一起发送过去。   例如：本例中可能会收到："Server: 777666555777666555"
//        Thread.sleep(1000);

        //buf
        cf1.channel().writeAndFlush(Unpooled.copiedBuffer("777".getBytes()));
//        Thread.sleep(1000);
        cf1.channel().writeAndFlush(Unpooled.copiedBuffer("666".getBytes()));
//        Thread.sleep(1000);
        cf1.channel().writeAndFlush(Unpooled.copiedBuffer("555".getBytes()));

        cf1.channel().closeFuture().sync();
        workgroup.shutdownGracefully();
    }
}
