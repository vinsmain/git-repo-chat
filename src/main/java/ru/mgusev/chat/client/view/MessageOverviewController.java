package ru.mgusev.chat.client.view;

import io.netty.channel.Channel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import ru.mgusev.chat.client.ChatClient;
import ru.mgusev.chat.client.ChatClientFrame;
import ru.mgusev.chat.client.model.AuthResult;
import ru.mgusev.chat.client.model.ChatMessage;
import ru.mgusev.chat.client.model.ServerMessage;
import ru.mgusev.chat.client.model.StartPrintingMessage;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

public class MessageOverviewController {

    @FXML
    private TextArea sendMessageArea = new TextArea();
    @FXML
    private TextFlow chatArea = new TextFlow();
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

    public void printMessage(ChatMessage msg) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Text dateAndNick = new Text();
                Text message = new Text();
                if (mainApp.getNickName().equals(msg.getNickName())) dateAndNick.setStyle("-fx-fill: BLUE;-fx-font-weight:bold;");
                else dateAndNick.setStyle("-fx-fill: RED;-fx-font-weight:bold;");
                dateAndNick.setText("[" + msg.getDateTime() + "] " + msg.getNickName() + ": ");
                message.setText(msg.getMessage() + "\r\n");
                chatArea.getChildren().addAll(dateAndNick, message);
            }
        });
    }

    public void printServerMessage(ServerMessage serverMessage) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Text message = new Text();
                message.setStyle("-fx-fill: BLACK;-fx-font-weight:bold;");
                message.setText("[" + serverMessage.getServerDateTime() + "] " + serverMessage.getServerMessage() + "\r\n");
                chatArea.getChildren().addAll(message);
            }
        });
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
