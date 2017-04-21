package ru.mgusev.chat.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.mgusev.chat.client.model.RegisterMessage;

public class ServerRegisterHandler extends SimpleChannelInboundHandler<RegisterMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RegisterMessage registerMessage) throws Exception {
        System.out.println("3");
        channelHandlerContext.channel().writeAndFlush(DataBaseHandler.registration(registerMessage));
        System.out.println("4");
    }
}
