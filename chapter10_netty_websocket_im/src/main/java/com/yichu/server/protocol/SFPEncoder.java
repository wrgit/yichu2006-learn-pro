package com.yichu.server.protocol;

import org.msgpack.MessagePack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class SFPEncoder extends MessageToByteEncoder<MessageObject> {

	@Override
	protected void encode(ChannelHandlerContext ctx, MessageObject msg, ByteBuf out) throws Exception {
		//序列化
		out.writeBytes(new MessagePack().write(msg));
	}


}
