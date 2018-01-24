package com.wp.ende1;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class ServerHandler extends ChannelHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("server channel active... ");
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

//		ByteBuf buf = (ByteBuf) msg;
//		byte[] req = new byte[buf.readableBytes()];
//		buf.readBytes(req);
//		String body = new String(req, "utf-8");
//		System.out.println("Server :" + body);

        //若ServerBootStrap中未给channel配置StringDecoder，则这里不能直接转为string。
		String request = (String)msg;
		System.out.println("Server :" + request);
		String response = "服务器响应：" + msg + "$_";
		ctx.writeAndFlush(Unpooled.copiedBuffer(response.getBytes()));
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable t) throws Exception {
		ctx.close();
	}
}
