package ru.mgusev.chat.client.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.mgusev.chat.client.ChatClientFrame;
import ru.mgusev.chat.client.model.AuthMessage;
import ru.mgusev.chat.client.model.AuthResult;

import java.util.concurrent.CountDownLatch;

public class AuthController {

    private ChatClientFrame mainApp;
    private CountDownLatch cdl;

    @FXML
    private Label errorLabel = new Label("");

    @FXML
    private TextField loginField = new TextField();

    @FXML
    private TextField passwordField = new PasswordField();

    @FXML
    private CheckBox rememberCB = new CheckBox();

    @FXML
    private Button authButton = new Button();

    @FXML
    private Button registerButton = new Button();

    @FXML
    private void registerButtonAction() {
        mainApp.setMainFrame(mainApp.getRegisterFrame());
        errorLabel.setText("");
    }

    @FXML
    private void authButtonAction() {
        disableElements(true);
        cdl = new CountDownLatch(1);
        mainApp.setTryAuthOrReg(true);
        mainApp.connect();

        try {
            cdl.await();
            mainApp.getChatClient().getChannelFuture().addListener(channelFuture -> {
                mainApp.getChatClient().getChannel().writeAndFlush(new AuthMessage(loginField.getText(), passwordField.getText()));
                setErrorLabel("");
            });
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void authorisation(AuthResult authResult) {
        try {
            if (authResult.getNickName() != null) {
                mainApp.setNickName(authResult.getNickName());
                mainApp.setMainFrame(mainApp.getChatFrame());
                if (rememberCB.isSelected()) {
                    passwordField.clear();
                    errorLabel.setText("");
                } else clearFields();
            } else {
                mainApp.getChatClient().getChannel().disconnect();
                MessageOverviewController.setIsConnected(false);
                setErrorLabel("Неверные данные для авторизации");
                disableElements(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
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

    private void clearFields() {
        loginField.clear();
        passwordField.clear();
        errorLabel.setText("");
    }
}
