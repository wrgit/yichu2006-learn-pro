package com.yichu.server.handler;

import com.yichu.server.processor.MessageProcessor;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

	private MessageProcessor processor = new MessageProcessor();


	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.fireChannelActive();
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
		//服务端与客户端的WebSocket交互
		processor.messageHandler(ctx.channel(), msg.text());
	}

	//客户端连接断开事件
	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		processor.logout(ctx.channel());
	}

}
