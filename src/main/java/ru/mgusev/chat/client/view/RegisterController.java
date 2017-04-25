package ru.mgusev.chat.client.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import ru.mgusev.chat.client.ChatClient;
import ru.mgusev.chat.client.ChatClientFrame;
import ru.mgusev.chat.client.model.RegisterMessage;
import ru.mgusev.chat.client.model.RegisterResult;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterController {

    private ChatClient chatClient;
    private ChatClientFrame mainApp;
    private CountDownLatch cdl;

    @FXML
    private TextField nickField = new TextField();

    @FXML
    private TextField loginField = new TextField();

    @FXML
    private PasswordField passwordField = new PasswordField();

    @FXML
    private Label errorLabel = new Label("");

    @FXML
    private Button backButton = new Button();

    @FXML
    private Button registerButton = new Button();

    @FXML
    private void backButtonAction() {
        mainApp.setMainFrame(mainApp.getAuthFrame());
    }

    @FXML
    private void registerButtonAction() {
        if (isCorrect(nickField.getText()) && isCorrect(loginField.getText()) && isCorrect(passwordField.getText())) {
            disableElements(true);
            cdl = new CountDownLatch(1);
            mainApp.setTryAuthOrReg(false);
            mainApp.connect();

            try {
                cdl.await();
                mainApp.getChatClient().getChannelFuture().addListener(channelFuture -> {
                    mainApp.getChatClient().getChannel().writeAndFlush(new RegisterMessage(nickField.getText(), loginField.getText(), passwordField.getText()));
                    setErrorLabel("");
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else setErrorLabel("Поля заполнены некорректно:\nдопускается использовать только латинский алфавит и цифры");
    }

    public void registration(RegisterResult registerResult) {
        if (registerResult.isStatus()) {
            errorLabel.setTextFill(Color.BLUE);
            setErrorLabel(registerResult.getMessage());
            errorLabel.setTextFill(Color.RED);
            nickField.clear();
            loginField.clear();
            passwordField.clear();
        } else setErrorLabel(registerResult.getMessage());

        disableElements(false);
        mainApp.getChatClient().getChannel().disconnect();
        MessageOverviewController.setIsConnected(false);
    }

    public void disableElements(boolean status) {
        nickField.setDisable(status);
        loginField.setDisable(status);
        passwordField.setDisable(status);
        backButton.setDisable(status);
        registerButton.setDisable(status);
    }

    public void setErrorLabel(String text) {
        Platform.runLater(() -> errorLabel.setText(text));
    }

    public void cdlDown() {
        cdl.countDown();
    }

    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public void setMainApp(ChatClientFrame mainApp) {
        this.mainApp = mainApp;
    }

    public static boolean isCorrect(String text){
        Pattern p = Pattern.compile("^[A-Za-z0-9]{5,10}$");
        Matcher m = p.matcher(text);
        return m.matches();
    }
}
