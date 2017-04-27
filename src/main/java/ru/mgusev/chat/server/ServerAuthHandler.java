package ru.mgusev.chat.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.mgusev.chat.client.model.AuthMessage;
import ru.mgusev.chat.client.model.AuthResult;

public class ServerAuthHandler extends SimpleChannelInboundHandler<AuthMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AuthMessage authMessage) throws Exception {
        AuthResult authResult = DataBaseHandler.authorisation(authMessage);
        System.out.println(authResult.getNickName());
        if (authResult.getNickName() != null) ChatServerHandler.addChannel(channelHandlerContext.channel(), authResult);
        channelHandlerContext.channel().writeAndFlush(authResult);
    }
}
