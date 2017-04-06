package ru.mgusev.chat.client.view;

import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ru.mgusev.chat.client.ChatClient;
import ru.mgusev.chat.client.ChatClientFrame;
import ru.mgusev.chat.client.model.AuthMessage;
import ru.mgusev.chat.client.model.AuthResult;

public class AuthController {

    //private ChatClient chatClient;
    private ChatClientFrame mainApp;

    @FXML
    private Label errorLabel = new Label("");

    @FXML
    private TextField loginField = new TextField();

    @FXML
    private TextField passwordField = new PasswordField();

    @FXML
    private Button authButton = new Button();

    @FXML
    private Button registerButton = new Button();

    @FXML
    private void registerButtonAction() {
        mainApp.setMainFrame(mainApp.getRegisterFrame());
    }

    @FXML
    private void authButtonAction() {
        ChatClientFrame.connect();
        while (true) {
            if (MessageOverviewController.isConnected()) {
                ChatClientFrame.getChatClient().getChannel().writeAndFlush(new AuthMessage(loginField.getText(), passwordField.getText()));
                setErrorLabel("");
                activeElements(true);
                break;
            }
        }
    }

    public void authorisation(AuthResult authResult) {
        System.out.println(authResult.getNickName());
        if (authResult.getNickName() != null) {
            mainApp.setNickName(authResult.getNickName());
            mainApp.setMainFrame(mainApp.getChatFrame());
        } else {
            setErrorLabel("Неверные данные для авторизации");
            activeElements(false);
        }
    }

    public void activeElements(boolean status) {
        loginField.setDisable(status);
        passwordField.setDisable(status);
        authButton.setDisable(status);
        registerButton.setDisable(status);
    }

    public void setErrorLabel(String text) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                errorLabel.setText(text);
            }
        });
    }

    /*private void connect() {
        if (!MessageOverviewController.isConnected()) {
            Service<Void> connectService = new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            chatClient = new ChatClient("localhost", 8000);
                            ChatClientFrame.setChatClient(chatClient);
                            chatClient.run();
                            return null;
                        }
                    };
                }
            };
            connectService.restart();
        }
    }*/

    /*public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;
    }*/

    public void setMainApp(ChatClientFrame mainApp) {
        this.mainApp = mainApp;
    }
}
