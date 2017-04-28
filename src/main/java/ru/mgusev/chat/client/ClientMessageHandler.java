package ru.mgusev.chat.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.mgusev.chat.client.model.ChatMessage;

public class ClientMessageHandler extends SimpleChannelInboundHandler<ChatMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ChatMessage msg) throws Exception {
        ChatClientFrame.getController().printMessage("[" + msg.getDateTime() + "] " + msg.getNickName() + ": " + msg.getMessage());
    }
}
