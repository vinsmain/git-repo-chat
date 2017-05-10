package ru.mgusev.chat.client.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import ru.mgusev.chat.client.ChatClientFrame;
import ru.mgusev.chat.client.model.ChatMessage;
import ru.mgusev.chat.client.model.ServerMessage;
import ru.mgusev.chat.client.model.StartPrintingMessage;
import ru.mgusev.chat.client.model.UsersListMessage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

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
    private static boolean isConnected = false;
    private static long time = System.currentTimeMillis();
    private static boolean isPrinting = false;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd.MM.yyyy");

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

    public void setPrintingField(CopyOnWriteArrayList<StartPrintingMessage> msg) {
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    String resultPrintingMessage = "";
                    if (msg.isEmpty()) {
                        resultPrintingMessage = "";
                    } else if (msg.size() == 1) {
                        resultPrintingMessage = msg.get(0).getNickName() + " печатает сообщение...";
                    } else if (msg.size() < 5) {
                        for (StartPrintingMessage printingMessage: msg) {
                            if (msg.indexOf(printingMessage) < msg.size() - 1) {
                                resultPrintingMessage += printingMessage.getNickName() + ", ";
                            } else resultPrintingMessage += printingMessage.getNickName();
                        }
                        resultPrintingMessage += " печатают сообщение...";
                    } else resultPrintingMessage = msg.size() + " пользователей печатают сообщение...";

                    printingField.setText(resultPrintingMessage);
                }
            });
        } catch (ArrayIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
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
                dateAndNick.setText("[" + dateFormat.format(msg.getDateTime()) + "] " + msg.getNickName() + ": ");
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
                message.setText("[" + dateFormat.format(serverMessage.getServerDateTime()) + "] " + serverMessage.getServerMessage() + "\r\n");
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

    public void setUsersList(CopyOnWriteArrayList<String> usersList) {
        try {
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    usersListVBox.getChildren().clear();
                    for (String value : usersList) {
                        try {
                            usersListVBox.getChildren().add(new Label(value));
                        } catch (Exception e) {
                            System.out.println("222");
                            e.printStackTrace();
                        }
                    }
                }
            });
        } catch (Exception e) {
            System.out.println("222");
            e.printStackTrace();
        }
    }
}
