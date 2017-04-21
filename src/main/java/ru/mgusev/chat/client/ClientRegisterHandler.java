package ru.mgusev.chat.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.mgusev.chat.client.model.RegisterResult;

public class ClientRegisterHandler extends SimpleChannelInboundHandler<RegisterResult> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RegisterResult registerResult) throws Exception {
        System.out.println("123");
        ChatClientFrame.getRegisterController().registration(registerResult);
    }
}
