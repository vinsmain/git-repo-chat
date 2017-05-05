package ru.mgusev.chat.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.mgusev.chat.client.model.UsersListMessage;

public class UsersListHandler extends SimpleChannelInboundHandler<UsersListMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, UsersListMessage usersList) throws Exception {
        ChatClientFrame.getController().setUsersList(usersList.getUsersList());
    }
}
