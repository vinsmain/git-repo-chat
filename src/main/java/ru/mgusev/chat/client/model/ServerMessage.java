package ru.mgusev.chat.client.model;

import java.io.Serializable;
import java.util.Date;

public class ServerMessage implements Serializable {
    private Date serverDateTime;
    private String serverMessage;

    public ServerMessage(Date serverDateTime, String serverMessage) {
        this.serverDateTime = serverDateTime;
        this.serverMessage = serverMessage;
    }

    public Date getServerDateTime() {
        return serverDateTime;
    }

    public String getServerMessage() {
        return serverMessage;
    }
}
