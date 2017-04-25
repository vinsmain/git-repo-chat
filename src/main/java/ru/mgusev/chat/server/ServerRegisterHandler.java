package ru.mgusev.chat.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.mgusev.chat.client.model.RegisterMessage;

public class ServerRegisterHandler extends SimpleChannelInboundHandler<RegisterMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RegisterMessage registerMessage) throws Exception {
        channelHandlerContext.channel().writeAndFlush(DataBaseHandler.registration(registerMessage));
    }
}
