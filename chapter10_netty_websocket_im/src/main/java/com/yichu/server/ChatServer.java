package com.yichu.server;

import com.yichu.server.handler.HttpHandler;
import com.yichu.server.handler.SFPHandler;
import com.yichu.server.handler.WebSocketHandler;
import com.yichu.server.protocol.SFPDecoder;
import com.yichu.server.protocol.SFPEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class ChatServer {

	public static void main(String[] args) throws Exception {
		int port=8080; //服务端默认端口
		new ChatServer().bind(port);
	}

	public void bind(int port) throws Exception{
		//1用于服务端接受客户端的连接
		EventLoopGroup acceptorGroup = new NioEventLoopGroup();
		//2用于进行SocketChannel的网络读写
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			//Netty用于启动NIO服务器的辅助启动类
			ServerBootstrap sb = new ServerBootstrap();
			//将两个NIO线程组传入辅助启动类中
			sb.group(acceptorGroup, workerGroup)
					//设置创建的Channel为NioServerSocketChannel类型
					.channel(NioServerSocketChannel.class)
					//配置NioServerSocketChannel的TCP参数
					.option(ChannelOption.SO_BACKLOG, 1024)
					//设置绑定IO事件的处理类
					.childHandler(new ChannelInitializer<SocketChannel>() {
						//创建NIOSocketChannel成功后，在进行初始化时，将它的ChannelHandler设置到ChannelPipeline中，用于处理网络IO事件
						@Override
						protected void initChannel(SocketChannel arg0) throws Exception {
							//这里就不能这样写了，需要和界面交互
							//arg0.pipeline().addLast(new TimeServerHandler());

							arg0.pipeline().addLast(new SFPDecoder());
							arg0.pipeline().addLast(new SFPEncoder());
							arg0.pipeline().addLast(new SFPHandler());

							//支持http协议， http请求处理的编解码器
							arg0.pipeline().addLast(new HttpServerCodec());
							//用于将http请求进行封装为FullHttpRequest对象
							arg0.pipeline().addLast(new HttpObjectAggregator(1024*64));
							//处理文件流
							arg0.pipeline().addLast(new ChunkedWriteHandler());
							//Http请求的具体处理对象
							arg0.pipeline().addLast(new HttpHandler());

							//支持WebSocket协议
							arg0.pipeline().addLast(new WebSocketServerProtocolHandler("/im"));
							arg0.pipeline().addLast(new WebSocketHandler());
						}
					});
			//绑定端口，同步等待成功（sync()：同步阻塞方法，等待bind操作完成才继续）
			//ChannelFuture主要用于异步操作的通知回调
			ChannelFuture cf = sb.bind(port).sync();
			System.out.println("服务端启动在8080端口。");
			//等待服务端监听端口关闭
			cf.channel().closeFuture().sync();
		} finally {
			//优雅退出，释放线程池资源
			acceptorGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
}

