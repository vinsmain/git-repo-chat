package ru.mgusev.chat.client.model;

import java.io.Serializable;

public class StartPrintingMessage implements Serializable {

    private String nickName;

    public StartPrintingMessage(String nickName) {
        this.nickName = nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }
}
