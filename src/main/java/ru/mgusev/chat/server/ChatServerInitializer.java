package ru.mgusev.chat.server;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class ChatServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast("decoder", new ObjectDecoder(ClassResolvers.cacheDisabled(getClass().getClassLoader())));
        pipeline.addLast("encoder", new ObjectEncoder());

        pipeline.addLast("handler", new ChatServerHandler());
        pipeline.addLast("startPrintingHandler", new ServerStartPrintingHandler());
        pipeline.addLast("stopPrintingHandler", new ServerStopPrintingHandler());
        pipeline.addLast("registerHandler", new ServerRegisterHandler());
        pipeline.addLast("authHandler", new ServerAuthHandler());
    }
}
