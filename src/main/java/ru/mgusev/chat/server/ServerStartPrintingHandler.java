package ru.mgusev.chat.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.mgusev.chat.client.model.StartPrintingMessage;

public class ServerStartPrintingHandler extends SimpleChannelInboundHandler<StartPrintingMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, StartPrintingMessage msg) throws Exception {
        ChatServer.addElementToVector(msg);
    }
}