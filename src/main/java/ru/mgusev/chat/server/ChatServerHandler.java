package ru.mgusev.chat.server;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import ru.mgusev.chat.client.model.ChatMessage;
import ru.mgusev.chat.client.model.ServerMessage;
import java.util.Date;

public class ChatServerHandler extends SimpleChannelInboundHandler<ChatMessage> {

    private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static ChannelHandlerContext channelHandlerContext;

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        channelHandlerContext = ctx;
        ServerMessage serverMessage = new ServerMessage(new Date(), "[SERVER] - " + incoming.remoteAddress() + " has joined!");
        System.out.println(serverMessage.getServerMessage());
        for (Channel channel : channels) {
            channel.writeAndFlush(serverMessage);
        }
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        ServerMessage serverMessage = new ServerMessage(new Date(), "[SERVER] - " + incoming.remoteAddress() + " has left!");
        System.out.println(serverMessage.getServerMessage());
        channels.remove(ctx.channel());
        for (Channel channel : channels) {
            channel.writeAndFlush(serverMessage);
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
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Client " + ctx.channel().remoteAddress() + " connected");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    public static ChannelGroup getChannels() {
        return channels;
    }

    public static void addChannel(Channel channel) {
        channels.add(channelHandlerContext.channel());
    }
}
