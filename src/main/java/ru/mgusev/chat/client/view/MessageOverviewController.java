package ru.mgusev.chat.client.view;

import io.netty.channel.Channel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import ru.mgusev.chat.client.ChatClient;
import ru.mgusev.chat.client.ChatClientFrame;
import ru.mgusev.chat.client.model.AuthResult;
import ru.mgusev.chat.client.model.ChatMessage;
import ru.mgusev.chat.client.model.StartPrintingMessage;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class MessageOverviewController {

    @FXML
    private TextArea sendMessageArea = new TextArea();
    @FXML
    private TextArea chatArea = new TextArea();
    @FXML
    private TextField printingField = new TextField();
    @FXML
    private VBox usersListVBox = new VBox();

    private ChatClientFrame mainApp;
    //private ChatClient chatClient;
    private static boolean isConnected = false;
    private static long time = System.currentTimeMillis();
    private static boolean isPrinting = false;

    public void setMainApp(ChatClientFrame mainApp) {
        this.mainApp = mainApp;
    }

    @FXML
    private void sendMessage() {
        if (isConnected && !sendMessageArea.getText().trim().equals("")) {
            ChatMessage msg = new ChatMessage(new Date(), mainApp.getNickName(), sendMessageArea.getText());
            mainApp.getChatClient().getChannel().writeAndFlush(msg);
            sendMessageArea.setText("");
        }
    }

    @FXML
    public void disconnect() {
        if (isConnected) {
            mainApp.getChatClient().getChannel().disconnect();
            isConnected = false;
            mainApp.setMainFrame(mainApp.getAuthFrame());
            mainApp.getAuthController().disableElements(false);
        }
    }

    @FXML
    private void startPrintingMessage() {
        time = System.currentTimeMillis();
        if (!isPrinting && isConnected) {
            mainApp.getChatClient().getChannel().writeAndFlush(new StartPrintingMessage(mainApp.getNickName()));
            isPrinting = true;
        }
    }

    public void setPrintingField(String message) {
        printingField.setText(message);
    }
/*
    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public ChatClient getChatClient() {
        return chatClient;
    }

   /* public ChatClientFrame getMainApp() {
        return mainApp;
    }*/

    public void printMessage(String message) {
        chatArea.appendText(message + "\r\n");
    }

    public static void setIsConnected(boolean isConnected) {
        MessageOverviewController.isConnected = isConnected;
    }

    public TextArea getSendMessageArea() {
        return sendMessageArea;
    }

    public static long getTime() {
        return time;
    }

    public static boolean isPrinting() {
        return isPrinting;
    }

    public static void setIsPrinting(boolean isPrinting) {
        MessageOverviewController.isPrinting = isPrinting;
    }

    public static boolean isConnected() {
        return isConnected;
    }

    public void setUsersList(ConcurrentHashMap<Channel, String> usersList) {
        mainApp.getUsersListVBox().getChildren().clear();
        for (String value : usersList.values()) {
            mainApp.getUsersListVBox().getChildren().add(new Label(value));
        }
    }
}
