package ru.mgusev.chat.client.model;

import io.netty.channel.Channel;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;


public class UsersListMessage implements Serializable {

    private ConcurrentHashMap<Channel, String> usersHM;

    public UsersListMessage(ConcurrentHashMap<Channel, String> usersHM) {
        this.usersHM = usersHM;
        System.out.println(this.usersHM);
    }

    public ConcurrentHashMap<Channel, String> getUsersHM() {
        return usersHM;
    }
}
