package ru.mgusev.chat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import ru.mgusev.chat.client.model.StopPrintingMessage;
import ru.mgusev.chat.client.view.AuthController;
import ru.mgusev.chat.client.view.MessageOverviewController;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ChatClient {

    private final String host;
    private final int port;
    private ChannelFuture channel;
    private ChatClientFrame chatClientFrame;

    public ChatClient(String host, int port, ChatClientFrame chatClientFrame) {
        this.host = host;
        this.port = port;
        this.chatClientFrame = chatClientFrame;
    }

    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap()
                .group(group)
                .channel(NioSocketChannel.class)
                .handler(new ChatClientInitializer());

            channel = bootstrap.connect(host, port).sync();
            MessageOverviewController.setIsConnected(true);
            chatClientFrame.getAuthController().cdlDown();
            System.out.println("1");

            ScheduledFuture<?> future = channel.channel().eventLoop().scheduleAtFixedRate(
                (Runnable) () -> {
                    if (MessageOverviewController.isPrinting() && System.currentTimeMillis() - MessageOverviewController.getTime() > 500 && MessageOverviewController.isConnected()) {
                        chatClientFrame.getChatClient().getChannel().writeAndFlush(new StopPrintingMessage());
                        MessageOverviewController.setIsPrinting(false);
                    }
                }, 100, 100, TimeUnit.MILLISECONDS);
            System.out.println("2");
            channel.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

    public Channel getChannel() {
        return channel.channel();
    }

    public ChannelFuture getChannelFuture() {
        return channel;
    }
}
