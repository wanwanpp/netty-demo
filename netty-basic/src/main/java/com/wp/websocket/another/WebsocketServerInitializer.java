package com.wp.websocket.another;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * @author 王萍
 * @date 2018/1/22 0022
 */
public class WebsocketServerInitializer extends ChannelInitializer<Channel> {
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline().addLast(
                new HttpServerCodec(),
                new HttpObjectAggregator(65536),
                new WebSocketServerProtocolHandler("/websocket"),
                new TextFrameHandler(),
                new BinaryFrameHandler(),
                new ContinuationFrameHandler()
        );
    }

    public static final class TextFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
        protected void messageReceived(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
            ctx.channel().writeAndFlush(new TextWebSocketFrame(msg.text()));
        }
    }

    public static final class BinaryFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame> {
        protected void messageReceived(ChannelHandlerContext ctx, BinaryWebSocketFrame msg) throws Exception {
            ctx.channel().writeAndFlush(msg);
        }
    }

    public static final class ContinuationFrameHandler extends SimpleChannelInboundHandler<ContinuationWebSocketFrame> {
        protected void messageReceived(ChannelHandlerContext ctx, ContinuationWebSocketFrame msg) throws Exception {
            ctx.channel().writeAndFlush(msg);
        }
    }
}
