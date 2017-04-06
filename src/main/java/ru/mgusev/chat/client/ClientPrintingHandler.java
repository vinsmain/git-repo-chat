package ru.mgusev.chat.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import ru.mgusev.chat.client.model.StartPrintingMessage;
import java.util.Vector;

public class ClientPrintingHandler extends SimpleChannelInboundHandler<Vector<StartPrintingMessage>> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Vector<StartPrintingMessage> msg) throws Exception {
        String resultPrintingMessage = "";
        if (msg.isEmpty()) {
            resultPrintingMessage = "";
        } else if (msg.size() == 1) {
            resultPrintingMessage = msg.elementAt(0).getNickName() + " печатает сообщение...";
        } else if (msg.size() < 5) {
            for (StartPrintingMessage printingMessage: msg) {
                if (msg.indexOf(printingMessage) < msg.size() - 1) {
                    resultPrintingMessage += printingMessage.getNickName() + ", ";
                } else resultPrintingMessage += printingMessage.getNickName();
            }
            resultPrintingMessage += " печатают сообщение...";
        } else resultPrintingMessage = msg.size() + " пользователей печатают сообщение...";

        ChatClientFrame.getController().setPrintingField(resultPrintingMessage);
    }
}