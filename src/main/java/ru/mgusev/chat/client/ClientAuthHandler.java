package ru.mgusev.chat.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.mgusev.chat.client.model.AuthResult;

public class ClientAuthHandler extends SimpleChannelInboundHandler<AuthResult> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, AuthResult authResult) throws Exception {
        ChatClientFrame.getAuthController().authorisation(authResult);
    }
}
