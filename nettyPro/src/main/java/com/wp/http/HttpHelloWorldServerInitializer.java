
  package com.wp.http;
  
  import io.netty.channel.ChannelInitializer;
  import io.netty.channel.ChannelPipeline;
  import io.netty.channel.socket.SocketChannel;
  import io.netty.handler.codec.http.HttpServerCodec;
  import io.netty.handler.ssl.SslContext;
  
  public class HttpHelloWorldServerInitializer extends ChannelInitializer<SocketChannel> {
  
      private final SslContext sslCtx;
  
      public HttpHelloWorldServerInitializer(SslContext sslCtx) {
         this.sslCtx = sslCtx;
      }
  
      @Override
      public void initChannel(SocketChannel ch) {
          if (sslCtx != null) {
              ch.pipeline().addLast(sslCtx.newHandler(ch.alloc()));
          }
          ch.pipeline().addLast(new HttpServerCodec());
          ch.pipeline().addLast(new HttpHelloWorldServerHandler());
      }
  }