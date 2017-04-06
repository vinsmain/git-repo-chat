package ru.mgusev.chat.client.model;

import java.io.Serializable;

public class RegisterMessage implements Serializable {

    private String nickName;
    private String login;
    private String password;

    public RegisterMessage(String nickName, String login, String password) {
        this.nickName = nickName;
        this.login = login;
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
