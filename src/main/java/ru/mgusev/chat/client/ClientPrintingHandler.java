package ru.mgusev.chat.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.mgusev.chat.client.model.StartPrintingMessage;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientPrintingHandler extends SimpleChannelInboundHandler<CopyOnWriteArrayList<StartPrintingMessage>> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CopyOnWriteArrayList<StartPrintingMessage> msg) throws Exception {
        ChatClientFrame.getController().setPrintingField(msg);
    }
}