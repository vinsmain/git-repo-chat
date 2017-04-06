package ru.mgusev.chat.client;

import javafx.application.Application;

public class MainClient {
    public static void main(String[] args) {
        try {
            //ChatClient chatClient = new ChatClient("localhost", 8000);
            //ChatClientFrame.setChatClient(chatClient);
            Application.launch(ChatClientFrame.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
