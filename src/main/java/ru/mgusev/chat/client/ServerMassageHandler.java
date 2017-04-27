package ru.mgusev.chat.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.mgusev.chat.client.model.ServerMessage;

public class ServerMassageHandler extends SimpleChannelInboundHandler<ServerMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ServerMessage msg) throws Exception {
        ChatClientFrame.getController().printMessage("[" + msg.getServerDateTime() + "] " + msg.getServerMessage());
    }
}