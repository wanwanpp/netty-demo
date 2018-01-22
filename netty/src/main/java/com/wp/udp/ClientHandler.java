package com.wp.udp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;


public class ClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    public void messageReceived(ChannelHandlerContext ctx, DatagramPacket msg)
	    throws Exception {
		String response = msg.content().toString(CharsetUtil.UTF_8);
		    System.out.println(response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
	    throws Exception {
		cause.printStackTrace();
		ctx.close();
    }
}
