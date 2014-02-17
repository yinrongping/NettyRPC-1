package com.lubin.rpc.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import com.lubin.rpc.protocol.SrvrRequestDecoder;
import com.lubin.rpc.protocol.SrvrResponseEncoder;

public class DefaultServerInitializer extends ChannelInitializer<SocketChannel> {

	private final ServerConfig conf;

	private final EventExecutorGroup executor;

	public DefaultServerInitializer(ServerConfig conf) {
		this.conf = conf;
		this.executor = new DefaultEventExecutorGroup(
				conf.getTaskThreadPoolSize());
	}

	@Override
	public void initChannel(SocketChannel ch) throws Exception {
		// Create a default pipeline implementation
		final ChannelPipeline p = ch.pipeline();

		p.addLast("decoder", new SrvrRequestDecoder());

		p.addLast("encoder", new SrvrResponseEncoder());

		p.addLast("handler", new DefaultHandler(executor));
		
		p.addLast("httpExceptionHandler", new DefaultExceptionHandler());
	}
}