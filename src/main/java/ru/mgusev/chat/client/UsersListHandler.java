package ru.mgusev.chat.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.mgusev.chat.client.model.UsersListMessage;

import java.util.concurrent.ConcurrentHashMap;

public class UsersListHandler extends SimpleChannelInboundHandler<UsersListMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, UsersListMessage usersList) throws Exception {
        System.out.println("1");
        //ChatClientFrame.getController().setUsersList(usersList.getUsersHM());
    }
}
