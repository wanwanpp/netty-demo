package com.wp.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;

import static io.netty.handler.codec.http.HttpHeaderNames.*;
import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;


public class HttpHelloWorldServerHandler extends ChannelHandlerAdapter {
    private static byte[] CONTENT = {'<', 'h', '1', '>', 'H', 'e', 'l', 'l', 'o', ' ', 'W', 'o', 'r', 'l', 'd', '<', '/', 'h', '1', '>'};

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        //将msg转为HttpRequest对象方便操作，如进行请求方法判断，获取url等。
        if (msg instanceof HttpRequest) {
            HttpRequest req = (HttpRequest) msg;
            String uri = req.uri();
            if (uri.equals("/haha")) {
                CONTENT = "haha".getBytes();
            }

            if (HttpHeaderUtil.is100ContinueExpected(req)) {
                ctx.write(new DefaultFullHttpResponse(HTTP_1_1, CONTINUE));
            }
            boolean keepAlive = HttpHeaderUtil.isKeepAlive(req);
            FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(CONTENT));
//            response.headers().set(CONTENT_TYPE, "text/plain");
            response.headers().set(CONTENT_TYPE, "text/html");//返回html。
            response.headers().setInt(CONTENT_LENGTH, response.content().readableBytes());//必须要在响应头中设置这个信息

//            若不是keepalive，则发送响应后就关闭连接
            if (!keepAlive) {
                ctx.write(response).addListener(ChannelFutureListener.CLOSE);
            } else {
                response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
                ctx.write(response);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}