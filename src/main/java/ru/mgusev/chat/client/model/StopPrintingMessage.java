package ru.mgusev.chat.client.model;

import java.io.Serializable;

public class StopPrintingMessage implements Serializable {

    private String nickName;

    public StopPrintingMessage(String nickName) {
        this.nickName = nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }
}
