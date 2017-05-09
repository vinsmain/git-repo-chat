package ru.mgusev.chat.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import ru.mgusev.chat.client.model.*;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatServerHandler extends SimpleChannelInboundHandler<ChatMessage> {

    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static final ConcurrentHashMap<Channel, AuthResult> usersHM = new ConcurrentHashMap<>();
    private static final CopyOnWriteArrayList<String> usersList = new CopyOnWriteArrayList<>();

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        String logoutUser = usersHM.get(ctx.channel()).getNickName();
        if (logoutUser != null) {
            ChatServer.deleteElementToVector(new StopPrintingMessage((usersHM.get(ctx.channel()).getNickName())));
            channels.remove(ctx.channel());
            usersHM.remove(ctx.channel());
            usersList.remove(logoutUser);
            for (Channel channel : channels) {
                channel.writeAndFlush(new ServerMessage(new Date(), "Пользователь " + logoutUser + " отключается"));
                channel.writeAndFlush(new UsersListMessage(usersList));
            }
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ChatMessage buf) throws Exception {
        Channel incoming = ctx.channel();
        System.out.println("[" + incoming.remoteAddress() + "] " + buf.getDateTime() + " " + buf.getNickName() + " " + buf.getMessage());
        for (Channel channel : channels) {
            //if (channel != incoming) {
            channel.writeAndFlush(buf);
            //}
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public static ChannelGroup getChannels() {
        return channels;
    }

    public static void addChannel(Channel incoming, AuthResult authResult) {
        channels.add(incoming);
        usersHM.put(incoming, authResult);
        usersList.add(authResult.getNickName());
        usersList.sort((o1, o2) -> o1.compareTo(o2));
        for (Channel channel : channels) {
            if (channel != incoming) {
                channel.writeAndFlush(new ServerMessage(new Date(), "Пользователь " + authResult.getNickName() + " присоединяется к беседе"));
            }
            channel.writeAndFlush(new UsersListMessage(usersList));
        }
    }

    public static boolean isAuthUser(String login) {
        return usersHM.contains(login);
    }
}
