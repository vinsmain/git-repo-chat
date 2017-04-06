package ru.mgusev.chat.client.model;

import javafx.beans.property.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

public class ChatMessage implements Serializable {
    private Date dateTime;
    private String nickName;
    private String message;
    //private final StringProperty street;
    //private final IntegerProperty postalCode;
    //private final StringProperty city;

/*
    public ChatMessage() {
        this(null, null);
    }

    /**
     * Конструктор с некоторыми начальными данными.
     *
     * @param firstName
     * @param lastName
     */
    public ChatMessage(Date dateTime, String nickName, String message) {
        this.nickName = nickName;
        this.message = message;
/*
        // Какие-то фиктивные начальные данные для удобства тестирования.
        this.street = new SimpleStringProperty("какая-то улица");
        this.postalCode = new SimpleIntegerProperty(1234);
        this.city = new SimpleStringProperty("какой-то город");*/
        this.dateTime = dateTime;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public String getNickName() {
        return nickName;
    }

    public String getMessage() {
        return message;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
