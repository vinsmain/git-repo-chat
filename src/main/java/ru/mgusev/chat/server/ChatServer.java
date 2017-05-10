package ru.mgusev.chat.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import ru.mgusev.chat.client.model.StartPrintingMessage;
import ru.mgusev.chat.client.model.StopPrintingMessage;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ChatServer {

    public static void main(String[] args) {
        new ChatServer(8000).run();
    }

    private final int port;
    private static CopyOnWriteArrayList<StartPrintingMessage> printingUsersArray = new CopyOnWriteArrayList<>();
    private static boolean arrayIsChange = false;

    public ChatServer(int port) {
        this.port = port;
    }

    public void run() {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        DataBaseHandler.connect();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChatServerInitializer());

            Channel channel = bootstrap.bind(port).sync().channel();

            ScheduledFuture<?> future = channel.eventLoop().scheduleAtFixedRate(
                    (Runnable) () -> {
                        if (arrayIsChange) {
                            for (Channel channelArray : ChatServerHandler.getChannels()) {
                                channelArray.writeAndFlush(printingUsersArray);
                            }
                        }
                    }, 100, 100, TimeUnit.MILLISECONDS);

            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            DataBaseHandler.disconnect();
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void addPrintingUser(StartPrintingMessage msg) {
        printingUsersArray.add(msg);
        arrayIsChange = true;
    }

    public static void removePrintingUser(StopPrintingMessage msg) {
        int index = -1;
        for (StartPrintingMessage startPrintingMessage : printingUsersArray) {
            if (startPrintingMessage.getNickName().equals(msg.getNickName())) {
                index = printingUsersArray.indexOf(startPrintingMessage);
                break;
            }
        }
        if (index != -1) {
            printingUsersArray.remove(index);
            arrayIsChange = true;
        }
    }
}
