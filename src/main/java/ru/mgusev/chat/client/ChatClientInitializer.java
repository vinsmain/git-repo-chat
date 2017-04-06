package ru.mgusev.chat.client;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class ChatClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast("decoder", new ObjectDecoder(ClassResolvers.cacheDisabled(getClass().getClassLoader())));
        pipeline.addLast("encoder", new ObjectEncoder());

        pipeline.addLast("handlerClientMessage", new ClientMessageHandler());
        pipeline.addLast("handlerServerMessage", new ServerMassageHandler());
        pipeline.addLast("handlerPrintingMessage", new ClientPrintingHandler());
        pipeline.addLast("handlerAuthHandler", new ClientAuthHandler());
    }
}
