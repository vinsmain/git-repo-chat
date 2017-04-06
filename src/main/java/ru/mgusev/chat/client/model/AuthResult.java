package ru.mgusev.chat.client.model;

import java.io.Serializable;

public class AuthResult implements Serializable {

    private String nickName;

    public AuthResult(String nickName) {
        this.nickName = nickName;
    }

    public String getNickName() {
        return nickName;
    }
}
