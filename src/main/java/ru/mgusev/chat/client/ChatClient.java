package ru.mgusev.chat.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import ru.mgusev.chat.client.model.StopPrintingMessage;
import ru.mgusev.chat.client.view.MessageOverviewController;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class ChatClient {

    private final String host;
    private final int port;
    private ChannelFuture channel;

    public ChatClient(String host, int port) {
        this.host = host;
        this.port = port;
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

            ScheduledFuture<?> future = channel.channel().eventLoop().scheduleAtFixedRate(
                (Runnable) () -> {
                    if (MessageOverviewController.isPrinting() && System.currentTimeMillis() - MessageOverviewController.getTime() > 500 && MessageOverviewController.isConnected()) {
                        ChatClientFrame.getChatClient().getChannel().writeAndFlush(new StopPrintingMessage());
                        MessageOverviewController.setIsPrinting(false);
                    }
                }, 100, 100, TimeUnit.MILLISECONDS);

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
}
