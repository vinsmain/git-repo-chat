package ru.mgusev.chat.client.model;

import java.io.Serializable;

public class AuthResult implements Serializable {

    private String nickName;
    private String login;

    public AuthResult(String nickName, String login) {
        this.nickName = nickName;
        this.login = login;
    }

    public String getNickName() {
        return nickName;
    }

    public String getLogin() {
        return login;
    }
}
