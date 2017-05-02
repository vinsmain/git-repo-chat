package ru.mgusev.chat.client;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.concurrent.ConcurrentHashMap;

public class UsersListHandler extends SimpleChannelInboundHandler<ConcurrentHashMap<Channel, String>> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ConcurrentHashMap<Channel, String> usersHM) throws Exception {
        ChatClientFrame.getController().setUsersList(usersHM);
        System.out.println("1");
    }
}
