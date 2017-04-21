package ru.mgusev.chat.client.model;

import java.io.Serializable;

public class RegisterResult implements Serializable {

    //status = true - success, status = false - fail;
    private boolean status;
    private String message;

    public RegisterResult(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
