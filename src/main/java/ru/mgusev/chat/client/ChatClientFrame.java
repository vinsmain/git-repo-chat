package ru.mgusev.chat.client;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ru.mgusev.chat.client.view.AuthController;
import ru.mgusev.chat.client.view.MessageOverviewController;
import ru.mgusev.chat.client.view.RegisterController;
import java.io.IOException;

public class ChatClientFrame extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;
    private Pane authFrame;
    private Pane registerFrame;
    private AnchorPane chatFrame;
    private String nickName;
    private VBox usersListVBox;
    private boolean tryAuthOrReg = true; //true - auth, false - reg
    private static ChatClient chatClient;
    private static MessageOverviewController controller;
    private static AuthController authController;
    private static RegisterController registerController;

    //private ObservableList<ChatMessage> messageData = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Chat");

        //connect();

        initRootLayout();
        initAuthFrame();
        initRegisterFrame();
        initChatFrame();
    }

    @Override
    public void stop() throws Exception {
        if (MessageOverviewController.isConnected()) {
            chatClient.getChannel().disconnect();
        }
    }

    public ChatClient getChatClient() {
        return chatClient;
    }

    public void connect() {
        if (!MessageOverviewController.isConnected()) {
            Service<Void> connectService = new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            chatClient = new ChatClient("localhost", 8000, authController.getMainApp());
                            //ChatClientFrame.setChatClient(chatClient);
                            chatClient.run();
                            return null;
                        }
                    };
                }
            };
            connectService.restart();
        }
    }

    public void initRootLayout() {
        try {
            // Загружаем корневой макет из fxml файла.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/RootLayout.fxml"));
            rootLayout = loader.load();

            // Отображаем сцену, содержащую корневой макет.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initAuthFrame() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/AuthFrame.fxml"));
            authFrame = loader.load();

            // Помещаем сведения об адресатах в центр корневого макета.
            rootLayout.setCenter(authFrame);
            // Даём контроллеру доступ к главному приложению.
            authController = loader.getController();
            authController.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initRegisterFrame() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/RegisterFrame.fxml"));
            registerFrame = loader.load();

            registerController = loader.getController();
            registerController.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void initChatFrame() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/ChatFrame.fxml"));
            chatFrame = loader.load();

            controller = loader.getController();
            controller.setMainApp(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /*public ObservableList<ChatMessage> getPersonData() {
        return messageData;
    }*/

    public static MessageOverviewController getController() {
        return controller;
    }

    public static void setChatClient(ChatClient chatClient1) {
        chatClient = chatClient1;
    }

    public void setMainFrame(Pane frame) {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                rootLayout.setCenter(frame);
            }
        });
    }

    public Pane getAuthFrame() {
        return authFrame;
    }

    public Pane getRegisterFrame() {
        return registerFrame;
    }

    public AnchorPane getChatFrame() {
        return chatFrame;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public static AuthController getAuthController() {
        return authController;
    }

    public static RegisterController getRegisterController() {
        return registerController;
    }

    public void setTryAuthOrReg(boolean tryAuthOrReg) {
        this.tryAuthOrReg = tryAuthOrReg;
    }

    public boolean isTryAuthOrReg() {
        return tryAuthOrReg;
    }

    public VBox getUsersListVBox() {
        return usersListVBox;
    }
}
