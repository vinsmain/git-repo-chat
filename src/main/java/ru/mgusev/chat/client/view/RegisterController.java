package ru.mgusev.chat.client.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ru.mgusev.chat.client.ChatClient;
import ru.mgusev.chat.client.ChatClientFrame;
import ru.mgusev.chat.client.model.RegisterMessage;

public class RegisterController {

    private ChatClient chatClient;
    private ChatClientFrame mainApp;

    @FXML
    private TextField nickField = new TextField();

    @FXML
    private TextField loginField = new TextField();

    @FXML
    private PasswordField passwordField = new PasswordField();

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
        //System.out.println("111");
        //System.out.println(nickField.getText() + loginField.getText() + passwordField.getText());
        ChatClientFrame.getChatClient().getChannel().writeAndFlush(new RegisterMessage(nickField.getText(), loginField.getText(), passwordField.getText()));
        //System.out.println("222");
    }

    public void setChatClient(ChatClient chatClient) {
        this.chatClient = chatClient;
    }

    public void setMainApp(ChatClientFrame mainApp) {
        this.mainApp = mainApp;
    }
}
