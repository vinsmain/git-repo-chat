package ru.mgusev.chat.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.mgusev.chat.client.model.AuthMessage;

public class ServerAuthHandler extends SimpleChannelInboundHandler<AuthMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AuthMessage authMessage) throws Exception {
        channelHandlerContext.channel().writeAndFlush(DataBaseHandler.authorisation(authMessage));
    }
}
