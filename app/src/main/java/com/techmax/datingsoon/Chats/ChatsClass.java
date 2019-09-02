package com.techmax.datingsoon.Chats;

import java.util.Date;

public class ChatsClass {

    private Date chat_datesent;
    private Date chat_dateseen;
    private String chat_sender;
    private String chat_receiver;
    private String chat_message;
    private String chat_seenchat;
    private String delete_sender;
    private String delete_receiver;

    public ChatsClass() {

    }

    public ChatsClass(Date chat_datesent, Date chat_dateseen, String chat_sender, String chat_receiver, String chat_message, String chat_seenchat, String delete_sender, String delete_receiver) {
        this.chat_datesent = chat_datesent;
        this.chat_dateseen = chat_dateseen;
        this.chat_sender = chat_sender;
        this.chat_receiver = chat_receiver;
        this.chat_message = chat_message;
        this.chat_seenchat = chat_seenchat;
        this.delete_sender = delete_sender;
        this.delete_receiver = delete_receiver;
    }

    public Date getChat_datesent() {
        return chat_datesent;
    }

    public void setChat_datesent(Date chat_datesent) {
        this.chat_datesent = chat_datesent;
    }

    public Date getChat_dateseen() {
        return chat_dateseen;
    }

    public void setChat_dateseen(Date chat_dateseen) {
        this.chat_dateseen = chat_dateseen;
    }

    public String getChat_sender() {
        return chat_sender;
    }

    public void setChat_sender(String chat_sender) {
        this.chat_sender = chat_sender;
    }

    public String getChat_receiver() {
        return chat_receiver;
    }

    public void setChat_receiver(String chat_receiver) {
        this.chat_receiver = chat_receiver;
    }

    public String getChat_message() {
        return chat_message;
    }

    public void setChat_message(String chat_message) {
        this.chat_message = chat_message;
    }

    public String getChat_seenchat() {
        return chat_seenchat;
    }

    public void setChat_seenchat(String chat_seenchat) {
        this.chat_seenchat = chat_seenchat;
    }

    public String getDelete_sender() {
        return delete_sender;
    }

    public void setDelete_sender(String delete_sender) {
        this.delete_sender = delete_sender;
    }

    public String getDelete_receiver() {
        return delete_receiver;
    }

    public void setDelete_receiver(String delete_receiver) {
        this.delete_receiver = delete_receiver;
    }
}
