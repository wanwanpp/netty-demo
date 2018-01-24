package com.wp.helloworld;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class Server {
    public static void main(String[] args) throws Exception {
        //1 创建线两个程组
        //第一个经常被叫做‘boss’，用来接收进来的连接。
        // 第二个经常被叫做‘worker’，用来处理已经被接收的连接，
        // 一旦‘boss’接收到连接，就会把连接信息注册到‘worker’上。
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        //传统阻塞io
//        EventLoopGroup bossGroup = new OioEventLoopGroup();
//        EventLoopGroup workerGroup = new OioEventLoopGroup();
//        EpollEventLoopGroup仅支持在linux上运行，所以windows上这样写会报错
//        EventLoopGroup bossGroup = new EpollEventLoopGroup();
//        EventLoopGroup workerGroup = new EpollEventLoopGroup();

        //2 创建辅助工具类，用于服务器通道的一系列配置
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup)        //绑定俩个线程组
                .channel(NioServerSocketChannel.class)        //指定NIO的模式
                .option(ChannelOption.SO_BACKLOG, 1024)        //设置tcp缓冲区
                .option(ChannelOption.SO_SNDBUF, 32 * 1024)    //设置发送缓冲大小
                .option(ChannelOption.SO_RCVBUF, 32 * 1024)    //这是接收缓冲大小
                .option(ChannelOption.SO_KEEPALIVE, true)    //保持连接
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel sc) throws Exception {
                        //3 在这里配置具体数据接收方法的处理
                        sc.pipeline().addLast(new HttpServerCodec());   //添加这个后，可以将获取的msg直接转为HttpRequest类型，编码器。
                        sc.pipeline().addLast(new ServerHandler());
                    }
                });

        //4 进行绑定
        ChannelFuture cf1 = b.bind(8765).sync();
        //ChannelFuture cf2 = b.bind(8764).sync();
        //5 等待关闭
        cf1.channel().closeFuture().sync();
        //cf2.channel().closeFuture().sync();
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }
}
