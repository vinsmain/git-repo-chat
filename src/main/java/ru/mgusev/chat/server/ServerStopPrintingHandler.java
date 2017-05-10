package ru.mgusev.chat.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.mgusev.chat.client.model.StopPrintingMessage;

public class ServerStopPrintingHandler extends SimpleChannelInboundHandler<StopPrintingMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, StopPrintingMessage msg) throws Exception {
        ChatServer.removePrintingUser(msg);
    }
}