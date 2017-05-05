package ru.mgusev.chat.client.model;

import java.io.Serializable;
import java.util.concurrent.CopyOnWriteArrayList;

public class UsersListMessage implements Serializable {

    private CopyOnWriteArrayList<String> usersList;

    public UsersListMessage(CopyOnWriteArrayList<String> usersList) {
        this.usersList = usersList;
    }

    public CopyOnWriteArrayList<String> getUsersList() {
        return usersList;
    }
}
