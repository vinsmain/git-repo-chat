package ru.mgusev.chat.client.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ru.mgusev.chat.client.ChatClientFrame;
import ru.mgusev.chat.client.model.AuthMessage;
import ru.mgusev.chat.client.model.AuthResult;

import java.util.concurrent.CountDownLatch;

public class AuthController {

    //private ChatClient chatClient;
    private ChatClientFrame mainApp;
    private CountDownLatch cdl;

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
        cdl = new CountDownLatch(1);
        mainApp.connect();
        disableElements(true);

        //ChannelFuture future = mainApp.connect();
        try {
            cdl.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mainApp.getChatClient().getChannelFuture().addListener(channelFuture -> {
          //  if (channelFuture.isSuccess()) {
                mainApp.getChatClient().getChannel().writeAndFlush(new AuthMessage(loginField.getText(), passwordField.getText()));
                setErrorLabel("");
          //  } else System.out.println("00000");
        });
    }

    public void authorisation(AuthResult authResult) {
        if (authResult.getNickName() != null) {
            mainApp.setNickName(authResult.getNickName());
            mainApp.setMainFrame(mainApp.getChatFrame());
        } else {
            setErrorLabel("Неверные данные для авторизации");
            disableElements(false);
        }
    }

    public void disableElements(boolean status) {
        loginField.setDisable(status);
        passwordField.setDisable(status);
        authButton.setDisable(status);
        registerButton.setDisable(status);
    }

    public void setErrorLabel(String text) {
        Platform.runLater(() -> errorLabel.setText(text));
    }

    public void setMainApp(ChatClientFrame mainApp) {
        this.mainApp = mainApp;
    }

    public ChatClientFrame getMainApp() {
        return mainApp;
    }

    public void cdlDown() {
        cdl.countDown();
    }
}
