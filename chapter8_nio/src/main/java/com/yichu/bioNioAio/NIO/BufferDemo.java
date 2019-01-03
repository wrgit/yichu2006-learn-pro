package com.yichu.bioNioAio.NIO;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BufferDemo {
	public static void main(String[] args) {
		try {
			RandomAccessFile file = new RandomAccessFile("E:/dongnao/VIP_v2.0/netty/nio.txt","r");
			FileChannel channel = file.getChannel();

			//定义一个Buffer，并且初始化大小
			ByteBuffer buf = ByteBuffer.allocate(10);
			System.out.println("position:"+buf.position()+"; limit:"+buf.limit()+"; capacity:"+buf.capacity());
			//将Channel中的数据读到buffer中
			channel.read(buf);
			System.out.println("position:"+buf.position()+"; limit:"+buf.limit()+"; capacity:"+buf.capacity());

			//将buffer写模式切换为读模式
			buf.flip();
			System.out.println("position:"+buf.position()+"; limit:"+buf.limit()+"; capacity:"+buf.capacity());

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}