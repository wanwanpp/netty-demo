package com.wp.helloworld;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("server channel active... ");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
//        if (msg instanceof HttpRequest) {
//            HttpRequest request = (HttpRequest) msg;
//            System.out.println(request.method());
//
//            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer("<h1>hello</h1>".getBytes()));
//            response.headers().set(CONTENT_TYPE, "text/html");
//            response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());
//            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
//            ctx.writeAndFlush("haha".getBytes())
//                    .addListener(ChannelFutureListener.CLOSE);
//        }
        ByteBuf buf = (ByteBuf) msg;
        byte[] req = new byte[buf.readableBytes()];
        buf.readBytes(req);
        String body = new String(req, "utf-8");
        System.out.println("Server :" + body);
        String response = "返回给客户端的响应：" + body;
        ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
//        .addListener(ChannelFutureListener.CLOSE);
//        加上这个listener后,服务器接收到一次该客户端的请求后就关闭与该客户端的连接，即只进行短连接。
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx)
            throws Exception {
        System.out.println("读完了");
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable t)
            throws Exception {
        //这里可以在关闭连接之前，给客户端返回一个出现异常的响应码
        ctx.close();
    }
}
